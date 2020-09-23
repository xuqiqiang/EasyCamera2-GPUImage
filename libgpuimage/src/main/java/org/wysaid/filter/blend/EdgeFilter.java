package org.wysaid.filter.blend;

import org.wysaid.filter.base.BaseFilter;

public class EdgeFilter extends BaseFilter {

//    protected boolean curve;
//
//    public EdgeFilter(boolean curve) {
//        this.curve = curve;
//    }
//
//    @Override
//    public String getConfig() {
//        return "@style edge " + intensity + " 2"
//                + (curve ? "@curve RGB(0, 255)(255, 0)" : "");
//    }

    @Override
    public String getConfig() {
        return "@style edge " + intensity + " 2";
    }
}
