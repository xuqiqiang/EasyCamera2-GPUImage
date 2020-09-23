package org.wysaid.filter.origin;

import org.wysaid.filter.blend.WaveFilter;

public class BlendWaveFilter extends BlendFilter {

    public BlendWaveFilter() {
        super("Wave", new WaveFilter(1f));
    }
}
