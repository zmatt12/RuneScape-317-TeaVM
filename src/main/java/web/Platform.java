package web;

import web.impl.js.JSPlatform;

import java.io.IOException;

public abstract class Platform {

    private static String text;
    private static final Platform defaultEngine = findDefault();

    private static Platform findDefault() {
        //This dirty hack is to force the class check to be at runtime, otherwise TeaVM will attempt to
        //compile all of AWT, and we really don't want that
        text = "java.awt.Component";
        try {
            Class.forName(text);
            text = "web.impl.jvm.impl.JVMPlatform";
            return (Platform) Class.forName(text).getDeclaredConstructor().newInstance();
        } catch (Throwable ignored) {
        }
        return new JSPlatform();
    }

    public static Platform getDefault() {
        return defaultEngine;
    }

    public abstract IComponent createComponent();

    public abstract IFont getFont(String name, int style, int size);

    public abstract IImage<?> createImage(byte[] data);

    public abstract IImage<?> createImage(int width, int height, int type);

    public abstract ISocket openSocket(String server, int port) throws IOException;

    public abstract IAllocator alloc();

    public abstract SoundEngine sound();
}
