package org.wysaid.filter.blend;

import org.wysaid.filter.base.BaseFilter;

public class CurveFilter extends BaseFilter {

    protected int[] rgb;
    protected int[] r;
    protected int[] g;
    protected int[] b;

    public CurveFilter(int[] curve) {
        if (curve.length > 0) rgb = curve;
    }

    public CurveFilter(int[][] curve) {
        if (curve != null) {
            if (curve.length > 0) rgb = curve[0];
            if (curve.length > 1) r = curve[1];
            if (curve.length > 2) g = curve[2];
            if (curve.length > 3) b = curve[3];
        }
    }

    private void appendData(StringBuilder config, int[] data, String prefix) {
        if (data != null && data.length > 0) {
            config.append(prefix);
            if (data[0] != 0) config.append("(0, ").append((int) (data[1] * intensity)).append(")");
            for (int i = 0; i < data.length / 2; i++) {
                config.append("(").append(data[i * 2]).append(", ")
                        .append((int) (data[i * 2 + 1] * intensity + (1 - intensity) * data[i * 2])).append(")");
            }
            if (data[data.length - 2] != 255) config.append("(255, ")
                    .append((int) (data[data.length - 1] * intensity + (1 - intensity) * 255)).append(")");
        }
    }

    @Override
    public String getConfig() {
        StringBuilder config = new StringBuilder("@curve ");
        appendData(config, r, "R");
        appendData(config, g, "G");
        appendData(config, b, "B");
        appendData(config, rgb, "RGB");
//        if (r != null && r.length > 0) {
//            config.append("R");
//            for (int i = 0; i < r.length / 2; i++) {
//                config.append("(").append(r[i * 2]).append(", ")
//                        .append(r[i * 2 + 1]).append(")");
//            }
//        }
        return config.toString();
    }
}
