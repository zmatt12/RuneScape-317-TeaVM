package web;

import client.Entity;
import client.Model;
import client.textures.Renderer;
import client.textures.software.SoftwareRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.impl.js.JSPlatform;

import java.io.IOException;

public abstract class Platform {

    private static final Logger logger = LoggerFactory.getLogger(Platform.class);

    private static final Platform defaultEngine = findDefault();

    private static String text;

    private static Platform findDefault() {
        //This dirty hack is to force the class check to be at runtime, otherwise TeaVM will attempt to
        //compile all of AWT, and we really don't want that
        try {
            text = "java.awt.Component";
            Class.forName(text);
            text = "web.impl.jvm.impl.JVMPlatform";
            logger.info("Using JVM Platform");
            return (Platform) Class.forName(text).getDeclaredConstructor().newInstance();
        } catch (Throwable ignored) {
        }
        logger.info("Using JS Platform");
        return new JSPlatform();
    }

    public Renderer getRenderer(){
        return SoftwareRenderer.getInstance();
    }

    public static Platform getDefault() {
        return defaultEngine;
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
