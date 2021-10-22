package web.event;

public final class ImmutableFocusEvent extends FocusEvent {

    private final int type;

    public ImmutableFocusEvent(int type) {
        this.type = type;
    }

    @Override
    public int getEventType() {
        return type;
    }

    @Override
    public String toString() {
        return "ImmutableFocusEvent{" +
                "type=" + type +
                '}';
    }
}
