package org.wysaid.filter.base;

public class BeautifyFilter extends BaseFilter {

    protected float mValue;
    protected boolean isBilateral;

//    public BeautifyFilter(String name, float value) {
//        this(name, value, 1f);
//    }

    public BeautifyFilter(float value, boolean bilateral) {
        this.mValue = value;
        this.isBilateral = bilateral;
    }

    @Override
    public String getConfig() {
        if (isBilateral)
            return "@beautify bilateral 100 3.5 " + mValue * intensity * 2;
        return "@beautify face " + mValue * intensity + " 480 640";
    }
}
