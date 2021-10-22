package web.impl.js;

import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import web.IFont;
import web.IGraphics;
import web.IImage;
import web.ImmutableFont;
import web.util.Color;

public class JSGraphics implements IGraphics {

    private final CanvasRenderingContext2D context;

    public JSGraphics(CanvasRenderingContext2D context) {
        this.context = context;
    }

    @Override
    public void setColor(Color color) {
        String hex = color.toHex();
        context.setFillStyle(hex);
        context.setStrokeStyle(hex);
        context.setShadowColor("transparent");
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        context.fillRect(x, y, width, height);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        context.strokeRect(x, y, width, height);
    }

    @Override
    public void setFont(IFont font) {
        ImmutableFont f = (ImmutableFont) font;
        context.setFont(f.toHtml());
    }

    @Override
    public void drawString(String str, int x, int y) {
        context.fillText(str, x, y);
    }


    @Override
    public void drawImage(IImage img, int x, int y, Object observer) {
        JSImage i = (JSImage) img;
        i.updateData();
        context.putImageData(i.getData(), x, y);
    }
}
