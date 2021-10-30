package client.textures.software;

import client.Buffer;
import client.Draw2D;
import client.FileArchive;
import client.textures.IndexedTexture;

import java.io.IOException;

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
        int l = x + (y * Draw2D.width);
        int i1 = 0;
        int j1 = getHeight();
        int k1 = getWidth();
        int l1 = Draw2D.width - k1;
        int i2 = 0;
        if (y < Draw2D.top) {
            int j2 = Draw2D.top - y;
            j1 -= j2;
            y = Draw2D.top;
            i1 += j2 * k1;
            l += j2 * Draw2D.width;
        }
        if ((y + j1) > Draw2D.bottom) {
            j1 -= (y + j1) - Draw2D.bottom;
        }
        if (x < Draw2D.left) {
            int k2 = Draw2D.left - x;
            k1 -= k2;
            x = Draw2D.left;
            i1 += k2;
            l += k2;
            i2 += k2;
            l1 += k2;
        }
        if ((x + k1) > Draw2D.right) {
            int l2 = (x + k1) - Draw2D.right;
            k1 -= l2;
            i2 += l2;
            l1 += l2;
        }
        if ((k1 > 0) && (j1 > 0)) {
            draw(j1, Draw2D.pixels, indices, l1, l, k1, i1, palette, i2);
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

    public void draw(int i, int[] ai, byte[] abyte0, int j, int k, int l, int i1, int[] ai1, int j1) {
        int k1 = -(l >> 2);
        l = -(l & 3);
        for (int l1 = -i; l1 < 0; l1++) {
            for (int i2 = k1; i2 < 0; i2++) {
                byte byte1 = abyte0[i1++];
                if (byte1 != 0) {
                    ai[k++] = ai1[byte1 & 0xff];
                } else {
                    k++;
                }
                byte1 = abyte0[i1++];
                if (byte1 != 0) {
                    ai[k++] = ai1[byte1 & 0xff];
                } else {
                    k++;
                }
                byte1 = abyte0[i1++];
                if (byte1 != 0) {
                    ai[k++] = ai1[byte1 & 0xff];
                } else {
                    k++;
                }
                byte1 = abyte0[i1++];
                if (byte1 != 0) {
                    ai[k++] = ai1[byte1 & 0xff];
                } else {
                    k++;
                }
            }
            for (int j2 = l; j2 < 0; j2++) {
                byte byte2 = abyte0[i1++];
                if (byte2 != 0) {
                    ai[k++] = ai1[byte2 & 0xff];
                } else {
                    k++;
                }
            }
            k += j;
            i1 += j1;
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
