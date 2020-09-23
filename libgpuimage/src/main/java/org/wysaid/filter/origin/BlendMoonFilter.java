package org.wysaid.filter.origin;

import org.wysaid.filter.base.ColorMatrixFilter;
import org.wysaid.filter.base.PixBlendFilter;
import org.wysaid.filter.blend.BrightnessFilter;
import org.wysaid.filter.blend.ContrastFilter;

public class BlendMoonFilter extends BlendFilter {

    public BlendMoonFilter() {
        super("Moon", new PixBlendFilter("softlight", 0xffa0a0a0),
                new PixBlendFilter("lighten", 0xff383838),
                new ColorMatrixFilter(new float[]{0.3086f, 0.6094f, 0.082f,
                        0.3086f, 0.6094f, 0.082f,
                        0.3086f, 0.6094f, 0.082f}),
                new ContrastFilter(1.1f), new BrightnessFilter(0.1f));
    }
}
