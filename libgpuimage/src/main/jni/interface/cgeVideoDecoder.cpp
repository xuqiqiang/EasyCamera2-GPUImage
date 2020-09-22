/*
* cgeVideoDecoder.cpp
*
*  Created on: 2015-12-7
*/

#ifdef _CGE_USE_FFMPEG_

#include "cgeVideoDecoder.h"
#include "cgeFFmpegHeaders.h"

namespace CGE
{
	struct CGEVideoDecodeContext
	{
		CGEVideoDecodeContext() : pFormatCtx(nullptr), pVideoCodecCtx(nullptr), pAudioCodecCtx(nullptr), pVideoCodec(nullptr), pAudioCodec(nullptr), pVideoFrame(nullptr), pVideoFrameRGB(nullptr), pAudioFrame(nullptr), pVideoStream(nullptr), pAudioStream(nullptr), videoStreamIndex(-1), audioStreamIndex(-1), pSwrCtx(nullptr) {}
		~CGEVideoDecodeContext()
		{
			cleanup();
		}

		inline void cleanup()
		{
			if(pVideoCodecCtx != nullptr)
			{
				avcodec_close(pVideoCodecCtx);
				pVideoCodecCtx = nullptr;
			}
			
			if(pAudioCodecCtx)
			{
				avcodec_close(pAudioCodecCtx);
				pAudioCodecCtx = nullptr;
			}
			
			if(pFormatCtx != nullptr)
			{
				avformat_close_input(&pFormatCtx);
				pFormatCtx = nullptr;
			}
			
			av_free(pVideoFrame);
			av_free(pVideoFrameRGB);
			av_free(pAudioFrame);
			pVideoFrame = nullptr;
			pVideoFrameRGB = nullptr;
			pAudioFrame = nullptr;

			if(pSwrCtx != nullptr)
			{
				av_free(dstSampleData[0]);
				dstSampleData = nullptr;
				dstSamplesSize = 0;
				swr_free(&pSwrCtx);
				pSwrCtx = nullptr;
			}

			videoStreamIndex = -1;
			audioStreamIndex = -1;
		}

		AVFormatContext *pFormatCtx;
		AVCodecContext  *pVideoCodecCtx;
		AVCodecContext	*pAudioCodecCtx;

		AVCodec         *pVideoCodec;
		AVCodec			*pAudioCodec;

		AVFrame         *pVideoFrame;
		AVFrame         *pVideoFrameRGB;
		AVFrame			*pAudioFrame;

		AVPacket        packet;
		AVStream        *pVideoStream;
		AVStream		*pAudioStream;

		SwrContext      *pSwrCtx;
		uint8_t**       dstSampleData;
		int             dstSamplesLinesize;
		int             dstSamplesSize;
		int             maxDstNbSamples;

		int				videoStreamIndex;
		int				audioStreamIndex;
	};

	CGEVideoDecodeHandler::CGEVideoDecodeHandler() : m_bufferPtr(nullptr), m_width(0), m_height(0), m_samplingStyle(ssFastBilinear), m_currentTimestamp(0.0)
	{
		m_context = new CGEVideoDecodeContext();
		memset(&m_cachedVideoFrame, 0, sizeof(m_cachedVideoFrame));
		memset(&m_cachedAudioFrame, 0, sizeof(m_cachedAudioFrame));
	}

	CGEVideoDecodeHandler::~CGEVideoDecodeHandler()
	{
		close();
	}

	bool CGEVideoDecodeHandler::open(const char* filename)
	{		
		

		if(avformat_open_input(&m_context->pFormatCtx, filename, nullptr, nullptr)!=0 ||
			avformat_find_stream_info(m_context->pFormatCtx, nullptr)<0)
		{
			return false;  //解码失败
		}

		av_dump_format(m_context->pFormatCtx, 0, filename, 0);
		m_context->videoStreamIndex = -1;
		m_context->audioStreamIndex = -1;

		for(unsigned int i = 0; i < m_context->pFormatCtx->nb_streams; i++)  
		{
			if(m_context->pFormatCtx->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO)
			{  
				m_context->videoStreamIndex = i;
				m_context->pVideoStream = m_context->pFormatCtx->streams[i];
				m_context->pVideoCodecCtx = m_context->pFormatCtx->streams[i]->codec;
			}
			else if(m_context->pFormatCtx->streams[i]->codec->codec_type == AVMEDIA_TYPE_AUDIO)
			{
				m_context->audioStreamIndex = i;
				m_context->pAudioStream = m_context->pFormatCtx->streams[i];
				m_context->pAudioCodecCtx = m_context->pFormatCtx->streams[i]->codec;
			}
		}

		if(m_context->videoStreamIndex == -1)
		{
			return false; //找不到视频文件流
		}

		if(m_context->audioStreamIndex == -1)
		{
			CGE_LOG_INFO("未找到音频流, 视频将处于静音状态...\n");
		}

		//////////////////////////////////////////////////////////////////////////

		m_context->pVideoCodec = avcodec_find_decoder(m_context->pVideoCodecCtx->codec_id);

		if(m_context->pVideoCodec == nullptr || avcodec_open2(m_context->pVideoCodecCtx, m_context->pVideoCodec, nullptr) < 0)
		{
			return false; //视频解码失败
		}

		if(m_context->audioStreamIndex != -1)
		{
			m_context->pAudioCodec = avcodec_find_decoder(m_context->pAudioCodecCtx->codec_id);

			if(m_context->pAudioCodec == nullptr || avcodec_open2(m_context->pAudioCodecCtx, m_context->pAudioCodec, nullptr) < 0)
			{
				CGE_LOG_ERROR("音频解码失败! 静音处理...\n");
				m_context->audioStreamIndex = -1;
				m_context->pAudioCodec = nullptr;
				m_context->pAudioCodecCtx = nullptr;
			}
		}

		m_width = m_context->pVideoCodecCtx->width;
		m_height = m_context->pVideoCodecCtx->height;

		m_context->pVideoFrame = av_frame_alloc();
		m_context->pAudioFrame = av_frame_alloc();

		av_init_packet(&m_context->packet);
		m_context->packet.data = nullptr;
		m_context->packet.size = 0;

		return m_context->pVideoFrame != nullptr && m_context->pAudioFrame != nullptr;// && initFrameRGB();
	}

	bool CGEVideoDecodeHandler::initFrameRGB()
	{
		

		if(m_context->pVideoFrameRGB == nullptr)
			m_context->pVideoFrameRGB = av_frame_alloc();  

		if(m_context->pVideoFrame == nullptr || m_context->pVideoFrameRGB == nullptr)
		{
			return false; //申请frame 失败.
		}

		int numBytes = avpicture_get_size(PIX_FMT_RGBA, m_context->pVideoCodecCtx->width,
			m_context->pVideoCodecCtx->height);  
		m_bufferPtr = (uint8_t *)av_malloc(numBytes * sizeof(uint8_t)); 

		avpicture_fill((AVPicture *)m_context->pVideoFrameRGB, m_bufferPtr, PIX_FMT_RGBA, m_context->pVideoCodecCtx->width, m_context->pVideoCodecCtx->height);  
		return true;
	}

	void CGEVideoDecodeHandler::close()
	{
		if(m_context == nullptr)
			return;
				
		av_free(m_bufferPtr);
		m_bufferPtr = nullptr;
		delete m_context;
		m_context = nullptr;
	}

	void CGEVideoDecodeHandler::start()
	{

	}

	void CGEVideoDecodeHandler::end()
	{

	}

	CGEFrameTypeNext CGEVideoDecodeHandler::queryNextFrame()
	{
		
		int gotFrame, ret = 0;

    	while(av_read_frame(m_context->pFormatCtx, &m_context->packet) >= 0)
		{  
			if(m_context->packet.stream_index == m_context->videoStreamIndex)
			{  
				ret = avcodec_decode_video2(m_context->pVideoCodecCtx, m_context->pVideoFrame, &gotFrame, &m_context->packet);

				if(gotFrame)
				{
                    m_currentTimestamp = 1000.0 * (m_context->pVideoFrame->pkt_pts - m_context->pVideoStream->start_time) * av_q2d(m_context->pVideoStream->time_base);

                    av_free_packet(&m_context->packet);
					return FrameType_VideoFrame;
				}
			}
			else if(m_context->packet.stream_index == m_context->audioStreamIndex)
			{
				ret = avcodec_decode_audio4(m_context->pAudioCodecCtx, m_context->pAudioFrame, &gotFrame, &m_context->packet);

//				ret = FFMIN(ret, m_context->packet.size);

				if(gotFrame)
				{
					av_free_packet(&m_context->packet);

// 					size_t unpaddedLinesize = m_context->pAudioFrame->nb_samples * av_get_bytes_per_sample((AVSampleFormat)m_context->pAudioFrame->format);

					return FrameType_AudioFrame;
				}
			}

			av_free_packet(&m_context->packet);
		}
		return FrameType_NoFrame;
	}

	const CGEVideoFrameBufferData* CGEVideoDecodeHandler::getNextVideoFrame()
	{
		CGEFrameTypeNext type;

		while ((type = queryNextFrame()) != FrameType_VideoFrame)
		{
			if(type == FrameType_NoFrame)
				return nullptr;
		}

		return getCurrentVideoFrame();
	}

	const CGEVideoFrameBufferData* CGEVideoDecodeHandler::getCurrentVideoFrame()
	{
		
		memcpy(m_cachedVideoFrame.linesize, m_context->pVideoFrame->linesize, sizeof(m_context->pVideoFrame->linesize));
		memcpy(m_cachedVideoFrame.data, m_context->pVideoFrame->data, sizeof(m_context->pVideoFrame->data));
		m_cachedVideoFrame.timestamp = av_frame_get_best_effort_timestamp(m_context->pVideoFrame);
		m_cachedVideoFrame.width = m_context->pVideoFrame->width;
		m_cachedVideoFrame.height = m_context->pVideoFrame->height;
		m_cachedVideoFrame.format = (CGEVideoFormat)m_context->pVideoFrame->format;
		return &m_cachedVideoFrame;
	}

	AVFrame* CGEVideoDecodeHandler::getCurrentVideoAVFrame()
	{
		
		return m_context->pVideoFrame;
	}

	const CGEAudioFrameBufferData* CGEVideoDecodeHandler::getNextAudio()
	{
		CGEFrameTypeNext type;

		while ((type = queryNextFrame()) != FrameType_AudioFrame)
		{
			if(type == FrameType_NoFrame)
				return nullptr;
		}

		return getCurrentAudioFrame();
	}

	const CGEAudioFrameBufferData* CGEVideoDecodeHandler::getCurrentAudioFrame()
	{
		if(m_context->pSwrCtx == nullptr)
		{
			if(m_context->pAudioStream->codec->sample_fmt != AV_SAMPLE_FMT_S16)
			{
				m_context->pSwrCtx = swr_alloc();
				if(m_context->pSwrCtx == nullptr)
				{
					CGE_LOG_ERROR("Allocate resampler context failed!\n");
					return nullptr;
				}

				auto ctx = m_context->pSwrCtx;
				auto c = m_context->pAudioStream->codec;

				av_opt_set_int       (ctx, "in_channel_count",   c->channels,       0);
				av_opt_set_int       (ctx, "in_sample_rate",     c->sample_rate,    0);
				av_opt_set_sample_fmt(ctx, "in_sample_fmt",      c->sample_fmt, 0);
				av_opt_set_int       (ctx, "out_channel_count",  1,       0);
				av_opt_set_int       (ctx, "out_sample_rate",    c->sample_rate,    0);
				av_opt_set_sample_fmt(ctx, "out_sample_fmt",     AV_SAMPLE_FMT_S16,     0);

				int ret;

				if ((ret = swr_init(ctx)) < 0)
				{
					CGE_LOG_ERROR("Failed to initialize the resampling context: %d\n", ret);
					return nullptr;
				}

				m_context->maxDstNbSamples = c->codec->capabilities & CODEC_CAP_VARIABLE_FRAME_SIZE ?
			10000 : c->frame_size;

				ret = av_samples_alloc_array_and_samples(&m_context->dstSampleData, &m_context->dstSamplesLinesize, c->channels, m_context->maxDstNbSamples, c->sample_fmt, 0);

				if (ret < 0)
				{
					CGE_LOG_ERROR("Could not allocate destination samples\n");
					return nullptr;
				}

				m_context->dstSamplesSize = av_samples_get_buffer_size(NULL, c->channels, m_context->maxDstNbSamples, c->sample_fmt, 0);

			}
			else
			{
				CGE_LOG_ERROR("errorxxxx");
			}
		}

		int ret = swr_convert(m_context->pSwrCtx, m_context->dstSampleData, m_context->dstSamplesSize, (const uint8_t**)m_context->pAudioFrame->data, m_context->pAudioFrame->nb_samples);

		if(ret <= 0)
			return nullptr;

		m_cachedAudioFrame.timestamp = av_frame_get_best_effort_timestamp(m_context->pAudioFrame);
		m_cachedAudioFrame.data = m_context->dstSampleData[0];
		m_cachedAudioFrame.nbSamples = m_context->pAudioFrame->nb_samples;
		m_cachedAudioFrame.channels = 1;//av_frame_get_channels(m_context->pAudioFrame);
		m_cachedAudioFrame.bytesPerSample = 2;
		m_cachedAudioFrame.linesize = m_context->dstSamplesSize;
		m_cachedAudioFrame.format = CGE_SAMPLE_FMT_S16;
		return &m_cachedAudioFrame;
	}

	AVFrame* CGEVideoDecodeHandler::getCurrentAudioAVFrame()
	{
		return m_context->pAudioFrame;
	}

	CGEVideoFrameBufferData CGEVideoDecodeHandler::getNextVideoFrameRGB()
	{
		if(queryNextFrame() != FrameType_VideoFrame)
		{
			return CGEVideoFrameBufferData();
		}
		return getCurrentVideoFrameRGB();
	}

	CGEVideoFrameBufferData CGEVideoDecodeHandler::getCurrentVideoFrameRGB()
	{
		
		SwsContext* img_convert_ctx = sws_getContext(m_context->pVideoCodecCtx->width, m_context->pVideoCodecCtx->height, m_context->pVideoCodecCtx->pix_fmt, m_context->pVideoCodecCtx->width, m_context->pVideoCodecCtx->height, PIX_FMT_RGBA, m_samplingStyle, nullptr, nullptr, nullptr);  
		// Convert the image from its native format to RGB  
		sws_scale(img_convert_ctx, m_context->pVideoFrame->data, m_context->pVideoFrame->linesize, 0, m_height, m_context->pVideoFrameRGB->data, m_context->pVideoFrameRGB->linesize);

		CGEVideoFrameBufferData data;
		data.width = m_width;
		data.height = m_height;
		data.format = (CGEVideoFormat)m_context->pVideoFrameRGB->format;
		memcpy(data.linesize, m_context->pVideoFrameRGB->linesize, sizeof(m_context->pVideoFrameRGB->linesize));
		memcpy(data.data, m_context->pVideoFrameRGB->data, sizeof(m_context->pVideoFrameRGB->data));
		return data;
	}

	double CGEVideoDecodeHandler::getTotalTime()
	{
		
		return (double)m_context->pFormatCtx->duration;
	}

	double CGEVideoDecodeHandler::getCurrentTimestamp()
	{
		return m_currentTimestamp;
	}

	int CGEVideoDecodeHandler::getAudioSampleRate()
	{
		return (m_context && m_context->pAudioStream) ? m_context->pAudioStream->codec->sample_rate : 0;
	}

	AVDictionary* CGEVideoDecodeHandler::getOptions()
	{
		return (m_context && m_context->pVideoStream) ? m_context->pFormatCtx->metadata : nullptr;
	}

    const char *CGEVideoDecodeHandler::getRotation() {
        return extractMetadataInternal("rotate");
    }

    const char *CGEVideoDecodeHandler::extractMetadataInternal(const char* key) {
        char* value = NULL;
        AVFormatContext *ic = m_context->pFormatCtx;
        AVStream *video_st = m_context->pVideoStream;
        AVStream *audio_st = m_context->pAudioStream;
        
        if (!ic) {
            return value;
        }
        
        if (key) {
            if (av_dict_get(ic->metadata, key, NULL, AV_DICT_MATCH_CASE)) {
                value = av_dict_get(ic->metadata, key, NULL, AV_DICT_MATCH_CASE)->value;
            } else if (audio_st && av_dict_get(audio_st->metadata, key, NULL, AV_DICT_MATCH_CASE)) {
                value = av_dict_get(audio_st->metadata, key, NULL, AV_DICT_MATCH_CASE)->value;
            } else if (video_st && av_dict_get(video_st->metadata, key, NULL, AV_DICT_MATCH_CASE)) {
                value = av_dict_get(video_st->metadata, key, NULL, AV_DICT_MATCH_CASE)->value;
            }
        }
        
        return value;   
    }
}

#endif