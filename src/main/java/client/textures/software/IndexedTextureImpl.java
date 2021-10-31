package client.textures.software;

import client.Draw2D;
import client.textures.IndexedTexture;


class IndexedTextureImpl extends IndexedTexture {

    private final int[] palette;
    private byte[] indices;

    IndexedTextureImpl (int[] palette, int width, int height){
        setSize(width, height);
        this.palette = palette;
        this.indices = new byte[width * height];
    }

    @Override
	public void shrink() {
        setCropW(getCropW() / 2);
        setCropH(getCropH() / 2);
        byte[] indices = new byte[getCropW() * getCropH()];
        int i = 0;
        for (int j = 0; j < getHeight(); j++) {
            for (int k = 0; k < getWidth(); k++) {
                indices[((k + getCropX()) >> 1) + (((j + getCropY()) >> 1) * getCropW())] = this.indices[i++];
            }
        }
        this.indices = indices;
        setWidth(getCropW());
        setHeight(getCropH());
        setCropX(0);
        setCropY(0);
    }

    @Override
	public void crop() {
        if ((getWidth() == getCropW()) && (getHeight() == getCropH())) {
            return;
        }
        byte[] indices = new byte[getCropW() * getCropH()];
        int i = 0;
        for (int j = 0; j < getHeight(); j++) {
            for (int k = 0; k < getWidth(); k++) {
                indices[k + getCropX() + ((j + getCropY()) * getCropW())] = this.indices[i++];
            }
        }
        this.indices = indices;
        setWidth(getCropW());
        setHeight(getCropH());
        setCropX(0);
        setCropY(0);
    }

    @Override
	public void flipH() {
        byte[] indices = new byte[getWidth() * getHeight()];
        int i = 0;
        for (int y = 0; y < getHeight(); y++) {
            for (int x = getWidth() - 1; x >= 0; x--) {
                indices[i++] = this.indices[x + (y * getWidth())];
            }
        }
        this.indices = indices;
        setCropX(getCropW() - getWidth() - getCropX());
    }

    @Override
	public void flipV() {
        byte[] indices = new byte[getWidth() * getHeight()];
        int i = 0;
        for (int y = getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < getWidth(); x++) {
                indices[i++] = this.indices[x + (y * getWidth())];
            }
        }
        this.indices = indices;
        setCropY(getCropH() - getHeight() - getCropY());
    }

    @Override
	public void translate(int r, int g, int b) {
        for (int i = 0; i < palette.length; i++) {
            int red = (palette[i] >> 16) & 0xff;
            red += r;
            if (red < 0) {
                red = 0;
            } else if (red > 255) {
                red = 255;
            }
            int green = (palette[i] >> 8) & 0xff;
            green += g;
            if (green < 0) {
                green = 0;
            } else if (green > 255) {
                green = 255;
            }
            int blue = palette[i] & 0xff;
            blue += b;
            if (blue < 0) {
                blue = 0;
            } else if (blue > 255) {
                blue = 255;
            }
            palette[i] = (red << 16) + (green << 8) + blue;
        }
    }

    @Override
	public void draw(int x, int y) {
        x += getCropX();
        y += getCropY();
        int destStart = x + (y * Draw2D.width);
        int start = 0;
        int height = getHeight();
        int width = getWidth();
        int destOffset = Draw2D.width - width;
        int srcOffset = 0;
        if (y < Draw2D.top) {
            int j2 = Draw2D.top - y;
            height -= j2;
            y = Draw2D.top;
            start += j2 * width;
            destStart += j2 * Draw2D.width;
        }
        if ((y + height) > Draw2D.bottom) {
            height -= (y + height) - Draw2D.bottom;
        }
        if (x < Draw2D.left) {
            int k2 = Draw2D.left - x;
            width -= k2;
            x = Draw2D.left;
            start += k2;
            destStart += k2;
            srcOffset += k2;
            destOffset += k2;
        }
        if ((x + width) > Draw2D.right) {
            int l2 = (x + width) - Draw2D.right;
            width -= l2;
            srcOffset += l2;
            destOffset += l2;
        }
        if ((width > 0) && (height > 0)) {
            draw(height, Draw2D.pixels, indices, destOffset, destStart, width, start, palette, srcOffset);
        }
    }

    @Override
    public int[] getPixels() {
        int[] data = new int[indices.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = palette[indices[i]];
        }
        return data;
    }

    private static void draw(int height, int[] dest, byte[] indices, int destOffset,
                             int destIndex, int width, int srcIndex, int[] palette, int srcOffset) {
        int k1 = -(width >> 2);
        width = -(width & 3);
        for (int l1 = -height; l1 < 0; l1++) {
            for (int i2 = k1; i2 < 0; i2++) {
                byte index = indices[srcIndex++];
                if (index != 0) {
                    dest[destIndex++] = palette[index & 0xff];
                } else {
                    destIndex++;
                }
                index = indices[srcIndex++];
                if (index != 0) {
                    dest[destIndex++] = palette[index & 0xff];
                } else {
                    destIndex++;
                }
                index = indices[srcIndex++];
                if (index != 0) {
                    dest[destIndex++] = palette[index & 0xff];
                } else {
                    destIndex++;
                }
                index = indices[srcIndex++];
                if (index != 0) {
                    dest[destIndex++] = palette[index & 0xff];
                } else {
                    destIndex++;
                }
            }
            for (int j2 = width; j2 < 0; j2++) {
                byte index = indices[srcIndex++];
                if (index != 0) {
                    dest[destIndex++] = palette[index & 0xff];
                } else {
                    destIndex++;
                }
            }
            destIndex += destOffset;
            srcIndex += srcOffset;
        }
    }

    public byte[] getIndices() {
        return indices;
    }

    public void setIndices(byte[] indices) {
        this.indices = indices;
    }

    public int[] getPalette() {
        return palette;
    }
}
