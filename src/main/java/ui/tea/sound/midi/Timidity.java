package ui.tea.sound.midi;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;

public abstract class Timidity implements JSObject {

    @JSBody(script = "return typeof Timidity != 'undefined'")
    public static native boolean isSupported();

    @JSBody(script = "return Timidity")
    public static native Timidity get();

    public abstract void load(String path);

    public abstract void play();
}
