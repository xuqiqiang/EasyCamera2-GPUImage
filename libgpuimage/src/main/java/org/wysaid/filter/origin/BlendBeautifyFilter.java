package org.wysaid.filter.origin;

import org.wysaid.filter.base.BeautifyFilter;

public class BlendBeautifyFilter extends BlendFilter {

    public BlendBeautifyFilter() {
        super("Beautify", new BeautifyFilter(1f, false));
    }
}
