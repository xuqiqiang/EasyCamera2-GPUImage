package org.wysaid.filter.origin;

import org.wysaid.filter.blend.EmbossFilter;

public class BlendEmbossFilter extends BlendFilter {

    public BlendEmbossFilter() {
        super("Emboss", new EmbossFilter());
    }
}
