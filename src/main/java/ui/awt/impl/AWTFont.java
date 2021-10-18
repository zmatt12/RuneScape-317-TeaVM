package ui.awt.impl;

import ui.IFont;

import java.awt.*;

class AWTFont implements IFont {

    private final Font font;

    public AWTFont(Font font) {
        this.font = font;
    }

    public AWTFont(String name, int style, int size) {
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
