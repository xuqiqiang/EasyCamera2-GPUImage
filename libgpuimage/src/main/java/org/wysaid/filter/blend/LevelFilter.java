package org.wysaid.filter.blend;

import org.wysaid.filter.base.BaseFilter;

public class LevelFilter extends BaseFilter {

    protected float mDark;
    protected float mLight;
    protected float mGamma;
//    protected float mValue;

    public LevelFilter(float dark, float light, float gamma) {
        this.mDark = dark;
        this.mLight = light;
        this.mGamma = gamma;
//        this.mValue = mValue;
    }

    //    public LevelFilter(String name, float value) {
//        this(name, value, 1f);
//    }
//
//    public LevelFilter(String name, float value, float defaultValue) {
//        this.mName = name;
//        this.mValue = value;
//        this.mDefaultValue = defaultValue;
//    }

    @Override
    public String getConfig() {
        return "@adjust level " + mDark * intensity + " " + (1 + (mLight - 1) * intensity)
                + " " + (1 + (mGamma - 1) * intensity);
    }
}
