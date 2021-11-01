package web.event;

public abstract class MouseEvent extends Event {

    public static final int TYPE_CLICKED = 500;
    public static final int TYPE_PRESSED = 501;
    public static final int TYPE_RELEASED = 502;
    public static final int TYPE_MOVED = 503;
    public static final int TYPE_ENTERED = 504;
    public static final int TYPE_EXITED = 505;
    public static final int TYPE_DRAGGED = 506;
    public static final int TYPE_WHEEL = 507;

    public static final int BUTTON_NONE = 0;
    public static final int BUTTON_ONE = 1;
    public static final int BUTTON_TWO = 2;
    public static final int BUTTON_THREE = 3;

    private final int x, y;

    private final int button;
    private final int clickCount;

    public MouseEvent(int x, int y, int button, int clickCount) {
        this.x = x;
        this.y = y;
        this.button = button;
        this.clickCount = clickCount;
    }

    public abstract boolean isRightMouseButton();

    @Override
    public final int getType() {
        return TYPE_MOUSE;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public boolean isPopupTrigger(){
        return false;
    }

    public final int getButton(){
        return button;
    }

    public final int getClickCount(){
        return clickCount;
    }
}
