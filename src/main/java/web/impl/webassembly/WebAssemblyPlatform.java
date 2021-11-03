package web.impl.webassembly;

import client.Entity;
import client.Model;
import web.*;
import web.impl.webassembly.modules.PlatformModule;
import web.impl.webassembly.modules.SocketModule;

import java.io.IOException;

public class WebAssemblyPlatform extends Platform {

    private WAComponent component;

    @Override
    public IComponent createComponent() {
        if(component == null){
            component = new WAComponent();
        }
        return component;
    }

    @Override
    public IFont getFont(String name, int style, int size) {
        return new ImmutableFont(name, style, size);
    }

    @Override
    public IImage<?> createImage(byte[] data) {
        int id = PlatformModule.createImage(data);
        if(id == -1){
            return null;
        }
        int width = PlatformModule.getImageWidth(id);
        int height = PlatformModule.getImageHeight(id);
        int[] pixels = PlatformModule.getImagePixels(id);
        PlatformModule.freeImage(id);
        return new WAImage(width, height, pixels);
    }

    @Override
    public IImage<?> createImage(int width, int height, int type) {
        return new WAImage(width, height);
    }

    @Override
    public IImage<?> createImage(int width, int height, int[] pixels) {
        return new WAImage(width, height, pixels);
    }

    @Override
    public ISocket openSocket(String server, int port) throws IOException {
        int fd = SocketModule.open(server, port);
        if(fd == -1){
            return null;
        }
        return new WASocket(fd);
    }

    @Override
    public IAllocator alloc() {
        return null;
    }

    @Override
    public SoundEngine sound() {
        return null;
    }

    @Override
    public String getCodeBase() {
        return PlatformModule.getCodebase();
    }

    @Override
    public Model getModel(Entity entity) {
        return null;
    }
}
