package org.wysaid.filter.origin;

import org.wysaid.filter.blend.SharpenFilter;

public class BlendVagueFilter extends BlendFilter {

    public BlendVagueFilter() {
        super("Sharpen", new SharpenFilter(0));
    }
}
