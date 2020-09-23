package org.wysaid.filter.origin;

import org.wysaid.filter.blend.BrightnessFilter;
import org.wysaid.filter.blend.ContrastFilter;

public class BlendBrooklynFilter extends BlendFilter {

    public BlendBrooklynFilter() {
        super("Brooklyn", new BrooklynFilter(),
                new ContrastFilter(0.9f), new BrightnessFilter(0.1f));
    }
}
