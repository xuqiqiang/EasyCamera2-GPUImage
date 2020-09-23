package org.wysaid.filter.origin;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import org.wysaid.filter.base.OverlayBlendFilter;
import org.wysaid.filter.base.PixBlendFilter;
import org.wysaid.filter.blend.BrightnessFilter;
import org.wysaid.filter.blend.ContrastFilter;
import org.wysaid.filter.blend.SaturationFilter;

public class BrooklynFilter extends OverlayBlendFilter {

    public BrooklynFilter() {
        super("overlay", "brooklyn");
    }

    @Override
    protected Bitmap filterBitmap(int width, int height) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap bitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        float radius = (float) (Math.sqrt(bitmap.getWidth() * bitmap.getWidth()
                + bitmap.getHeight() * bitmap.getHeight()) / 2f);

        int [] colors = {0x66A8DFC1,0x66A8DFC1, 0xffc4b7c8};
        float[] position = new float[3];
        position[0] = 0f;
        position[1] = 0.7f;
        position[2] = 1f;
        RadialGradient radialGradient = new RadialGradient(bitmap.getWidth() / 2f,
                bitmap.getHeight() / 2f, radius*1f,colors,position, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(bitmap.getWidth() / 2,
                bitmap.getHeight() / 2, radius*1f, paint);

        return bitmap;
    }
}
