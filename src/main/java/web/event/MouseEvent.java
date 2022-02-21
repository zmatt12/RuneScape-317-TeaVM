package web.event;

public abstract class MouseEvent<T> extends Event {

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

    private final T source;

    public MouseEvent(T source){
        this.source = source;
    }

    public T getSource(){
        return source;
    }

    public abstract boolean isRightMouseButton();

    public abstract boolean isMiddleMouseButton();

    @Override
    public final int getType() {
        return TYPE_MOUSE;
    }

    public abstract int getX();

    public abstract int getY();

    public boolean isPopupTrigger(){
        return false;
    }

    public abstract int getButton();

    public abstract int getClickCount();

    public abstract int getWheelRotation();
}
