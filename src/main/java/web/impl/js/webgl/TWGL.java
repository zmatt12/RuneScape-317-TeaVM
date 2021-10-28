package web.impl.js.webgl;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;

public class TWGL implements JSObject{

    private TWGL(){

    }
    @JSBody(script = "return twgl.m4;")
    public static native M4 m4();

    public static abstract class M4 implements JSObject {
        private M4(){

        }
        public abstract float[] ortho(float f1, float f2, float f3, float f4, float f5, float f6);

        public abstract float[] translate(float[] matrix, float f1, float f2, float f3);

        public abstract float[] scale(float[] matrix, float f1, float f2, float f3);
    }
}
