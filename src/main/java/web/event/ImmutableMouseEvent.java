package web.event;

public class ImmutableMouseEvent extends MouseEvent {

    private final int type;
    private final boolean right;

    public ImmutableMouseEvent(int type, int x, int y) {
        this(type, x, y, MouseEvent.BUTTON_ONE, 1, false);
    }

    public ImmutableMouseEvent(int type, int x, int y, int button, int count, boolean right) {
        super(x, y, button, count);
        this.type = type;
        this.right = right;
    }

    @Override
    public int getEventType() {
        return type;
    }

    @Override
    public boolean isRightMouseButton() {
        return right;
    }

    @Override
    public String toString() {
        return "ImmutableMouseEvent{" +
                "type=" + type +
                ", right=" + right +
                ", x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
