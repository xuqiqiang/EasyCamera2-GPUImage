package org.wysaid.filter.origin;

import org.wysaid.filter.base.PixBlendFilter;
import org.wysaid.filter.blend.BrightnessFilter;
import org.wysaid.filter.blend.ContrastFilter;
import org.wysaid.filter.blend.SaturationFilter;

/**
 * Applies a simple 1977 effect.
 */
public class Blend1977Filter extends BlendFilter {

    public Blend1977Filter() {
        super("1977mix", new PixBlendFilter("screen", 0x4df36abc),
                new ContrastFilter(1.1f), new BrightnessFilter(0.1f),
                new SaturationFilter(1.3f));
    }
}
