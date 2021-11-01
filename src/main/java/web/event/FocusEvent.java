package web.event;

public abstract class FocusEvent extends Event {

    public static final int TYPE_GAINED = 1004;
    public static final int TYPE_LOST = 1005;

    @Override
    public final int getType() {
        return TYPE_FOCUS;
    }
}
