package org.wysaid.filter.origin;

import org.wysaid.filter.blend.CurveFilter;
import org.wysaid.filter.blend.EdgeFilter;
import org.wysaid.filter.blend.LevelFilter;

public class BlendEdgeGrayFilter extends BlendFilter {

    public BlendEdgeGrayFilter() {
        super("EdgeGray", new EdgeFilter(), new CurveFilter(new int[]{0, 255, 255, 0}),
                new LevelFilter(0.33f, 0.71f, 0.93f));
    }
}
