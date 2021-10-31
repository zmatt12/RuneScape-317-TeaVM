package client.textures;

import client.DoublyLinkedList;

public abstract class BaseTexture extends DoublyLinkedList.Node {

    private int width;
    private int height;

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
}
