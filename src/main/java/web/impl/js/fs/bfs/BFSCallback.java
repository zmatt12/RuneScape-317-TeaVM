package web.impl.js.fs.bfs;

import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSError;

@JSFunctor
public interface BFSCallback<T> extends JSObject{
    void callback(JSError arg1, T arg2);
}
