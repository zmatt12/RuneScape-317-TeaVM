package ui.awt.impl;

import ui.IImage;
import ui.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

class AWTImage implements IImage {

    private final Image image;

    public AWTImage(Image image) {
        this.image = image;
    }

    public Image getImage(){
        return image;
    }

    @Override
    public int getWidth(Window component) {
        //TODO get proper param
        return image.getWidth(null);
    }

    @Override
    public int getHeight(Window component) {
        //TODO get proper param
        return image.getHeight(null);
    }

    @Override
    public int[] getBufferAsIntegers() {
        if(!(image instanceof BufferedImage)){
            throw new RuntimeException("Bad image");
        }
        BufferedImage br = (BufferedImage) image;
        return ((DataBufferInt) br.getRaster().getDataBuffer()).getData();
    }
}
