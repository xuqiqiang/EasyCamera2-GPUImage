package org.wysaid.filter.base;

public class ValueFilter extends BaseFilter {

    protected String mName;
    protected float mValue;
    protected float mDefaultValue;

    public ValueFilter(String name, float value) {
        this(name, value, 1f);
    }

    public ValueFilter(String name, float value, float defaultValue) {
        this.mName = name;
        this.mValue = value;
        this.mDefaultValue = defaultValue;
    }

    @Override
    public String getConfig() {
        return "@adjust " + mName + " " +
                (intensity * mValue + (1 - intensity) * mDefaultValue);
    }
}
