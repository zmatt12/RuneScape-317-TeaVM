package web.impl.js.webgl;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;

public abstract class M4 implements JSObject {
    private M4() {

    }

    public abstract float[] orthographic(float f1, float f2, float f3, float f4, float f5, float f6);

    public abstract float[] translate(float[] matrix, float f1, float f2, float f3);

    public abstract float[] scale(float[] matrix, float f1, float f2, float f3);

    @JSBody(script = "return m4;")
    public static native M4 get();

}
