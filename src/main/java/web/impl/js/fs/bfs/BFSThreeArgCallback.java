package web.impl.js.fs.bfs;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSError;

public interface BFSThreeArgCallback <T, V> extends JSObject {
    void callback(JSError arg1, T arg2, V arg3);
}