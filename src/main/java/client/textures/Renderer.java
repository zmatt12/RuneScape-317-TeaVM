package client.textures;

import client.FileArchive;
import client.textures.software.SoftwareRenderer;

import java.io.IOException;

public abstract class Renderer {

    protected Renderer(){

    }

    public static Renderer get(){
        return SoftwareRenderer.getInstance();
    }

    public abstract RGBTexture create(int width, int height);

    public abstract RGBTexture create(byte[] data);

    public abstract RGBTexture create(FileArchive archive, String file, int index) throws IOException;

    public abstract IndexedTexture createIndexed(FileArchive archive, String s, int i) throws IOException;
}
