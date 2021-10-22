package web.impl.jvm.impl;

import web.IFontMetrics;

import java.awt.*;

class JVMFontMetrics implements IFontMetrics {

    private final FontMetrics metrics;

    public JVMFontMetrics(FontMetrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public int stringWidth(String str) {
        return metrics.stringWidth(str);
    }
}
