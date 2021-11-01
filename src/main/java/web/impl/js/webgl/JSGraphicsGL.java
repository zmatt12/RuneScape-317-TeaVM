package web.impl.js.webgl;

import client.textures.Renderer;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.webgl.*;
import web.IFont;
import web.IGraphics;
import web.IImage;
import web.impl.js.JSImage;
import web.impl.js.webgl.render.WebGLRenderer;
import web.util.Color;
import web.util.WebGL;

public class JSGraphicsGL implements IGraphics {

    private final HTMLCanvasElement canvas;
    private final WebGLRenderer renderer;

    private float[] color = Color.black.asFloatArray();

    public JSGraphicsGL(HTMLCanvasElement canvas, WebGLRenderingContext context) {
        this.canvas = canvas;
        renderer = new WebGLRenderer(context);
    }

    @Override
    public void setColor(Color color) {
        this.color = color.asFloatArray();
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        renderer.drawRect(color, x, y, width, height, canvas.getWidth(), canvas.getHeight(), true);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        renderer.drawRect(color, x, y, width, height, canvas.getWidth(), canvas.getHeight(), false);
    }

    @Override
    public void setFont(IFont font) {

    }

    @Override
    public void drawString(String str, int x, int y) {
        //TODO impl, or at least figure out how I want to implement it
    }

    @Override
    public void drawImage(IImage img, int x, int y, Object observer) {
        renderer.drawImage((JSImage) img, x, y, canvas.getWidth(), canvas.getHeight());
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
