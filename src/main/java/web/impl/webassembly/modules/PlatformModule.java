package web.impl.webassembly.modules;

import org.teavm.interop.Import;

public final class PlatformModule {

    public static final String MODULE_NAME = "platform";

    private PlatformModule(){

    }

    @Import(module = MODULE_NAME, name = "getCodebase")
    public static native String getCodebase();

    @Import(module = MODULE_NAME, name = "createImage")
    public static native int createImage(byte[] data);

    @Import(module = MODULE_NAME, name = "getImageWidth")
    public static native int getImageWidth(int image);

    @Import(module = MODULE_NAME, name = "getImageHeight")
    public static native int getImageHeight(int image);

    @Import(module = MODULE_NAME, name = "getImagePixels")
    public static native int[] getImagePixels(int image);

    @Import(module = MODULE_NAME, name = "freeImage")
    public static native void freeImage(int image);
}
