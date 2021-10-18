package ui;

import ui.tea.TeaEngine;

import java.io.IOException;
import java.net.UnknownHostException;

public abstract class WindowEngine {

    private static String text;
    private static final WindowEngine defaultEngine = getDefaultEngine();

    private static WindowEngine getDefaultEngine() {
        //This dirty hack is to force the class check to be at runtime, otherwise TeaVM will attempt to
        //compile all of AWT, and we really don't want that
        text = "java.awt.Component";
        try {
            Class.forName(text);
            text = "stuff.awt.impl.JVMWindowEngine";
            return (WindowEngine) Class.forName(text).getDeclaredConstructor().newInstance();
        } catch (Throwable t) {

        }
        return new TeaEngine();
    }

    public static WindowEngine getDefault() {
        return defaultEngine;
    }

    public abstract IComponent createComponent();

    public abstract IFont getFont(String name, int style, int size);

    public abstract IImage createImage(byte[] data);

    public abstract IImage createImage(int width, int height, int type);

    public abstract ISocket openSocket(String server, int port) throws IOException;
}
