package ui.event;

public abstract class Event {

    public static final int TYPE_FOCUS = 0;
    public static final int TYPE_KEY = 1;
    public static final int TYPE_MOUSE = 2;

    private boolean consumed = false;

    public abstract int getType();

    public abstract int getEventType();

    public final boolean consumed(){
        return consumed;
    }

    public void consume(){
        this.consumed = true;
    }
}
