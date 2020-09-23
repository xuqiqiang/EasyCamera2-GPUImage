package org.wysaid.filter.origin;

import org.wysaid.filter.base.BeautifyFilter;

public class BlendBeautifyBilateralFilter extends BlendFilter {

    public BlendBeautifyBilateralFilter() {
        super("BeautifyBilateral", new BeautifyFilter(1f, true));
    }
}
