package web.impl.js.sound.midi;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.typedarrays.Uint8Array;
import org.teavm.jso.webaudio.AudioContext;
import org.teavm.jso.webaudio.GainNode;

public abstract class Timidity implements JSObject {

    @JSBody(script = "return typeof timidity != 'undefined'")
    public static native boolean isSupported();

    @JSBody(params = {"baseUrl"}, script = "return new timidity(baseUrl)")
    public static native Timidity create(String baseUrl);

    @JSBody(params = {"event", "cb"}, script = "return this.on(event, cb);")
    public abstract void on(String event, Callback cb);

    public abstract void load(String path);

    public abstract void load(Uint8Array buf);

    public abstract void play();

    public abstract void pause();

    @JSProperty
    public abstract void setVolume(float vol);

    @JSProperty
    public abstract float getVolume();

    @JSFunctor
    public interface Callback extends JSObject{
        void run();
    }
}
