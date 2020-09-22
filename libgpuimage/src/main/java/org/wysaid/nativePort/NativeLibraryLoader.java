package org.wysaid.nativePort;

public class NativeLibraryLoader {

    public static void load() {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("CGE");
        System.loadLibrary("CGEExt");
        CGEFFmpegNativeLibrary.avRegisterAll();
    }

}
