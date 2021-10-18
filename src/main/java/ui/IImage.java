package ui;

public interface IImage {

    int TYPE_INT_RGB = 0;

    int getWidth(Window component);

    int getHeight(Window component);

    default int getWidth(){
        return getWidth(null);
    }

    default int getHeight(){
        return getHeight(null);
    }

    static IImage create(int width, int height, int type){
        return WindowEngine.getDefault().createImage(width, height, type);
    }

    int[] getBufferAsIntegers();
}
