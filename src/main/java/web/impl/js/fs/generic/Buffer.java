package web.impl.js.fs.generic;

import org.teavm.jso.JSBody;
import org.teavm.jso.typedarrays.Uint8Array;

public abstract class Buffer extends Uint8Array {

    @JSBody(params = {"arr"}, script = "return fs.Buffer.from(arr);")
    public static native Buffer from(byte[] arr);
}
