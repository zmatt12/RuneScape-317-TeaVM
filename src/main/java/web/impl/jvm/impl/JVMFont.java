package web.impl.jvm.impl;

import web.IFont;

import java.awt.*;

class JVMFont implements IFont {

    private final Font font;

    public JVMFont(Font font) {
        this.font = font;
    }

    public JVMFont(String name, int style, int size) {
        int s = Font.PLAIN;
        if (style != PLAIN) {
            if ((style & BOLD) != 0) {
                s |= Font.BOLD;
            }
            if ((style & ITALIC) != 0) {
                s |= Font.ITALIC;
            }
        }
        this.font = new Font(name, s, size);
    }

    public Font getFont() {
        return font;
    }
}
