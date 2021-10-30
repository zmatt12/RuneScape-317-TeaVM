package client.textures;

public abstract class RGBTexture extends BaseTexture {

    public abstract void bind();

    public abstract void crop();

    public abstract void fill(int rgb);

    public abstract void blitOpaque(int x, int y);

    public abstract void draw(int x, int y, int alpha);

    public abstract void drawRotatedMasked(int x, int y, int width, int height, int anchorX, int anchorY, int zoom, int angle, int[] lineLengths, int[] lineOffsets);

    public abstract void drawRotated(int x, int y, int width, int height, int achorX, int anchorY, double radian, int zoom);

    public abstract void drawMasked(IndexedTexture mask, int x, int y);

    public abstract void setPixels(int[] pixels);
}
