package ui.tea;

import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import ui.IFont;
import ui.IGraphics;
import ui.IImage;
import ui.ImmutableFont;
import ui.poly.Color;

public class HtmlGraphics implements IGraphics {

    private final CanvasRenderingContext2D context;

    public HtmlGraphics(CanvasRenderingContext2D context) {
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
        //TODO scale the font size based on resolution, right now we just use the size as a pixel value
        ImmutableFont f = (ImmutableFont) font;
        context.setFont(f.toHtml());
    }

    @Override
    public void drawString(String str, int x, int y) {
        context.strokeText(str, x, y);
    }


    @Override
    public void drawImage(IImage img, int x, int y, Object observer) {
        ImageDataImage i = (ImageDataImage) img;
        HTMLCanvasElement canvas = i.getRenderCanvas();
        i.updateData();
        context.drawImage(canvas, x, y, img.getWidth(), img.getHeight());
    }
}
