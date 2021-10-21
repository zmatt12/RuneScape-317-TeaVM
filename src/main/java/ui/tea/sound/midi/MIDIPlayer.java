package ui.tea.sound.midi;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;

public abstract class MIDIPlayer implements JSObject {

    @JSBody(script = "return typeof MIDI != 'undefined' && typeof MIDI.Player != 'undefined'")
    public static native boolean isSupported();

}
