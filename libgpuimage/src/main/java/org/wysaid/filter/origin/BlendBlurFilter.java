package org.wysaid.filter.origin;

import org.wysaid.filter.blend.BlurFilter;

public class BlendBlurFilter extends BlendFilter {

    public BlendBlurFilter() {
        super("Blur", new BlurFilter(1f));
    }
}
