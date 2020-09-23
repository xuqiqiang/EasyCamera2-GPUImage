package org.wysaid.filter.origin;

import org.wysaid.filter.blend.HueFilter;
import org.wysaid.filter.blend.SaturationFilter;
import org.wysaid.filter.blend.SepiaToneFilter;

/**
 * Applies a simple 1977 effect.
 */
public class Simple1977Filter extends BlendFilter {

    public Simple1977Filter() {
        super("1977", new SepiaToneFilter(0.5f),
                new SaturationFilter(1.4f), new HueFilter(5.75959f));
    }
}
