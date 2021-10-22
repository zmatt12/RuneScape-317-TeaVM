package web.impl.jvm.event;

import web.event.KeyEvent;

public class AWTKeyEvent extends KeyEvent {

    private static final int[] CODE_MAP = new int[2000];

    static {
        CODE_MAP[java.awt.event.KeyEvent.VK_LEFT] = KeyEvent.VK_LEFT;
        CODE_MAP[java.awt.event.KeyEvent.VK_RIGHT] = KeyEvent.VK_RIGHT;
        CODE_MAP[java.awt.event.KeyEvent.VK_UP] = KeyEvent.VK_UP;
        CODE_MAP[java.awt.event.KeyEvent.VK_DOWN] = KeyEvent.VK_DOWN;
        CODE_MAP[java.awt.event.KeyEvent.VK_CONTROL] = KeyEvent.VK_CONTROL;
        CODE_MAP[java.awt.event.KeyEvent.VK_BACK_SPACE] = KeyEvent.VK_BACK_SPACE;
        CODE_MAP[java.awt.event.KeyEvent.VK_DELETE] = KeyEvent.VK_DELETE;
        CODE_MAP[java.awt.event.KeyEvent.VK_TAB] = KeyEvent.VK_TAB;
        CODE_MAP[java.awt.event.KeyEvent.VK_ENTER] = KeyEvent.VK_ENTER;

        for (int i = 0; i < VK_F12 - VK_F1; i++) {
            CODE_MAP[java.awt.event.KeyEvent.VK_F1 + i] = KeyEvent.VK_F1 + i;
        }

        CODE_MAP[java.awt.event.KeyEvent.VK_HOME] = KeyEvent.VK_HOME;
        CODE_MAP[java.awt.event.KeyEvent.VK_END] = KeyEvent.VK_END;
        CODE_MAP[java.awt.event.KeyEvent.VK_PAGE_UP] = KeyEvent.VK_PAGE_UP;
        CODE_MAP[java.awt.event.KeyEvent.VK_PAGE_DOWN] = KeyEvent.VK_PAGE_DOWN;
    }

    private final java.awt.event.KeyEvent event;

    public AWTKeyEvent(java.awt.event.KeyEvent event) {
        this.event = event;
    }

    @Override
    public int getKeyCode() {
        int code = CODE_MAP[event.getKeyCode()];
        if (code != 0) {
            return code;
        }
        return event.getKeyCode();
    }

    @Override
    public char getKeyChar() {
        return event.getKeyChar();
    }

    @Override
    public int getEventType() {
        switch (event.getID()) {
            case java.awt.event.KeyEvent.KEY_PRESSED:
                return KeyEvent.TYPE_PRESSED;
            case java.awt.event.KeyEvent.KEY_RELEASED:
                return KeyEvent.TYPE_RELEASED;
            case java.awt.event.KeyEvent.KEY_TYPED:
                return KeyEvent.TYPE_TYPED;
        }
        return -1;
    }
}
