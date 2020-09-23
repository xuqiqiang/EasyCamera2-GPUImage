package org.wysaid.filter.blend;

import org.wysaid.filter.base.ValueFilter;

/**
 * Applies a simple sharpen effect.
 */
public class SharpenFilter extends ValueFilter {

    public SharpenFilter(float value) {
        super(value > 1 ? "sharpen" : "blur", value > 1 ? value : (1 - value), value > 1 ? 1f : 0);
    }

    @Override
    public String getConfig() {
        return super.getConfig() + " 1.5";
    }
}
