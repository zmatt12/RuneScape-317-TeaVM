package ui.tea;

import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import ui.IImage;
import ui.Window;

public class ImageDataImage implements IImage {

    private final HTMLCanvasElement renderCanvas;
    private final CanvasRenderingContext2D context;
    private final ImageData data;
    private final int[] pixels;

    public ImageDataImage(ImageData data) {
        renderCanvas = org.teavm.jso.browser.Window.current().getDocument().createElement("canvas").cast();
        renderCanvas.setHeight(data.getHeight());
        renderCanvas.setWidth(data.getWidth());
        this.context = renderCanvas.getContext("2d").cast();
        this.context.putImageData(data, 0, 0);
        this.data = data;
        this.pixels = new int[data.getData().getByteLength() / 4];
        getPixels();
    }

    public HTMLCanvasElement getRenderCanvas() {
        return renderCanvas;
    }

    public ImageData getData() {
        return data;
    }

    @Override
    public int getWidth(Window component) {
        return data.getWidth();
    }

    @Override
    public int getHeight(Window component) {
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
            int a = 0xFF; // TODO see if this needs to be hardcoded or not
            arr.set(i, r);
            arr.set(i + 1, g);
            arr.set(i + 2, b);
            arr.set(i + 3, a);
        }
        data.getData().set(arr);
    }

    public void updateData() {
        setPixels();
        context.putImageData(data, 0, 0);
    }
}
