package org.wysaid.camera.focus;

import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

public class FocusOverlayManager {

    private static final String TAG = "FocusOverlay";

    private FocusView mFocusView;
    private MainHandler mHandler;
    private CameraUiEvent mListener;
    private float currentX;
    private float currentY;
    private CoordinateTransformer mTransformer;
    private Rect mPreviewRect;
    private Rect mFocusRect;

    private static final int HIDE_FOCUS_DELAY = 4000;
    private static final int MSG_HIDE_FOCUS = 0x10;


    private static class MainHandler extends Handler {
        final WeakReference<FocusOverlayManager> mManager;

        MainHandler(FocusOverlayManager manager, Looper looper) {
            super(looper);
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mManager.get() == null) {
                return;
            }
            switch (msg.what) {
                case MSG_HIDE_FOCUS:
                    mManager.get().mFocusView.resetToDefaultPosition();
                    mManager.get().hideFocusUI();
                    mManager.get().mListener.resetTouchToFocus();
                    break;
            }
        }
    }

    public FocusOverlayManager(FocusView focusView, Looper looper) {
        mFocusView = focusView;
        mHandler = new MainHandler(this, looper);
        mFocusView.resetToDefaultPosition();
        mFocusRect = new Rect();
    }

    public void setListener(CameraUiEvent listener) {
        mListener = listener;
    }

    public void onPreviewChanged(int width, int height, CameraCharacteristics c) {
        mPreviewRect = new Rect(0, 0, width, height);
        mTransformer = new CoordinateTransformer(c, rectToRectF(mPreviewRect));
    }

    /* just set focus view position, not start animation*/
    public void startFocus(float x, float y) {
        currentX = x;
        currentY = y;
        mHandler.removeMessages(MSG_HIDE_FOCUS);
        mFocusView.moveToPosition(x, y);
        //mFocusView.startFocus();
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_FOCUS, HIDE_FOCUS_DELAY);
    }
    /* show focus view by af state */
    public void startFocus() {
        mHandler.removeMessages(MSG_HIDE_FOCUS);
        mFocusView.startFocus();
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_FOCUS, HIDE_FOCUS_DELAY);
    }

    public void autoFocus() {
        mHandler.removeMessages(MSG_HIDE_FOCUS);
        mFocusView.resetToDefaultPosition();
        mFocusView.startFocus();
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_FOCUS, 1000);
    }

    public void focusSuccess() {
        mFocusView.focusSuccess();
    }

    public void focuserror() {
        mFocusView.focuserror();
    }

    public void hideFocusUI() {
        //mFocusView.resetToDefaultPosition();
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                mFocusView.hideFocusView();
//            }
//        });
        mFocusView.hideFocusView();
    }

    public void removeDelayMessage() {
        mHandler.removeMessages(MSG_HIDE_FOCUS);
    }

    public MeteringRectangle getFocusArea(float x, float y, boolean isFocusArea) {
        currentX = x;
        currentY = y;
        if (isFocusArea) {
            return calcTapAreaForCamera2(mPreviewRect.width() / 5, 1000);
        } else {
            return calcTapAreaForCamera2(mPreviewRect.width() / 4, 1000);
        }
    }

    private MeteringRectangle calcTapAreaForCamera2(int areaSize, int weight) {
        int left = clamp((int) currentX - areaSize / 2,
                mPreviewRect.left, mPreviewRect.right - areaSize);
        int top = clamp((int) currentY - areaSize / 2,
                mPreviewRect.top, mPreviewRect.bottom - areaSize);
        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        toFocusRect(mTransformer.toCameraSpace(rectF));
        return new MeteringRectangle(mFocusRect, weight);
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    private RectF rectToRectF(Rect rect) {
        return new RectF(rect);
    }

    private void toFocusRect(RectF rectF) {
        mFocusRect.left = Math.round(rectF.left);
        mFocusRect.top = Math.round(rectF.top);
        mFocusRect.right = Math.round(rectF.right);
        mFocusRect.bottom = Math.round(rectF.bottom);
    }

    public void updateAFState(final int state) {
        Log.d("FocusView", "updateAFState:" + state);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                switch (state) {
                    case CaptureResult.CONTROL_AF_STATE_ACTIVE_SCAN:
                        startFocus();
                        break;
                    case CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED:
                        focusSuccess();
                        break;
                    case CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED:
                        focuserror();
                        break;
                    case CaptureResult.CONTROL_AF_STATE_PASSIVE_FOCUSED:
                        focusSuccess();
                        break;
                    case CaptureResult.CONTROL_AF_STATE_PASSIVE_SCAN:
                        autoFocus();
                        break;
                    case CaptureResult.CONTROL_AF_STATE_PASSIVE_UNFOCUSED:
                        focuserror();
                        break;
                    case CaptureResult.CONTROL_AF_STATE_INACTIVE:
                        hideFocusUI();
                        break;
                }
            }
        });
    }

}
