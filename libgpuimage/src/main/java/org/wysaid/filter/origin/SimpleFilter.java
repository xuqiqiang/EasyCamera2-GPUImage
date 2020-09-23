package org.wysaid.filter.origin;

public class SimpleFilter extends BlendFilter {

    private String mConfig;

    public SimpleFilter(String name, String config) {
        super(name);
        mConfig = config;
    }

    @Override
    public String getConfig() {
        return mConfig;
    }
}
