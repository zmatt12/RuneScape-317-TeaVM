package web.impl.js;

import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import web.IImage;
import web.Window;
import web.impl.jvm.impl.JVMComponent;

public class JSImage implements IImage<JVMComponent> {

    private final ImageData data;
    private final int[] pixels;

    public JSImage(ImageData data) {
        this.data = data;
        this.pixels = new int[data.getData().getByteLength() / 4];
        getPixels();
    }

    public ImageData getData() {
        return data;
    }

    @Override
    public int getWidth(Window<JVMComponent> component) {
        return data.getWidth();
    }

    @Override
    public int getHeight(Window<JVMComponent> component) {
        return data.getHeight();
    }

    @Override
    public int[] getBufferAsIntegers() {
        return pixels;
    }

    public void getPixels() {
        Uint8ClampedArray arr = data.getData();
        for (int i = 0; i < pixels.length; i++) {
            int offset = i * 4;
            int r = arr.get(offset);
            int g = arr.get(offset + 1);
            int b = arr.get(offset + 2);
            int a = arr.get(offset + 3);
            //argb
            pixels[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }
    }

    public void setPixels() {
        Uint8ClampedArray arr = Uint8ClampedArray.create(data.getData().getByteLength());
        for (int i = 0; i < arr.getByteLength(); i += 4) {
            int pixel = pixels[i / 4];
            int r = (pixel >> 16) & 0xFF;
            int g = (pixel >> 8) & 0xFF;
            int b = (pixel) & 0xFF;
            int a = 0xFF;
            arr.set(i, r);
            arr.set(i + 1, g);
            arr.set(i + 2, b);
            arr.set(i + 3, a);
        }
        data.getData().set(arr);
    }

    public void updateData() {
        setPixels();
    }
}
