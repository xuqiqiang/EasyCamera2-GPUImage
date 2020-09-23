package org.wysaid.filter.origin;

import org.wysaid.filter.base.BaseFilter;

public class BlendFilter extends BaseFilter {

    private String mName;
    private BaseFilter[] mBaseFilters;

    public BlendFilter(String name, BaseFilter... filters) {
        this.mName = name;
        this.mBaseFilters = filters;
    }

    public String getName() {
        return mName;
    }

    @Override
    public void setIntensity(final float intensity) {
        super.setIntensity(intensity);
        if (mBaseFilters != null) {
            for (BaseFilter filter : mBaseFilters) {
                if (filter != null)
                    filter.setIntensity(intensity);
            }
        }
    }

    @Override
    public String getConfig() {
        StringBuilder config = new StringBuilder();
        if (mBaseFilters != null) {
            for (BaseFilter filter : mBaseFilters) {
                if (filter != null)
                    config.append(filter.getConfig()).append(" ");
            }
        }
        return config.toString().trim();
    }
}
