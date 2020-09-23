package org.wysaid.filter.base;

import static org.wysaid.filter.FilterUtils.a;
import static org.wysaid.filter.FilterUtils.b;
import static org.wysaid.filter.FilterUtils.g;
import static org.wysaid.filter.FilterUtils.r;

public class PixBlendFilter extends BaseFilter {

    protected String mMode;
    protected int mColor;
    protected int mAlpha;

    public PixBlendFilter(String mode, int color) {
        this(mode, color, 100);
    }

    public PixBlendFilter(String mode, int color, int alpha) {
        this.mMode = mode;
        this.mColor = color;
        this.mAlpha = alpha;
    }

    @Override
    public String getConfig() {
        return "@pixblend " + mMode + " " + r(mColor) + " " + g(mColor)
                + " " + b(mColor) + " " + a(mColor) + " " + (int) (mAlpha * intensity);
    }
}
