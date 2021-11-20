package web.impl.js;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Int8Array;
import web.IFileManager;
import web.IFileStore;


public class IndexedDBFileManager implements IFileManager {

    public static final String STORE_NAME = "file_store";
    public static final String DB_NAME = "cache";

    private IDBFileStore store;

    @Override
    public void init(String path) {
        if (store != null) {
            throw new RuntimeException("Database is already open!");
        }
        store = IDBFileStore.create(DB_NAME, STORE_NAME);
        openImpl();
    }

    @Override
    public boolean canLoad() {
        return store != null;
    }

    @Override
    public IFileStore openOrCreateStore(int maxSize, int index) {
        return new IFileStore() {

            @Override
            public byte[] read(int fileId) {
                Int8Array arr = readImpl(fileId);
                if (arr == null) {
                    return null;
                }
                return JSMethods.unwrap(arr);
            }

            @Async
            public native Int8Array readImpl(int fileId);

            public void readImpl(int fileId, AsyncCallback<Int8Array> callback) {
                store.read(index, fileId, callback::complete, err -> callback.error(new Exception("Unable to write " + index + "-" + fileId + " Reason:" + err)));
            }

            @Async
            public void write(byte[] data, int fileId, int length) {
                Int8Array arr = Int8Array.create(data.length);
                arr.set(data);
                store.write(index, fileId, arr, () -> {
                }, System.out::println);
            }
        };
    }

    @Async
    private native JSObject openImpl();

    private void openImpl(AsyncCallback<JSObject> callback) {
        store.open(() -> callback.complete(null), err -> callback.error(new Exception("Unable to open DB Reason:" + err)));
    }
}
