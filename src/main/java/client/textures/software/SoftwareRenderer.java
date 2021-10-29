package client.textures.software;

import client.FileArchive;
import client.textures.IndexedTexture;
import client.textures.RGBTexture;
import client.textures.Renderer;

import java.io.IOException;

public class SoftwareRenderer extends Renderer {

    private static final SoftwareRenderer instance = new SoftwareRenderer();

    private SoftwareRenderer(){

    }

    public static SoftwareRenderer getInstance(){
        return instance;
    }

    public RGBTexture create(int width, int height) {
        return new RGBTextureImpl(width, height);
    }

    public RGBTexture create(byte[] data) {
        return new RGBTextureImpl(data);
    }

    public RGBTexture create(FileArchive archive, String file, int index) throws IOException {
        return new RGBTextureImpl(archive, file, index);
    }

    public IndexedTexture createIndexed(FileArchive archive, String s, int i) throws IOException {
        return new IndexedTextureImpl(archive, s, i);
    }
}
