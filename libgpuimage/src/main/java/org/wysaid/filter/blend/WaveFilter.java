package org.wysaid.filter.blend;

import org.wysaid.filter.base.BaseFilter;

public class WaveFilter extends BaseFilter {

    protected float mValue;

    public WaveFilter(float value) {
        this.mValue = value;
    }

    @Override
    public String getConfig() {
        return "@dynamic wave " + intensity * mValue;
    }
}
