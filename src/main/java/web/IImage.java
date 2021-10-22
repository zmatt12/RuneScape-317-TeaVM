package web;

public interface IImage<T extends IComponent> {

    int TYPE_INT_RGB = 0;

    static IImage<?> create(int width, int height, int type) {
        return Platform.getDefault().createImage(width, height, type);
    }

    int getWidth(Window<T> component);

    int getHeight(Window<T> component);

    default int getWidth() {
        return getWidth(null);
    }

    default int getHeight() {
        return getHeight(null);
    }

    int[] getBufferAsIntegers();
}
