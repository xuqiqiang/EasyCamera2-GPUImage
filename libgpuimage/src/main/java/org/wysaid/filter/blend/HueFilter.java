package org.wysaid.filter.blend;

import org.wysaid.filter.base.ValueFilter;

/**
 * Applies a simple hue effect.
 */
public class HueFilter extends ValueFilter {

    public HueFilter(float value) {
        super("hue", value, value < Math.PI ? 0 : (float) (Math.PI * 2));
    }
}
