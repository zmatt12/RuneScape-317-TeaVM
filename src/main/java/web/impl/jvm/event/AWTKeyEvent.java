package web.impl.jvm.event;

import web.event.KeyEvent;

public class AWTKeyEvent extends KeyEvent {

    private final java.awt.event.KeyEvent event;

    public AWTKeyEvent(java.awt.event.KeyEvent event) {
        this.event = event;
    }

    @Override
    public int getKeyCode() {
        return event.getKeyCode();
    }

    @Override
    public char getKeyChar() {
        return event.getKeyChar();
    }

    @Override
    public int getEventType() {
        return event.getID();
    }
}
