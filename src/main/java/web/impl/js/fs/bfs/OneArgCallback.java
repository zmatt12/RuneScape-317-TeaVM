package web.impl.js.fs.bfs;

import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

@JSFunctor
public interface OneArgCallback<T> extends JSObject {
    void callback(T res);
}
