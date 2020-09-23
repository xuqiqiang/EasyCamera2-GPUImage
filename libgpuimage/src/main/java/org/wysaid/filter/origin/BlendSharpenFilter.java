package org.wysaid.filter.origin;

import org.wysaid.filter.blend.SharpenFilter;

public class BlendSharpenFilter extends BlendFilter {

    public BlendSharpenFilter() {
        super("Sharpen", new SharpenFilter(10));
    }
}
