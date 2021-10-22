package web.event;

public class ImmutableKeyEvent extends KeyEvent {

    private final int type;
    private final int code;
    private final char key;

    public ImmutableKeyEvent(int type, int code, char key) {
        this.type = type;
        this.code = code;
        this.key = key;
    }

    @Override
    public int getEventType() {
        return type;
    }

    @Override
    public int getKeyCode() {
        return code;
    }

    @Override
    public char getKeyChar() {
        return key;
    }

    @Override
    public String toString() {
        return "ImmutableKeyEvent{" +
                "type=" + type +
                ", code=" + code +
                ", key=" + key +
                '}';
    }
}
