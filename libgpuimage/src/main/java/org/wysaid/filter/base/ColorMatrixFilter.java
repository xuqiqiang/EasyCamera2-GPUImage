package org.wysaid.filter.base;

public class ColorMatrixFilter extends BaseFilter {

    protected float mValue;
    private float[] colorMatrix;

    public ColorMatrixFilter(final float[] colorMatrix) {
        this(colorMatrix, 1f);
    }

    public ColorMatrixFilter(final float[] colorMatrix, float value) {
        this.colorMatrix = colorMatrix;
        this.mValue = value;
    }

    @Override
    public String getConfig() {
        StringBuilder config = new StringBuilder();
        if (colorMatrix != null) {
            config.append("@colormul mat ");
            for (int i = 0; i < colorMatrix.length; i += 1) {
                if (i % 3 == i / 3)
                    config.append(1 + (colorMatrix[i] - 1) * mValue * intensity).append(" ");
                else
                    config.append(colorMatrix[i] * mValue * intensity).append(" ");
            }
        }
        return config.toString().trim();
    }
}
