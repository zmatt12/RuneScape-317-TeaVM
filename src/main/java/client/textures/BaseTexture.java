package client.textures;

import client.DoublyLinkedList;

public abstract class BaseTexture extends DoublyLinkedList.Node {

    public int width;
    public int height;
    public int cropX;
    public int cropY;
    public int cropW;
    public int cropH;

    public abstract void translate(int r, int g, int b);

    public abstract void draw(int x, int y);
}
