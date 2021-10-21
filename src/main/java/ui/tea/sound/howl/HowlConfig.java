package ui.tea.sound.howl;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.dom.events.EventListener;

public abstract class HowlConfig implements JSObject {

    @JSProperty
    public abstract void setVolume(double vol);

    @JSProperty
    public abstract void setHtml5(boolean html5);

    @JSProperty
    public abstract void setLoop(boolean loop);

    @JSProperty("preload")
    public abstract void setPreloadBool(boolean preload);

    @JSProperty("preload")
    public abstract void setPreloadString(String preload);

    @JSProperty
    public abstract void setAutoplay(boolean autoplay);

    @JSProperty
    public abstract void setMute(boolean mute);

    //TODO sprite

    @JSProperty
    public abstract void setRate(double rate);

    @JSProperty
    public abstract void setPool(int pool);

    @JSProperty
    public abstract void setFormat(String format);

    @JSProperty
    public abstract void setFormat(String[] format);

    @JSProperty("onload")
    public abstract void setOnload(EventListener<?> callback);

    @JSProperty("onloaderror") //TODO proper error handler callback
    public abstract void setOnloadError(EventListener<?> callback);

    @JSProperty("onplay")
    public abstract void setOnPlay(EventListener<?> callback);

    @JSProperty("onplayerror") //TODO proper error handler callback
    public abstract void setOnPlayError(EventListener<?> callback);

    @JSProperty("onend")
    public abstract void setOnEnd(EventListener<?> callback);

    @JSProperty("onpause")
    public abstract void setOnPause(EventListener<?> callback);

    @JSProperty("onstop")
    public abstract void setOnStop(EventListener<?> callback);

    @JSProperty("onmute")
    public abstract void setOnMute(EventListener<?> callback);

    @JSProperty("onvolume")
    public abstract void setOnVolume(EventListener<?> callback);

    @JSProperty("onrate")
    public abstract void setOnRate(EventListener<?> callback);

    @JSProperty("onseek")
    public abstract void setOnSeek(EventListener<?> callback);

    @JSProperty("onfade")
    public abstract void setOnFade(EventListener<?> callback);

    @JSProperty("onunlock")
    public abstract void setOnUnlock(EventListener<?> callback);

    @JSBody(params = {"src"},script = "return {src: src}")
    public static native HowlConfig create(String src);

    @JSBody(params = {"src"},script = "return {src: src}")
    public static native HowlConfig create(String[] src);
}
