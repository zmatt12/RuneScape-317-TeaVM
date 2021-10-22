package web.impl.jvm.impl;

import web.IFont;
import web.IGraphics;
import web.IImage;
import web.util.Color;

import java.awt.*;
import java.awt.image.ImageObserver;

class JVMGraphics implements IGraphics {

    private final Graphics graphics;

    public JVMGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public void setColor(Color color) {
        graphics.setColor(Utils.toJvm(color));
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        graphics.fillRect(x, y, width, height);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    @Override
    public void setFont(IFont font) {
        graphics.setFont(((JVMFont) font).getFont());
    }

    @Override
    public void drawString(String str, int x, int y) {
        graphics.drawString(str, x, y);
    }

    @Override
    public void drawImage(IImage img, int width, int height, Object observer) {
        graphics.drawImage(((JVMImage) img).getImage(), width, height, (ImageObserver) observer);
    }
}
