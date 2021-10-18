package ui.awt.impl;

import ui.poly.Color;
import ui.poly.Dimension;

public final class AWTUtils {

    private AWTUtils(){

    }

    public static java.awt.Color toAwt(Color color){
        return new java.awt.Color(color.getRed(), color.getBlue(), color.getGreen(), color.getAlpha());
    }

    public static java.awt.Dimension toAwt(Dimension dimension){
        return new java.awt.Dimension(dimension.getWidth(), dimension.getHeight());
    }

    public static Dimension fromAwt(java.awt.Dimension dimension){
        return new Dimension(dimension.width, dimension.height);
    }
}
