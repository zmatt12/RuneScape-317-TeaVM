package web.impl.js.fs.bfs;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;

public abstract class Buffer implements JSObject {


    public abstract int length();

    @JSBody(params = {"arr"}, script = "return Buffer.from(arr);")
    public static native Buffer from(byte[] arr);
}
