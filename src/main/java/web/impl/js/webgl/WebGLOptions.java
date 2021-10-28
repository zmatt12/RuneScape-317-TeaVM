package web.impl.js.webgl;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

public abstract class WebGLOptions implements JSObject {

    private WebGLOptions(){

    }

    @JSProperty
    public abstract void setPreserveDrawingBuffer(boolean preserve);

    @JSBody(script = "return {};")
    public static native WebGLOptions create();
}
