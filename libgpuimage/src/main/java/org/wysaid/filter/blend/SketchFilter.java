package org.wysaid.filter.blend;

import org.wysaid.filter.base.BaseFilter;

public class SketchFilter extends BaseFilter {

    protected float mValue;

    public SketchFilter(float value) {
        this.mValue = value;
    }

    @Override
    public String getConfig() {
        return "#unpack @style sketch " + intensity * mValue;
    }
}
