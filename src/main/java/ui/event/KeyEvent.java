package ui.event;

public abstract class KeyEvent extends Event{

    public static final int TYPE_PRESSED = 0;
    public static final int TYPE_RELEASED = 1;
    public static final int TYPE_TYPED = 2;

    public static final int VK_LEFT = 1037;
    public static final int VK_RIGHT = 1039;
    public static final int VK_UP = 1038;
    public static final int VK_DOWN = 1040;

    public static final int VK_CONTROL = 1017;
    public static final int VK_BACK_SPACE = 1008;
    public static final int VK_DELETE = 1046;
    public static final int VK_TAB = 1009;
    public static final int VK_ENTER = 1013;

    public static final int VK_F1 = 1112;
    public static final int VK_F2 = 1113;
    public static final int VK_F3 = 1114;
    public static final int VK_F4 = 1115;
    public static final int VK_F5 = 1116;
    public static final int VK_F6 = 1117;
    public static final int VK_F7 = 1118;
    public static final int VK_F8 = 1119;
    public static final int VK_F9 = 1120;
    public static final int VK_F10 = 1121;
    public static final int VK_F11 = 1122;
    public static final int VK_F12 = 1123;

    public static final int VK_HOME = 1036;
    public static final int VK_END = 1035;
    public static final int VK_PAGE_UP = 1033;
    public static final int VK_PAGE_DOWN = 1034;

    @Override
    public final int getType() {
        return TYPE_KEY;
    }

    public abstract int getKeyCode();

    public abstract char getKeyChar();
}
