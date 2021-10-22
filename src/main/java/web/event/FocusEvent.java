package web.event;

public abstract class FocusEvent extends Event {

    public static final int TYPE_GAINED = 0;
    public static final int TYPE_LOST = 1;

    @Override
    public final int getType() {
        return TYPE_FOCUS;
    }
}
