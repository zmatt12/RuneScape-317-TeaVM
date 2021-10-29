package web.impl.jvm.impl;

import web.IImage;
import web.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

class JVMImage implements IImage<JVMComponent> {

    private final Image image;

    public JVMImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public int getWidth(Window<JVMComponent> component) {
        return image.getWidth(null);
    }

    @Override
    public int getHeight(Window<JVMComponent> component) {
        return image.getHeight(null);
    }

    @Override
    public int[] getBufferAsIntegers() {
        if (!(image instanceof BufferedImage)) {
            throw new RuntimeException("Bad image");
        }
        BufferedImage br = (BufferedImage) image;
        return ((DataBufferInt) br.getRaster().getDataBuffer()).getData();
    }
}
