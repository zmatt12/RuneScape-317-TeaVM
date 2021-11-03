package web;

import client.Entity;
import client.Model;
import client.textures.Renderer;
import client.textures.software.SoftwareRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teavm.classlib.PlatformDetector;
import web.impl.js.JSPlatform;
import web.impl.webassembly.WebAssemblyPlatform;

import java.io.IOException;

public abstract class Platform {

    private static Platform defaultPlatform = null;

    public static void setDefaultPlatform(Platform platform) {
        defaultPlatform = platform;
    }

    public static Platform getDefault() {
        return defaultPlatform;
    }

    public Renderer getRenderer() {
        return SoftwareRenderer.getInstance();
    }

    public abstract IComponent createComponent();

    public abstract IFont getFont(String name, int style, int size);

    public abstract IImage<?> createImage(byte[] data);

    public abstract IImage<?> createImage(int width, int height, int type);

    public abstract IImage<?> createImage(int width, int height, int[] pixels);

    public abstract ISocket openSocket(String server, int port) throws IOException;

    public abstract IAllocator alloc();

    public abstract SoundEngine sound();

    public abstract String getCodeBase();

    public abstract Model getModel(Entity entity);
}
