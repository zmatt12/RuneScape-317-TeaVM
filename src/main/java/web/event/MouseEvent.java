package web.event;

public abstract class MouseEvent extends Event {

    public static final int TYPE_CLICKED = 0;
    public static final int TYPE_DRAGGED = 1;
    public static final int TYPE_ENTERED = 2;
    public static final int TYPE_EXITED = 3;
    public static final int TYPE_MOVED = 4;
    public static final int TYPE_PRESSED = 5;
    public static final int TYPE_RELEASED = 6;
    public static final int TYPE_WHEEL = 7;
    private final int x, y;

    public MouseEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean isRightMouseButton();

    @Override
    public final int getType() {
        return TYPE_MOUSE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
