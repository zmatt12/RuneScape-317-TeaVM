package web.impl.js;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Int8Array;

public abstract class IDBFileStore implements JSObject {

    @JSBody(script = "return typeof IDBFileStore !== 'undefined'")
    static native boolean exists();

    @JSBody(params = {"dbName", "storeName"}, script = "return new IDBFileStore(dbName, storeName)")
    static native IDBFileStore create(String dbName, String storeName);

    public abstract void open(NoArgCallback onsuccess, OneArgCallback<JSObject> onerror);

    public abstract void read(int storeId, int fileId, OneArgCallback<Int8Array> onsuccess, OneArgCallback<JSObject> onerror);

    public abstract void write(int storeId, int fileId, Int8Array data, NoArgCallback onsuccess, OneArgCallback<JSObject> onerror);

    @JSFunctor
    public interface  NoArgCallback extends JSObject{
        void apply();
    }

    @JSFunctor
    public interface OneArgCallback<T extends JSObject> extends JSObject{
        void apply(T data);
    }
}