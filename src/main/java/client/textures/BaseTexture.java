package client.textures;

import client.DoublyLinkedList;

public abstract class BaseTexture extends DoublyLinkedList.Node {

    private int width;
    private int height;
    private int cropX;
    private int cropY;
    private int cropW;
    private int cropH;

    public abstract void translate(int r, int g, int b);

    public abstract void draw(int x, int y);

    public abstract int[] getPixels();

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getCropX() {
        return cropX;
    }

    public void setCropX(int cropX) {
        this.cropX = cropX;
    }

    public int getCropY() {
        return cropY;
    }

    public void setCropY(int cropY) {
        this.cropY = cropY;
    }

    public int getCropW() {
        return cropW;
    }

    public void setCropW(int cropW) {
        this.cropW = cropW;
    }

    public int getCropH() {
        return cropH;
    }

    public void setCropH(int cropH) {
        this.cropH = cropH;
    }

    public void setCrop(int x, int y, int width, int height){
        this.cropX = x;
        this.cropY = y;
        this.cropW = width;
        this.cropH = height;
    }
}
