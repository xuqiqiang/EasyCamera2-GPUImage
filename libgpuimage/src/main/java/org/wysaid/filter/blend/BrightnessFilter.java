package org.wysaid.filter.blend;

import org.wysaid.filter.base.ValueFilter;

/**
 * Applies a simple sepia effect.
 */
public class BrightnessFilter extends ValueFilter {

    public BrightnessFilter(float value) {
        super("brightness", value, 0);
    }
}
