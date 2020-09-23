package org.wysaid.filter.base;

import java.io.Serializable;

public abstract class BaseFilter implements Serializable {

    private static final long serialVersionUID = 1L;
    //    protected String mFilterName;
    protected float intensity = 1f;

//    public BaseFilter(String name) {
//        this.mFilterName = name;
//    }
//
//    public String getFilterName() {
//        return mFilterName;
//    }

    public void setIntensity(final float intensity) {
        this.intensity = intensity;
    }

    public abstract String getConfig();

    // for test
//    public void applyFilterWithConfig(GLSurfaceView glSurfaceView) {
//        if (glSurfaceView instanceof CameraGLSurfaceViewWithTexture) {
//            ((CameraGLSurfaceViewWithTexture) glSurfaceView).setFilterWithConfig(getConfig());
//        } else if (glSurfaceView instanceof ImageGLSurfaceView) {
//            ((ImageGLSurfaceView) glSurfaceView).setFilterWithConfig(getConfig());
//        }
//    }

    @Override
    public String toString() {
        return getConfig();
    }
}
