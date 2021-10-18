package ui.tea;

import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import ui.IFont;
import ui.IFontMetrics;
import ui.ImmutableFont;

public class HtmlFontMetrics implements IFontMetrics {

    private final HTMLCanvasElement canvas;
    private final CanvasRenderingContext2D context;

    public HtmlFontMetrics(IFont font) {
        this((HTMLCanvasElement)Window.current().getDocument().createElement("canvas"));
        context.setFont(((ImmutableFont)font).toHtml());
    }

    public HtmlFontMetrics(HTMLCanvasElement canvas){
        this(canvas, canvas.getContext("2d").cast());
    }

    public HtmlFontMetrics(HTMLCanvasElement canvas, CanvasRenderingContext2D context){
        this.canvas = canvas;
        this.context = context;
    }

    @Override
    public int stringWidth(String str) {
        return context.measureText(str).getWidth();
    }
}
