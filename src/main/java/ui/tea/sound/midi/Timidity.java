package ui.tea.sound.midi;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Uint8Array;

public abstract class Timidity implements JSObject {

    @JSBody(script = "return typeof Timidity != 'undefined'")
    public static native boolean isSupported();

    @JSBody(script = "return Timidity")
    public static native Timidity get();

    public abstract void on(String event, Callback cb);

    public abstract void load(String path);

    public abstract void load(Uint8Array buf);

    public abstract void play();

    @JSFunctor
    public interface Callback extends JSObject{
        void run();
    }
}
