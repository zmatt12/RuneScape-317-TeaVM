package web.impl.webassembly.modules;

import org.teavm.interop.Import;

public final class ComponentModule {

    public static final String MODULE_NAME = "component";

    private ComponentModule(){

    }

    @Import(module = MODULE_NAME, name = "create")
    public static native int createComponent();

    @Import(module = MODULE_NAME, name = "setColor")
    public static native void setColor(int id, String hexCode);

    @Import(module = MODULE_NAME, name = "drawRect")
    public static native void drawRect(int id, int x, int y, int width, int height);

    @Import(module = MODULE_NAME, name = "fillRect")
    public static native void filLRect(int id, int x, int y, int width, int height);

    @Import(module = MODULE_NAME, name = "drawString")
    public static native void drawString(int id, String str, int x, int y);

    @Import(module = MODULE_NAME, name = "getHeight")
    public static native int getHeight(int id);

    @Import(module = MODULE_NAME, name = "getWidth")
    public static native int getWidth(int id);

    @Import(module = MODULE_NAME, name = "setSize")
    public static native void setSize(int id, int width, int height);

    @Import(module = MODULE_NAME, name = "requestFocus")
    public static native void requestFocus(int id);

    @Import(module = MODULE_NAME, name = "setFont")
    public static native void setFont(int id, String font);

    @Import(module = MODULE_NAME, name = "drawPixelsRGB")
    public static native void drawPixels(int id, int x, int y, int width, int height, int[] rgbPixels);
}
