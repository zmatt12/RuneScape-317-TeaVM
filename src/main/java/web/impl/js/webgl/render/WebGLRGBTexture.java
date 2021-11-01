package web.impl.js.webgl.render;

import client.textures.IndexedTexture;
import client.textures.RGBTexture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teavm.jso.typedarrays.DataView;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import org.teavm.jso.webgl.WebGLFramebuffer;
import org.teavm.jso.webgl.WebGLRenderingContext;
import org.teavm.jso.webgl.WebGLTexture;

import java.util.Arrays;

public class WebGLRGBTexture extends RGBTexture {

    private static final Logger logger = LoggerFactory.getLogger(WebGLRGBTexture.class);

    private final WebGLRenderingContext gl;
    private final WebGLTexture texture;
    private final Uint8ClampedArray pixels;
    private final DataView pixView;

    private final WebGLFramebuffer fb;

    public WebGLRGBTexture(WebGLRenderingContext gl, int width, int height) {
        this.gl = gl;
        this.pixels = Uint8ClampedArray.create(width * height * 4);
        this.pixView = DataView.create(pixels.getBuffer());
        this.texture = gl.createTexture();
        this.fb = gl.createFramebuffer();
        setSize(width, height);

        gl.bindTexture(gl.TEXTURE_2D, texture);
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR);

        gl.texImage2D(gl.TEXTURE_2D, // target
                0, // level
                gl.RGBA, // internal format
                width,
                height,
                0, // border
                gl.RGBA, //srcFormat
                gl.UNSIGNED_BYTE, // srcType
                pixels // pixels
        );


        WebGLFramebuffer current = gl.getParameter(gl.FRAMEBUFFER_BINDING).cast();
        gl.bindFramebuffer(gl.FRAMEBUFFER, fb);
        gl.framebufferTexture2D(
                gl.FRAMEBUFFER, gl.COLOR_ATTACHMENT0,
                gl.TEXTURE_2D, texture, 0);
        gl.bindFramebuffer(gl.FRAMEBUFFER, current);
    }

    @Override
    public void translate(int r, int g, int b) {
        logger.info("translate({}, {}, {})", r, g, b);
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void draw(int x, int y) {
        logger.info("draw({}, {})", x, y);
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public int[] getPixels() {
        WebGLFramebuffer current = gl.getParameter(gl.FRAMEBUFFER_BINDING).cast();
        WebGLFramebuffer tmp = gl.createFramebuffer();
        gl.bindFramebuffer(gl.FRAMEBUFFER, tmp);
        gl.readPixels(0,0,getWidth(), getHeight(), gl.RGBA, gl.UNSIGNED_BYTE, pixels);
        int[] res = new int[getWidth() * getHeight()];
        for(int i = 0; i < res.length; i++){
            int pixel = pixView.getInt32(i * 4) >> 8;
            pixel |= 0xFF0000;
            res[i] = pixel;
        }
        gl.bindFramebuffer(gl.FRAMEBUFFER, current);
        return res;
    }

    @Override
    public void bind() {
        logger.info("bind()");
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void crop() {
        logger.info("crop()");
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void fill(int rgb) {
        logger.info("fill({})", rgb);
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void blitOpaque(int x, int y) {
        logger.info("blitOpaque({}, {})", x, y);
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void draw(int x, int y, int alpha) {
        logger.info("draw({}, {}, {})", x, y, alpha);
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void drawRotatedMasked(int x, int y, int width, int height, int anchorX, int anchorY, int zoom, int angle,
                                  int[] lineLengths, int[] lineOffsets) {
        logger.info("drawRotatedMasked({}, {}, {}, {}, {}, {}, {}, {}, {}, {})", x, y, width, height, anchorX, anchorY,
                zoom, angle, Arrays.toString(lineLengths), Arrays.toString(lineOffsets));
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void drawRotated(int x, int y, int width, int height, int anchorX, int anchorY, double radian, int zoom) {
        logger.info("drawRotated({}, {}, {}, {}, {}, {}, {}, {})", x, y, width, height, anchorX, anchorY, radian, zoom);
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void drawMasked(IndexedTexture mask, int x, int y) {
        logger.info("drawMasked({}, {}, {})", mask, x, y);
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void setPixels(int[] pixels) {
        logger.info("setPixels({})", pixels);
        throw new UnsupportedOperationException("Unimplemented");
    }
}
