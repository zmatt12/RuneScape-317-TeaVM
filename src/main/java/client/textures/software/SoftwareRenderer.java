package client.textures.software;

import client.textures.IndexedTexture;
import client.textures.RGBTexture;
import client.textures.Renderer;


public class SoftwareRenderer extends Renderer {

    private static final SoftwareRenderer instance = new SoftwareRenderer();

    private SoftwareRenderer(){

    }

    public static SoftwareRenderer getInstance(){
        return instance;
    }

    public RGBTexture createRGB(int width, int height) {
        return new RGBTextureImpl(width, height);
    }

    public RGBTexture decodeRGB(byte[] data) {
        return new RGBTextureImpl(data);
    }

    @Override
    public IndexedTexture createIndexed(int[] palette, int width, int height) {
        return new IndexedTextureImpl(palette, width, height);
    }
}
