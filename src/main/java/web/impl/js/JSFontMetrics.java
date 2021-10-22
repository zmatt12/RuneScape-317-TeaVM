package web.impl.js;

import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import web.IFont;
import web.IFontMetrics;
import web.ImmutableFont;

public class JSFontMetrics implements IFontMetrics {

    private final CanvasRenderingContext2D context;

    public JSFontMetrics(IFont font) {
        this((HTMLCanvasElement) Window.current().getDocument().createElement("canvas"));
        context.setFont(((ImmutableFont) font).toHtml());
    }

    public JSFontMetrics(HTMLCanvasElement canvas) {
        this((CanvasRenderingContext2D) canvas.getContext("2d").cast());
    }

    public JSFontMetrics(CanvasRenderingContext2D context) {
        this.context = context;
    }

    @Override
    public int stringWidth(String str) {
        return context.measureText(str).getWidth();
    }
}
