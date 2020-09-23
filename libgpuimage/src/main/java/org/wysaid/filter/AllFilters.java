package org.wysaid.filter;

import org.wysaid.filter.origin.Blend1977Filter;
import org.wysaid.filter.origin.BlendBeautifyBilateralFilter;
import org.wysaid.filter.origin.BlendBeautifyFilter;
import org.wysaid.filter.origin.BlendBlurFilter;
import org.wysaid.filter.origin.BlendBrooklynFilter;
import org.wysaid.filter.origin.BlendEdgeFilter;
import org.wysaid.filter.origin.BlendEdgeGrayFilter;
import org.wysaid.filter.origin.BlendEmbossFilter;
import org.wysaid.filter.origin.BlendExposureFilter;
import org.wysaid.filter.origin.BlendFilter;
import org.wysaid.filter.origin.BlendMoonFilter;
import org.wysaid.filter.origin.BlendSharpenFilter;
import org.wysaid.filter.origin.BlendSketchFilter;
import org.wysaid.filter.origin.BlendVagueFilter;
import org.wysaid.filter.origin.BlendWaveFilter;
import org.wysaid.filter.origin.CurveRedFilter;
import org.wysaid.filter.origin.NoneFilter;
import org.wysaid.filter.origin.Simple1977Filter;

/**
 * 滤镜的集合
 */
public class AllFilters {

    public static final BlendFilter FILTERS[] = {
            new NoneFilter(),
            new Simple1977Filter(),
            new Blend1977Filter(),
            new BlendBrooklynFilter(),
            new BlendMoonFilter(),
            new BlendBeautifyFilter(),
            new BlendBeautifyBilateralFilter(),
            new BlendExposureFilter(),
            new BlendBlurFilter(),
            new BlendWaveFilter(),
            new BlendSketchFilter(),
            new BlendEmbossFilter(),
            new BlendEdgeFilter(false),
            new BlendEdgeFilter(true),
            new BlendEdgeGrayFilter(),
            new CurveRedFilter(),
            new BlendSharpenFilter(),
            new BlendVagueFilter(),
            new BlendFilter("更多"),
    };
}
