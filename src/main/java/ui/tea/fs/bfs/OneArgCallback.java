package ui.tea.fs.bfs;

import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSError;

@JSFunctor
public interface OneArgCallback<T> extends JSObject {
    void callback(T res);
}
