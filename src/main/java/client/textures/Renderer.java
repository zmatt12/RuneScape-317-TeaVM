package client.textures;

import client.Buffer;
import client.files.FileArchive;
import client.textures.software.SoftwareRenderer;

import java.io.IOException;

public abstract class Renderer {

    protected Renderer(){

    }

    public static Renderer get(){
        return SoftwareRenderer.getInstance();
    }

    public abstract RGBTexture createRGB(int width, int height);

    public abstract RGBTexture decodeRGB(byte[] data);

    public final RGBTexture decodeRGB(FileArchive archive, String file, int index) throws IOException {
        Buffer buffer = new Buffer(archive.read(file + ".dat"));
        Buffer buffer_1 = new Buffer(archive.read("index.dat"));
        buffer_1.position = buffer.get2U();
        int cropW = buffer_1.get2U();
        int cropH = buffer_1.get2();
        int count = buffer_1.get1U();
        int[] palette = new int[count];
        for (int k = 0; k < (count - 1); k++) {
            palette[k + 1] = buffer_1.get3();
            if (palette[k + 1] == 0) {
                palette[k + 1] = 1;
            }
        }
        for (int l = 0; l < index; l++) {
            buffer_1.position += 2;
            buffer.position += buffer_1.get2U() * buffer_1.get2U();
            buffer_1.position++;
        }
        int cropX = buffer_1.get1U();
        int cropY = buffer_1.get1U();
        int width = buffer_1.get2U();
        int height = buffer_1.get2U();
        RGBTexture texture = createRGB(width, height);
        texture.setCrop(cropX, cropY, cropW, cropH);
        int layout = buffer_1.get1U();
        int pixelLen = width * height;
        int[] pixels = new int[pixelLen];
        if (layout == 0) {
            for (int k1 = 0; k1 < pixelLen; k1++) {
                pixels[k1] = palette[buffer.get1U()];
            }
        }
        if (layout == 1) {
            for (int l1 = 0; l1 < width; l1++) {
                for (int i2 = 0; i2 < height; i2++) {
                    pixels[l1 + (i2 * width)] = palette[buffer.get1U()];
                }
            }
        }
        texture.setPixels(pixels);
        return texture;
    }

    public abstract IndexedTexture createIndexed(int[] palette, int width, int height);

    public IndexedTexture decodeIndexed(FileArchive archive, String s, int i) throws IOException {
        Buffer buffer = new Buffer(archive.read(s + ".dat"));
        Buffer buffer_1 = new Buffer(archive.read("index.dat"));
        buffer_1.position = buffer.get2U();
        int cropW = buffer_1.get2U();
        int cropH = buffer_1.get2U();
        int count = buffer_1.get1U();
        int[] palette = new int[count];
        for (int k = 0; k < (count - 1); k++) {
            palette[k + 1] = buffer_1.get3();
        }
        for (int l = 0; l < i; l++) {
            buffer_1.position += 2;
            buffer.position += buffer_1.get2U() * buffer_1.get2U();
            buffer_1.position++;
        }
        int cropX = buffer_1.get1U();
        int cropY = buffer_1.get1U();
        int width = buffer_1.get2U();
        int height = buffer_1.get2U();
        IndexedTexture res = this.createIndexed(palette, width, height);
        res.setCrop(cropX, cropY, cropW, cropH);
        int type = buffer_1.get1U();
        int size = width * height;
        byte[] indices = new byte[size];
        if (type == 0) {
            for (int k1 = 0; k1 < size; k1++) {
                indices[k1] = buffer.get1();
            }
        }
        if (type == 1) {
            for (int l1 = 0; l1 < width; l1++) {
                for (int i2 = 0; i2 < height; i2++) {
                    indices[l1 + (i2 * width)] = buffer.get1();
                }
            }
        }
        res.setIndices(indices);
        return res;
    }
}
