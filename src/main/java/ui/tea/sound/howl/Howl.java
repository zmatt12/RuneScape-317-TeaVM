package ui.tea.sound.howl;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.dom.events.EventListener;

public abstract class Howl implements JSObject {

    public abstract int play();

    public abstract int play(String sprite);

    public abstract int play(int id);

    public abstract void pause(int id);

    public abstract void stop();

    public abstract void stop(int id);

    public abstract void mute();

    public abstract void mute(boolean muted);

    public abstract void mite(boolean muted, int id);

    public abstract double volume();

    public abstract void volume(double volume);

    public abstract void volume(double volume, int id);

    public abstract void fade(double from, double to, int duration);

    public abstract void fade(double from, double to, int duration, int id);

    public abstract double rate();

    public abstract void rate(double rate);

    public abstract void rate(double rate, int id);

    public abstract int seek();

    public abstract void seek(int seek);

    public abstract void seek(int seek, int id);

    public abstract int loop();

    public abstract void loop(boolean loop);

    public abstract void loop(boolean loop, int id);

    public abstract String state();

    public abstract boolean playing();

    public abstract boolean playing(int id);

    public abstract int duration();

    public abstract int duration(int id);

    public abstract void on(String eventName, EventListener<?> callback);

    public abstract void on(String eventName, EventListener<?> callback, int id);

    public abstract void once(String eventName, EventListener<?> callback);

    public abstract void once(String eventName, EventListener<?> callback, int id);

    public abstract void off(String eventName);

    public abstract void off(String eventName, EventListener<?> callback);

    public abstract void off(String eventName, EventListener<?> callback, int id);

    public abstract void load();

    public abstract void unload();

    @JSBody(script = "return typeof Howl != 'undefined'")
    public static native boolean isSupported();

    @JSBody(params = {"config"}, script = "return new Howl(config);")
    public static native Howl create(HowlConfig config);
}
