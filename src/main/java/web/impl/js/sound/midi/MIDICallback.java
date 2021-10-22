package web.impl.js.sound.midi;

import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

@JSFunctor
public interface MIDICallback extends JSObject {
    void run();
}
