package org.wysaid.filter;

public class FilterUtils {

    public static int a(int color) {
        return color >> 24 & 0xFF;
    }

    public static int r(int color) {
        return color >> 16 & 0xFF;
    }

    public static int g(int color) {
        return color >> 8 & 0xFF;
    }

    public static int b(int color) {
        return color & 0xFF;
    }

    public static int argb(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public static int rgb(int red, int green, int blue) {
        return 255 << 24 | red << 16 | green << 8 | blue;
    }
}
