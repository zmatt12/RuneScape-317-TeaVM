package web.impl.js;

import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.typedarrays.DataView;
import web.IImage;
import web.Window;
import web.impl.jvm.impl.JVMComponent;

public class JSImage implements IImage<JVMComponent> {

    private final ImageData data;
    private final int[] pixels;
    private final DataView view;

    public JSImage(ImageData data) {
        this.data = data;
        this.view = DataView.create(data.getData().getBuffer());
        this.pixels = new int[data.getData().getByteLength() / 4];
        fetchPixels();
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

    private void fetchPixels() {
        //convert to RGB for the client
        for (int i = 0; i < pixels.length; i++) {
            int pixel = (view.getInt32(i * 4) >>> 8);
            pixels[i] = pixel;
        }
    }

    public void updateData() {
        //convert to RGBA for rendering
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            pixel <<= 8;
            pixel |= 0xFF; // alpha
            view.setInt32(i * 4, pixel);
        }
    }
}
