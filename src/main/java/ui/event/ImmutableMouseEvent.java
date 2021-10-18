package ui.event;

public class ImmutableMouseEvent extends MouseEvent {

    private final int type;
    private final boolean right;

    public ImmutableMouseEvent(int type, int x, int y) {
        this(type, x, y, false);
    }

    public ImmutableMouseEvent(int type, int x, int y, boolean right) {
        super(x, y);
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
