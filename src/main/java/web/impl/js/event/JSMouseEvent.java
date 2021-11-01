package web.impl.js.event;

import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.TextRectangle;
import web.event.MouseEvent;

public class JSMouseEvent extends MouseEvent<HTMLCanvasElement> {

    private static final int[] BUTTON_MAP = {BUTTON_ONE, BUTTON_TWO, BUTTON_THREE};

    private final int type;
    private final org.teavm.jso.dom.events.MouseEvent event;
    private final int x, y;

    public JSMouseEvent(HTMLCanvasElement source, int type, org.teavm.jso.dom.events.MouseEvent event) {
        super(source);
        this.type = type;
        this.event = event;

        TextRectangle bounds = source.getBoundingClientRect();

        int x = event.getClientX() - bounds.getLeft();
        int y = event.getClientY() - bounds.getTop();

        if(bounds.getWidth() != source.getWidth()){
            double scale = source.getWidth();
            scale /= bounds.getWidth();
            x *= scale;
        }

        if(bounds.getHeight() != source.getHeight()){
            double scale = source.getHeight();
            scale /= bounds.getHeight();
            y *= scale;
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public int getEventType() {
        return type;
    }

    @Override
    public boolean isRightMouseButton() {
        return getButton() == BUTTON_THREE;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getButton() {
        int button = event.getButton();
        if(button < BUTTON_MAP.length){
            return BUTTON_MAP[button];
        }
        return button;
    }

    @Override
    public int getClickCount() {
        return 1;
    }

    @Override
    public int getWheelRotation() {
        return 0; //TODO implement
    }
}
