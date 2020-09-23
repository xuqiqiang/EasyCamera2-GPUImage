package org.wysaid.filter.blend;

import org.wysaid.filter.base.ValueFilter;

/**
 * Applies a simple contrast effect.
 */
public class ContrastFilter extends ValueFilter {

    public ContrastFilter(float value) {
        super("contrast", value);
    }
}
