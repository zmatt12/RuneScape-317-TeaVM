package ui.tea.fs.bfs;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSBoolean;
import org.teavm.jso.core.JSError;
import org.teavm.jso.core.JSNumber;
import org.teavm.jso.typedarrays.Uint8Array;


public abstract class BrowserFileSystem implements JSObject {

    @JSBody(script = "return require('fs');")
    public static native BrowserFileSystem getRootFileSystem();

    @JSBody(script = "return typeof BrowserFS != 'undefined' && typeof require != 'undefined'")
    public static native boolean isSupported();

    public abstract void rename(String oldPath, String newPath, OneArgCallback<JSError> callback);

    public abstract void renameSync(String oldPath, String newPath);

    public abstract void exists(String path, OneArgCallback<JSBoolean> callback);

    public abstract boolean existsSync(String path);

    public abstract void stat(String path, BFSCallback<Stats> callback);

    public abstract Stats statSync(String path);

    public abstract void lstat(String path, BFSCallback<Stats> callback);

    public abstract Stats lstatSync(String path);

    public abstract void truncate(String path, OneArgCallback<JSError> callback);

    public abstract void truncate(String path, int len, OneArgCallback<JSError> callback);

    public abstract void truncate(String path);

    public abstract void truncateSync(String path);

    public abstract void truncateSync(String path, int len);

    public abstract void unlink(String path, OneArgCallback<JSError> callback);

    public abstract void unlinkSync(String path);

    public abstract void open(String path, String flag, BFSCallback<JSNumber> callback);

    public abstract void open(String path, String flag, int mode, BFSCallback<JSNumber> callback);

    public abstract void open(String path, String flag, String mode, BFSCallback<JSNumber> callback);

    public abstract JSNumber openSync(String path, String flag);

    public abstract void readFile(String name, BFSCallback<Uint8Array> callback);

    public abstract Buffer readFileSync(String name);

    public abstract void writeFile(String name, String data, OneArgCallback<JSError> callback);

    public abstract void writeFile(String name, Buffer data, OneArgCallback<JSError> callback);

    public abstract void writeFileSync(String name, String data);

    public abstract void writeFileSync(String name, Buffer data);

    public abstract void appendFile(String name, String data, OneArgCallback<JSError> callback);

    public abstract void appendFile(String name, Buffer data, OneArgCallback<JSError> callback);

    public abstract void appendFileSync(String name, String data);

    public abstract void appendFileSync(String name, Buffer data);

    public abstract void fstat(JSNumber fd, BFSCallback<Stats> callback);

    public abstract Stats fstatSync(JSNumber fd);

    public abstract void close(JSNumber fd, OneArgCallback<JSError> callback);

    public abstract void closeSync(JSNumber fd);

    public abstract void ftruncate(JSNumber fd, OneArgCallback<JSError> callback);

    public abstract void ftruncate(JSNumber fd, int len, OneArgCallback<JSError> callback);

    public abstract void ftruncateSync(JSNumber fd);

    public abstract void ftruncateSync(JSNumber fd, int len);

    public abstract void fsync(JSNumber fd, OneArgCallback<JSError> callback);

    public abstract void fsyncSync(JSNumber fd);

    public abstract void fdatasync(JSNumber fd, OneArgCallback<JSError> callback);

    public abstract void fdatasyncSync(JSNumber fd);

    //TODO non-sync writes

    public abstract int writeSync(JSNumber fd, Buffer buffer, int offset, int length);

    public abstract int writeSync(JSNumber fd, Buffer buffer, int offset, int length, int pos);

    //TODO non-sync reads

    public abstract int readSync(JSNumber fd, Buffer buffer, int offset, int length);

    public abstract int readSync(JSNumber fd, Buffer buffer, int offset, int length, int position);

    public abstract void fchown(JSNumber fd, int uid, int gid, OneArgCallback<JSError> callback);

    public abstract void fchownSync(JSNumber fd, int uid, int gid);

    public abstract String[] readdirSync(String path);

    public abstract void mkdir(String filename, int mode, OneArgCallback<JSError> callback);

    public abstract void mkdirSync(String fileName);

    public abstract void utimesSync(String path, int atime, int mtime);

    public abstract void createFileSync(String path);
}
