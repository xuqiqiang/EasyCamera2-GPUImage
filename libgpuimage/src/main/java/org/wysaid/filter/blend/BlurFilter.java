package org.wysaid.filter.blend;

import org.wysaid.filter.base.BaseFilter;

public class BlurFilter extends BaseFilter {

    protected float mValue;

    public BlurFilter(float value) {
        this.mValue = value;
    }

    @Override
    public String getConfig() {
        return "@blur lerp " + intensity * mValue;
    }
}
