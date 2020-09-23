package org.wysaid.filter.blend;

import org.wysaid.filter.base.ValueFilter;

/**
 * Applies a simple exposure effect.
 */
public class ExposureFilter extends ValueFilter {

    public ExposureFilter(float value) {
        super("exposure", value, -1f);
    }
}
