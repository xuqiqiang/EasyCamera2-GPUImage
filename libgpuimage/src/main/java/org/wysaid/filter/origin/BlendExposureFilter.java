package org.wysaid.filter.origin;

import org.wysaid.filter.blend.ExposureFilter;

public class BlendExposureFilter extends BlendFilter {

    public BlendExposureFilter() {
        super("Exposure", new ExposureFilter(1f));
    }
}
