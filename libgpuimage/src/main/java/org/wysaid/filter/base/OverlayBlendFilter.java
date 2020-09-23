package org.wysaid.filter.base;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

public abstract class OverlayBlendFilter extends BaseFilter {

    protected String mMode;
    protected String mName;
    protected int mAlpha;
    private boolean hasFilterBitmap;

    public OverlayBlendFilter(String mode, String name) {
        this(mode, name, 100);
    }

    public OverlayBlendFilter(String mode, String name, int alpha) {
        this.mMode = mode;
        this.mName = name;
        this.mAlpha = alpha;
    }

    public static void saveBitmap(Bitmap bitmap, File file) {
//        String filePath = mFilterBrooklyn;
        File galleryDir = file.getParentFile();
        if (!galleryDir.exists()) {
            try {
                galleryDir.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file, false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getConfig() {
        return "@blend " + mMode + " file://" + (int) (mAlpha * intensity);
    }

    // for test
//    @Override
//    public void applyFilterWithConfig(GLSurfaceView glSurfaceView) {
//        String filePath = Environment.getExternalStorageDirectory().getPath()
//                + Constants.PATH + File.separator + mName + ".png";
//
//        File file = new File(filePath);
//
//        boolean needRecreate = false;
//        if (!hasFilterBitmap) {
//            if (file.exists()) {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(filePath, options);
//                if (options.outWidth != glSurfaceView.getWidth()
//                        || options.outHeight != glSurfaceView.getHeight()) {
//                    file.delete();
//                    needRecreate = true;
//                }
//            } else {
//                needRecreate = true;
//            }
//            hasFilterBitmap = true;
//        }
//
//        if (needRecreate) {
//            saveBitmap(filterBitmap(glSurfaceView.getWidth(), glSurfaceView.getHeight()), file);
//        }
//        super.applyFilterWithConfig(glSurfaceView);
//    }

    protected abstract Bitmap filterBitmap(int width, int height);
}
