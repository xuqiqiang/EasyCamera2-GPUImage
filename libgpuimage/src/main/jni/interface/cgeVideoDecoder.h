/*
* cgeVideoDecoder.h
*
*  Created on: 2015-12-7
*/

#if !defined(_CGEVIDEODECODER_H_) && defined(_CGE_USE_FFMPEG_)
#define _CGEVIDEODECODER_H_

#include "cgeCommonDefine.h"

struct AVFrame;
struct AVDictionary;
class CGEVideoDecodeContext;

namespace CGE
{
	struct CGEVideoDecodeContext;

	enum CGEVideoFormat
	{
		CGE_VIDEO_FORMAT_NONE = -1,
		CGE_VIDEO_FORMAT_YUV420P,
		CGE_VIDEO_FORMAT_YUYV422,
		CGE_VIDEO_FORMAT_RGB24,
		CGE_VIDEO_FORMAT_BGR24,
		CGE_VIDEO_FORMAT_YUV422P,
		CGE_VIDEO_FORMAT_YUV444P,
		CGE_VIDEO_FORMAT_YUV410P,
		CGE_VIDEO_FORMAT_YUV411P,
		CGE_VIDEO_FORMAT_GRAY8,
		CGE_VIDEO_FORMAT_MONOWHITE,
		CGE_VIDEO_FORMAT_MONOBLACK,
		CGE_VIDEO_FORMAT_PAL8,
		CGE_VIDEO_FORMAT_YUVJ420P,
		CGE_VIDEO_FORMAT_YUVJ422P,
		CGE_VIDEO_FORMAT_YUVJ444P,

		CGE_VIDEO_FORMAT_XVMC_MPEG2_MC,
		CGE_VIDEO_FORMAT_XVMC_MPEG2_IDCT,
		CGE_VIDEO_FORMAT_UYVY422,
		CGE_VIDEO_FORMAT_UYYVYY411,
		CGE_VIDEO_FORMAT_BGR8,
		CGE_VIDEO_FORMAT_BGR4,
		CGE_VIDEO_FORMAT_BGR4_BYTE,
		CGE_VIDEO_FORMAT_RGB8,
		CGE_VIDEO_FORMAT_RGB4,
		CGE_VIDEO_FORMAT_RGB4_BYTE,
		CGE_VIDEO_FORMAT_NV12,
		CGE_VIDEO_FORMAT_NV21,

		CGE_VIDEO_FORMAT_ARGB,
		CGE_VIDEO_FORMAT_RGBA,
		CGE_VIDEO_FORMAT_ABGR,
		CGE_VIDEO_FORMAT_BGRA,

		CGE_VIDEO_FORMAT_GRAY16BE,
		CGE_VIDEO_FORMAT_GRAY16LE,
		CGE_VIDEO_FORMAT_YUV440P,
		CGE_VIDEO_FORMAT_YUVJ440P,
		CGE_VIDEO_FORMAT_YUVA420P,
		CGE_VIDEO_FORMAT_VDPAU_H264,
		CGE_VIDEO_FORMAT_VDPAU_MPEG1,
		CGE_VIDEO_FORMAT_VDPAU_MPEG2,
		CGE_VIDEO_FORMAT_VDPAU_WMV3,
		CGE_VIDEO_FORMAT_VDPAU_VC1,

		CGE_VIDEO_FORMAT_RGB48BE,
		CGE_VIDEO_FORMAT_RGB48LE,

		CGE_VIDEO_FORMAT_RGB565BE,
		CGE_VIDEO_FORMAT_RGB565LE,
		CGE_VIDEO_FORMAT_RGB555BE,
		CGE_VIDEO_FORMAT_RGB555LE,

		CGE_VIDEO_FORMAT_BGR565BE,
		CGE_VIDEO_FORMAT_BGR565LE,
		CGE_VIDEO_FORMAT_BGR555BE,
		CGE_VIDEO_FORMAT_BGR555LE,

		CGE_VIDEO_FORMAT_VAAPI_MOCO,
		CGE_VIDEO_FORMAT_VAAPI_IDCT,
		CGE_VIDEO_FORMAT_VAAPI_VLD,

		CGE_VIDEO_FORMAT_YUV420P16LE,
		CGE_VIDEO_FORMAT_YUV420P16BE,
		CGE_VIDEO_FORMAT_YUV422P16LE,
		CGE_VIDEO_FORMAT_YUV422P16BE,
		CGE_VIDEO_FORMAT_YUV444P16LE,
		CGE_VIDEO_FORMAT_YUV444P16BE,
		CGE_VIDEO_FORMAT_VDPAU_MPEG4,

		CGE_VIDEO_FORMAT_DXVA2_VLD,

		CGE_VIDEO_FORMAT_RGB444LE,
		CGE_VIDEO_FORMAT_RGB444BE,
		CGE_VIDEO_FORMAT_BGR444LE,
		CGE_VIDEO_FORMAT_BGR444BE,
		CGE_VIDEO_FORMAT_YA8,

		CGE_VIDEO_FORMAT_Y400A = CGE_VIDEO_FORMAT_YA8,
		CGE_VIDEO_FORMAT_GRAY8A= CGE_VIDEO_FORMAT_YA8,

		CGE_VIDEO_FORMAT_BGR48BE,
		CGE_VIDEO_FORMAT_BGR48LE,

		CGE_VIDEO_FORMAT_YUV420P9BE,
		CGE_VIDEO_FORMAT_YUV420P9LE,
		CGE_VIDEO_FORMAT_YUV420P10BE,
		CGE_VIDEO_FORMAT_YUV420P10LE,
		CGE_VIDEO_FORMAT_YUV422P10BE,
		CGE_VIDEO_FORMAT_YUV422P10LE,
		CGE_VIDEO_FORMAT_YUV444P9BE,
		CGE_VIDEO_FORMAT_YUV444P9LE,
		CGE_VIDEO_FORMAT_YUV444P10BE,
		CGE_VIDEO_FORMAT_YUV444P10LE,
		CGE_VIDEO_FORMAT_YUV422P9BE,
		CGE_VIDEO_FORMAT_YUV422P9LE,
		CGE_VIDEO_FORMAT_VDA_VLD,

		CGE_VIDEO_FORMAT_RGBA64BE,
		CGE_VIDEO_FORMAT_RGBA64LE,
		CGE_VIDEO_FORMAT_BGRA64BE,
		CGE_VIDEO_FORMAT_BGRA64LE,

		CGE_VIDEO_FORMAT_GBRP,
		CGE_VIDEO_FORMAT_GBRP9BE,
		CGE_VIDEO_FORMAT_GBRP9LE,
		CGE_VIDEO_FORMAT_GBRP10BE,
		CGE_VIDEO_FORMAT_GBRP10LE,
		CGE_VIDEO_FORMAT_GBRP16BE,
		CGE_VIDEO_FORMAT_GBRP16LE,

		CGE_VIDEO_FORMAT_YUVA422P_LIBAV,
		CGE_VIDEO_FORMAT_YUVA444P_LIBAV,

		CGE_VIDEO_FORMAT_YUVA420P9BE,
		CGE_VIDEO_FORMAT_YUVA420P9LE,
		CGE_VIDEO_FORMAT_YUVA422P9BE,
		CGE_VIDEO_FORMAT_YUVA422P9LE,
		CGE_VIDEO_FORMAT_YUVA444P9BE,
		CGE_VIDEO_FORMAT_YUVA444P9LE,
		CGE_VIDEO_FORMAT_YUVA420P10BE,
		CGE_VIDEO_FORMAT_YUVA420P10LE,
		CGE_VIDEO_FORMAT_YUVA422P10BE,
		CGE_VIDEO_FORMAT_YUVA422P10LE,
		CGE_VIDEO_FORMAT_YUVA444P10BE,
		CGE_VIDEO_FORMAT_YUVA444P10LE,
		CGE_VIDEO_FORMAT_YUVA420P16BE,
		CGE_VIDEO_FORMAT_YUVA420P16LE,
		CGE_VIDEO_FORMAT_YUVA422P16BE,
		CGE_VIDEO_FORMAT_YUVA422P16LE,
		CGE_VIDEO_FORMAT_YUVA444P16BE,
		CGE_VIDEO_FORMAT_YUVA444P16LE,

		CGE_VIDEO_FORMAT_VDPAU,

		CGE_VIDEO_FORMAT_XYZ12LE,
		CGE_VIDEO_FORMAT_XYZ12BE,
		CGE_VIDEO_FORMAT_NV16,
		CGE_VIDEO_FORMAT_NV20LE,
		CGE_VIDEO_FORMAT_NV20BE,

		CGE_VIDEO_FORMAT_RGBA64BE_LIBAV,
		CGE_VIDEO_FORMAT_RGBA64LE_LIBAV,
		CGE_VIDEO_FORMAT_BGRA64BE_LIBAV,
		CGE_VIDEO_FORMAT_BGRA64LE_LIBAV,

		CGE_VIDEO_FORMAT_YVYU422,
		CGE_VIDEO_FORMAT_VDA,
		CGE_VIDEO_FORMAT_YA16BE,
		CGE_VIDEO_FORMAT_YA16LE,

	};

	enum CGESampleFormat
	{
		CGE_SAMPLE_FMT_NONE = -1,
		CGE_SAMPLE_FMT_U8,          ///< unsigned 8 bits
		CGE_SAMPLE_FMT_S16,         ///< signed 16 bits
		CGE_SAMPLE_FMT_S32,         ///< signed 32 bits
		CGE_SAMPLE_FMT_FLT,         ///< float
		CGE_SAMPLE_FMT_DBL,         ///< double

		CGE_SAMPLE_FMT_U8P,         ///< unsigned 8 bits, planar
		CGE_SAMPLE_FMT_S16P,        ///< signed 16 bits, planar
		CGE_SAMPLE_FMT_S32P,        ///< signed 32 bits, planar
		CGE_SAMPLE_FMT_FLTP,        ///< float, planar
		CGE_SAMPLE_FMT_DBLP,        ///< double, planar

		CGE_SAMPLE_FMT_NB           ///< Number of sample formats. DO NOT USE if linking dynamically
	};

	struct CGEVideoFrameBufferData
	{
		const unsigned char* data[8]; // 浅拷贝， 实际内容为ffmpeg 的帧
		int linesize[8];
		double timestamp;
		int width;
		int height;		
		CGEVideoFormat format;
		
	};

	struct CGEAudioFrameBufferData
	{
		double timestamp;
		const unsigned char* data; // 浅拷贝， 实际内容为ffmpeg 的帧
		int nbSamples;		//采样数
		int bytesPerSample; //真实数据大小 = nbSamples * bytesPerSample
		int channels;		//声音包含几个通道
		int linesize;		//包含所有冗余数据的大小
		CGESampleFormat format;		
	};

	enum CGEFrameTypeNext{ FrameType_NoFrame, FrameType_VideoFrame, FrameType_AudioFrame };

	class CGEVideoDecodeHandler
	{
	public:
		CGEVideoDecodeHandler();
		~CGEVideoDecodeHandler();

		enum SamplingStyle { ssFastBilinear = 1, ssBilinear = 2, ssBicubic = 4, ssX = 8, ssPoint = 0x10, ssArea = 0x20, ssBicublin = 0x40, ssGauss = 0x80, ssSinc = 0x100, ssLanczos = 0x200, ssSpline = 0x400 };

		bool initFrameRGB();

		bool open(const char* filename);
		void close();

		void start();
		void end();

		CGEFrameTypeNext queryNextFrame();

		const CGEVideoFrameBufferData* getNextVideoFrame(); //强行忽略掉声音帧, 获取下一帧图像
		const CGEVideoFrameBufferData* getCurrentVideoFrame(); //获取当前帧, 重复调用可能会得到相同的重复图像
		AVFrame* getCurrentVideoAVFrame();

		const CGEAudioFrameBufferData* getNextAudio();
		const CGEAudioFrameBufferData* getCurrentAudioFrame();
		AVFrame* getCurrentAudioAVFrame();

		CGEVideoFrameBufferData getNextVideoFrameRGB();  //若 withAlpha为true， 则格式为 RGBA 		
		CGEVideoFrameBufferData getCurrentVideoFrameRGB(); //若 withAlpha为true， 则格式为 RGBA 

		void setSamplingStyle(SamplingStyle style) { m_samplingStyle = style; }

		int getWidth() { return m_width; }
		int getHeight() { return m_height; }
		double getTotalTime();
		double getCurrentTimestamp();

		int getAudioSampleRate();

		AVDictionary* getOptions();

		const char *getRotation();

	protected:
		CGEVideoDecodeContext* m_context;
		int m_width, m_height;
		SamplingStyle m_samplingStyle;

		CGEVideoFrameBufferData m_cachedVideoFrame;
		CGEAudioFrameBufferData m_cachedAudioFrame;
		double m_currentTimestamp;

	private:
		unsigned char* m_bufferPtr; //Video frame buffer cache

		const char* extractMetadataInternal(const char *key);
	};
}

#endif