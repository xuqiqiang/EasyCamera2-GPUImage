package org.wysaid.filter.blend;

import org.wysaid.filter.base.ColorMatrixFilter;

/**
 * Applies a simple sepia effect.
 */
public class SepiaToneFilter extends ColorMatrixFilter {

    public SepiaToneFilter(float value) {
        super(new float[]{
                0.393f, 0.769f, 0.198f,
                0.349f, 0.686f, 0.168f,
                0.272f, 0.534f, 0.131f,
        }, value);
    }
}
