package org.wysaid.filter.origin;

import org.wysaid.filter.blend.SketchFilter;

public class BlendSketchFilter extends BlendFilter {

    public BlendSketchFilter() {
        super("Sketch", new SketchFilter(1f));
    }
}
