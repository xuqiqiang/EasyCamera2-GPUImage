package org.wysaid.filter.origin;

import org.wysaid.filter.blend.CurveFilter;

public class CurveRedFilter extends BlendFilter {

    public CurveRedFilter() {
        super("CurveRed", new CurveFilter(new int[][]{{}, {48, 56, 82, 129, 130, 206, 214, 255},
                {7, 37, 64, 111, 140, 190, 232, 220}, {2, 97, 114, 153, 229, 172}}));
    }
}
