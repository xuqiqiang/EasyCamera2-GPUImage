package org.wysaid.filter.blend;

import org.wysaid.filter.base.BaseFilter;

public class EmbossFilter extends BaseFilter {

    @Override
    public String getConfig() {
        return "#unpack @style emboss " + intensity + " 2 2";
    }
}
