package web.impl.webassembly;

import web.IImage;
import web.Window;

public class WAImage implements IImage<WAComponent> {

    private final int width;
    private final int height;
    private final int[] pixels;

    public WAImage(int width, int height){
        this(width, height, new int[width * height]);
    }

    public WAImage(int width, int height, int[] pixels){
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    @Override
    public int getWidth(Window<WAComponent> component) {
        return width;
    }

    @Override
    public int getHeight(Window<WAComponent> component) {
        return height;
    }

    @Override
    public int[] getBufferAsIntegers() {
        return pixels;
    }
}
