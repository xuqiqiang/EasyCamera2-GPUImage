package org.wysaid.filter.blend;

import org.wysaid.filter.base.ValueFilter;

/**
 * Applies a simple saturation effect.
 */
public class SaturationFilter extends ValueFilter {

    public SaturationFilter(float value) {
        super("saturation", value);
    }
}
