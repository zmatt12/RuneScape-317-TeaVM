package ui.awt.impl;

import ui.IFontMetrics;

import java.awt.*;

class AWTFontMetrics implements IFontMetrics {

    private final FontMetrics metrics;

    public AWTFontMetrics(FontMetrics metrics){
        this.metrics = metrics;
    }

    @Override
    public int stringWidth(String str) {
        return metrics.stringWidth(str);
    }
}
