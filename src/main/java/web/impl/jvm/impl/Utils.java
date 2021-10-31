package web.impl.jvm.impl;

import web.util.Color;
import web.util.Dimension;

public final class Utils {

    private Utils() {

    }

    public static java.awt.Color toJvm(Color color) {
        return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static java.awt.Dimension toJvm(Dimension dimension) {
        return new java.awt.Dimension(dimension.getWidth(), dimension.getHeight());
    }

    public static Dimension fromJvm(java.awt.Dimension dimension) {
        return new Dimension(dimension.width, dimension.height);
    }
}
