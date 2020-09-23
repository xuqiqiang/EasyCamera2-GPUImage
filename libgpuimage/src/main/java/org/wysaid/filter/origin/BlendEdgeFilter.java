package org.wysaid.filter.origin;

import org.wysaid.filter.blend.CurveFilter;
import org.wysaid.filter.blend.EdgeFilter;

public class BlendEdgeFilter extends BlendFilter {

    public BlendEdgeFilter(boolean curve) {
        super(curve ? "EdgeCurve" : "Edge", new EdgeFilter(),
                curve ? new CurveFilter(new int[]{0, 255, 255, 0}) : null);
    }
}
