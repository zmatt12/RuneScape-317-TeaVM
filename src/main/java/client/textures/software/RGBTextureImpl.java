package client.textures.software;

import client.Buffer;
import client.Draw2D;
import client.FileArchive;
import client.textures.IndexedTexture;
import client.textures.RGBTexture;
import web.IImage;
import web.Platform;

import java.io.IOException;
import java.util.Arrays;

class RGBTextureImpl extends RGBTexture {

    private int[] pixels;

    RGBTextureImpl(int width, int height) {
        pixels = new int[width * height];
        setSize(width, height);
        setCrop(0, 0, width, height);
    }

    RGBTextureImpl(byte[] data) {
        try {
            IImage<?> img = Platform.getDefault().createImage(data);
            setSize(img.getWidth(), img.getHeight());
            setCrop(0, 0, getWidth(), getHeight());
            pixels = img.getBufferAsIntegers();
        } catch (Exception _ex) {
            _ex.printStackTrace();
            System.out.println("Error converting jpg");
        }
    }

    private static void copyPixels(int dstOff, int w, int h, int srcStep, int srcOff, int dstStep, int[] src, int[] dst) {
        int quarterWidth = -(w >> 2);
        w = -(w & 3);
        for (int i2 = -h; i2 < 0; i2++) {
            for (int j2 = quarterWidth; j2 < 0; j2++) {
                dst[dstOff++] = src[srcOff++];
                dst[dstOff++] = src[srcOff++];
                dst[dstOff++] = src[srcOff++];
                dst[dstOff++] = src[srcOff++];
            }
            for (int k2 = w; k2 < 0; k2++) {
                dst[dstOff++] = src[srcOff++];
            }
            dstOff += dstStep;
            srcOff += srcStep;
        }
    }

    private static void copyPixels(int[] dst, int[] src, int srcOff, int dstOff, int w, int h, int dstStep, int srcstep) {
        int quarterW = -(w >> 2);
        w = -(w & 3);
        for (int y = -h; y < 0; y++) {
            int rgb;
            for (int x = quarterW; x < 0; x++) {
                rgb = src[srcOff++];
                if (rgb != 0) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
                rgb = src[srcOff++];
                if (rgb != 0) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
                rgb = src[srcOff++];
                if (rgb != 0) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
                rgb = src[srcOff++];
                if (rgb != 0) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
            }
            for (int k2 = w; k2 < 0; k2++) {
                rgb = src[srcOff++];
                if (rgb != 0) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
            }
            dstOff += dstStep;
            srcOff += srcstep;
        }
    }

    private static void copyPixelsAlpha(int srcOff, int w, int[] dst, int[] src, int srcstep, int h, int dstStep, int alpha, int dstOff) {
        int invAlpha = 256 - alpha;
        for (int k2 = -h; k2 < 0; k2++) {
            for (int l2 = -w; l2 < 0; l2++) {
                int srcRGB = src[srcOff++];
                if (srcRGB != 0) {
                    int dstRGB = dst[dstOff];
                    dst[dstOff++] = (((((srcRGB & 0xff00ff) * alpha) + ((dstRGB & 0xff00ff) * invAlpha)) & 0xff00ff00) + ((((srcRGB & 0xff00) * alpha) + ((dstRGB & 0xff00) * invAlpha)) & 0xff0000)) >> 8;
                } else {
                    dstOff++;
                }
            }
            dstOff += dstStep;
            srcOff += srcstep;
        }
    }

    private static void copyPixelsMasked(int[] src, int srcOff, int srcStep, byte[] mask, int w, int h, int[] dst, int dstOff, int dstStep) {
        int quarterW = -(w >> 2);
        w = -(w & 3);
        for (int y = -h; y < 0; y++) {
            int rgb;
            for (int k2 = quarterW; k2 < 0; k2++) {
                rgb = src[srcOff++];
                if ((rgb != 0) && (mask[dstOff] == 0)) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
                rgb = src[srcOff++];
                if ((rgb != 0) && (mask[dstOff] == 0)) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
                rgb = src[srcOff++];
                if ((rgb != 0) && (mask[dstOff] == 0)) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
                rgb = src[srcOff++];
                if ((rgb != 0) && (mask[dstOff] == 0)) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
            }
            for (int l2 = w; l2 < 0; l2++) {
                rgb = src[srcOff++];
                if ((rgb != 0) && (mask[dstOff] == 0)) {
                    dst[dstOff++] = rgb;
                } else {
                    dstOff++;
                }
            }
            dstOff += dstStep;
            srcOff += srcStep;
        }
    }

    @Override
    public void bind() {
        Draw2D.bind(pixels, getWidth(), getHeight());
    }

    @Override
    public void translate(int r, int g, int b) {
        for (int i = 0; i < pixels.length; i++) {
            int rgb = pixels[i];
            if (rgb != 0) {
                int red = (rgb >> 16) & 0xff;
                red += r;
                if (red < 1) {
                    red = 1;
                } else if (red > 255) {
                    red = 255;
                }
                int green = (rgb >> 8) & 0xff;
                green += g;
                if (green < 1) {
                    green = 1;
                } else if (green > 255) {
                    green = 255;
                }
                int blue = rgb & 0xff;
                blue += b;
                if (blue < 1) {
                    blue = 1;
                } else if (blue > 255) {
                    blue = 255;
                }
                pixels[i] = (red << 16) + (green << 8) + blue;
            }
        }
    }

    @Override
    public void crop() {
        int[] pixels = new int[getCropW() * getCropH()];
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                pixels[((y + getCropY()) * getCropW()) + (x + getCropX())] = this.pixels[(y * getWidth()) + x];
            }
        }
        this.pixels = pixels;
        setWidth(getCropW());
        setHeight(getCropH());
        setCropX(0);
        setCropY(0);
    }

    @Override
    public void fill(int rgb) {
        Arrays.fill(pixels, rgb);
    }

    @Override
    public void blitOpaque(int x, int y) {
        x += getCropX();
        y += getCropY();
        int dstOff = x + (y * Draw2D.width);
        int srcOff = 0;
        int h = getHeight();
        int w = getWidth();
        int dstStep = Draw2D.width - w;
        int srcStep = 0;
        if (y < Draw2D.top) {
            int trim = Draw2D.top - y;
            h -= trim;
            y = Draw2D.top;
            srcOff += trim * w;
            dstOff += trim * Draw2D.width;
        }
        if ((y + h) > Draw2D.bottom) {
            h -= (y + h) - Draw2D.bottom;
        }
        if (x < Draw2D.left) {
            int trim = Draw2D.left - x;
            w -= trim;
            x = Draw2D.left;
            srcOff += trim;
            dstOff += trim;
            srcStep += trim;
            dstStep += trim;
        }
        if ((x + w) > Draw2D.right) {
            int trim = (x + w) - Draw2D.right;
            w -= trim;
            srcStep += trim;
            dstStep += trim;
        }
        if ((w > 0) && (h > 0)) {
            copyPixels(dstOff, w, h, srcStep, srcOff, dstStep, pixels, Draw2D.pixels);
        }
    }

    @Override
    public void draw(int x, int y) {
        x += getCropX();
        y += getCropY();
        int dstOff = x + (y * Draw2D.width);
        int srcOff = 0;
        int h = getHeight();
        int w = getWidth();
        int dstStep = Draw2D.width - w;
        int srcStep = 0;
        if (y < Draw2D.top) {
            int j2 = Draw2D.top - y;
            h -= j2;
            y = Draw2D.top;
            srcOff += j2 * w;
            dstOff += j2 * Draw2D.width;
        }
        if ((y + h) > Draw2D.bottom) {
            h -= (y + h) - Draw2D.bottom;
        }
        if (x < Draw2D.left) {
            int k2 = Draw2D.left - x;
            w -= k2;
            x = Draw2D.left;
            srcOff += k2;
            dstOff += k2;
            srcStep += k2;
            dstStep += k2;
        }
        if ((x + w) > Draw2D.right) {
            int trim = (x + w) - Draw2D.right;
            w -= trim;
            srcStep += trim;
            dstStep += trim;
        }
        if ((w > 0) && (h > 0)) {
            copyPixels(Draw2D.pixels, pixels, srcOff, dstOff, w, h, dstStep, srcStep);
        }
    }

    @Override
    public void draw(int x, int y, int alpha) {
        x += getCropX();
        y += getCropY();
        int i1 = x + (y * Draw2D.width);
        int j1 = 0;
        int k1 = getHeight();
        int w = getWidth();
        int i2 = Draw2D.width - w;
        int j2 = 0;
        if (y < Draw2D.top) {
            int k2 = Draw2D.top - y;
            k1 -= k2;
            y = Draw2D.top;
            j1 += k2 * w;
            i1 += k2 * Draw2D.width;
        }
        if ((y + k1) > Draw2D.bottom) {
            k1 -= (y + k1) - Draw2D.bottom;
        }
        if (x < Draw2D.left) {
            int l2 = Draw2D.left - x;
            w -= l2;
            x = Draw2D.left;
            j1 += l2;
            i1 += l2;
            j2 += l2;
            i2 += l2;
        }
        if ((x + w) > Draw2D.right) {
            int i3 = (x + w) - Draw2D.right;
            w -= i3;
            j2 += i3;
            i2 += i3;
        }
        if ((w > 0) && (k1 > 0)) {
            copyPixelsAlpha(j1, w, Draw2D.pixels, pixels, j2, k1, i2, alpha, i1);
        }
    }

    @Override
    public void drawRotatedMasked(int x, int y, int width, int height, int anchorX, int anchorY, int zoom, int angle, int[] lineLengths, int[] lineOffsets) {
        try {
            int midX = -width / 2;
            int midY = -height / 2;
            int sin = (int) (Math.sin((double) angle / 326.11000000000001D) * 65536D);
            int cos = (int) (Math.cos((double) angle / 326.11000000000001D) * 65536D);
            sin = (sin * zoom) >> 8;
            cos = (cos * zoom) >> 8;
            int leftX = (anchorX << 16) + ((midY * sin) + (midX * cos));
            int lefty = (anchorY << 16) + ((midY * cos) - (midX * sin));
            int leftOff = x + (y * Draw2D.width);
            for (y = 0; y < height; y++) {
                int lineOffset = lineOffsets[y];
                int dstOff = leftOff + lineOffset;
                int srcX = leftX + (cos * lineOffset);
                int srcY = lefty - (sin * lineOffset);
                for (x = -lineLengths[y]; x < 0; x++) {
                    Draw2D.pixels[dstOff++] = pixels[(srcX >> 16) + ((srcY >> 16) * this.getWidth())];
                    srcX += cos;
                    srcY -= sin;
                }
                leftX += sin;
                lefty += cos;
                leftOff += Draw2D.width;
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void drawRotated(int x, int y, int width, int height, int anchorX, int anchorY, double radians, int zoom) {
        try {
            int centerX = -width / 2;
            int centerY = -height / 2;
            int sin = (int) (Math.sin(radians) * 65536D);
            int cos = (int) (Math.cos(radians) * 65536D);
            sin = (sin * zoom) >> 8;
            cos = (cos * zoom) >> 8;
            int leftX = (anchorX << 16) + ((centerY * sin) + (centerX * cos));
            int leftY = (anchorY << 16) + ((centerY * cos) - (centerX * sin));
            int leftOff = x + (y * Draw2D.width);

            for (y = 0; y < height; y++) {
                int dstOff = leftOff;
                int dstX = leftX;
                int dstY = leftY;
                for (x = -width; x < 0; x++) {
                    int rgb = pixels[(dstX >> 16) + ((dstY >> 16) * this.getWidth())];
                    if (rgb != 0) {
                        Draw2D.pixels[dstOff++] = rgb;
                    } else {
                        dstOff++;
                    }
                    dstX += cos;
                    dstY -= sin;
                }
                leftX += sin;
                leftY += cos;
                leftOff += Draw2D.width;
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void drawMasked(IndexedTexture mask, int x, int y) {
        x += getCropX();
        y += getCropY();
        int dstOff = x + (y * Draw2D.width);
        int srcOff = 0;
        int h = getHeight();
        int w = getWidth();
        int dstStep = Draw2D.width - w;
        int srcStep = 0;
        if (y < Draw2D.top) {
            int trim = Draw2D.top - y;
            h -= trim;
            y = Draw2D.top;
            srcOff += trim * w;
            dstOff += trim * Draw2D.width;
        }
        if ((y + h) > Draw2D.bottom) {
            h -= (y + h) - Draw2D.bottom;
        }
        if (x < Draw2D.left) {
            int trim = Draw2D.left - x;
            w -= trim;
            x = Draw2D.left;
            srcOff += trim;
            dstOff += trim;
            srcStep += trim;
            dstStep += trim;
        }
        if ((x + w) > Draw2D.right) {
            int trim = (x + w) - Draw2D.right;
            w -= trim;
            srcStep += trim;
            dstStep += trim;
        }
        if ((w > 0) && (h > 0)) {
            copyPixelsMasked(pixels, srcOff, srcStep, mask.getIndices(), w, h, Draw2D.pixels, dstOff, dstStep);
        }
    }

    @Override
    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    @Override
    public int[] getPixels() {
        return pixels;
    }
}
