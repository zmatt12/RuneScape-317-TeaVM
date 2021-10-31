package web.impl.js;

import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.DataView;
import org.teavm.jso.typedarrays.Int8Array;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import org.teavm.jso.webgl.WebGLRenderingContext;
import org.teavm.jso.webgl.WebGLTexture;
import web.IImage;
import web.Window;
import web.impl.jvm.impl.JVMComponent;

public class JSImage implements IImage<JVMComponent> {

    private final ImageData data; // the data we are rendering (RGBA)
    private final int[] pixels; // the pixels that the game expects to be in RGB
    private final DataView view; // used with the data to set the correct values
    private WebGLTexture texture;

    public JSImage(ImageData data) {
        this.data = data;
        this.view = DataView.create(data.getData());
        this.pixels = new int[data.getData().getByteLength() / 4];
        fetchPixels();
    }

    public JSImage(int[] pixels, int width, int height){
        this.data = JSMethods.createImageData(width, height);
        this.view = DataView.create(data.getData());
        this.pixels = pixels;
        //don't need to fetch the pixel values, as they are provided by the game
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
            pixels[i] = (view.getInt32(i * 4) >>> 8);
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

    public WebGLTexture getTexture(WebGLRenderingContext gl){
        return getTexture(gl, true);
    }

    public WebGLTexture getTexture(WebGLRenderingContext gl, boolean updateData){
        if(texture == null){
            texture = gl.createTexture();
            gl.bindTexture(gl.TEXTURE_2D, texture);
            // let's assume all images are not a power of 2
            gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
            gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
            gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR);
        }

        if(updateData){
            updateData();
            gl.bindTexture(gl.TEXTURE_2D, texture);
            gl.texImage2D(gl.TEXTURE_2D, // target
                    0, // level
                    gl.RGBA, // internal format
                    getWidth(),
                    getHeight(),
                    0, // border
                    gl.RGBA, //srcFormat (not actually RGBA, but we'll swap that in the shader)
                    gl.UNSIGNED_BYTE, // srcType
                    data.getData() // pixels
                    );
        }

        return texture;
    }
}
