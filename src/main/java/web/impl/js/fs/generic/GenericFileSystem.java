package web.impl.js.fs.generic;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSDate;
import org.teavm.jso.core.JSNumber;


public abstract class GenericFileSystem implements JSObject {

    @JSBody(script = "return fs;")
    public static native GenericFileSystem getRootFileSystem();

    @JSBody(script = "return typeof fs != 'undefined'")
    public static native boolean isSupported();

    public abstract String[] readdir(String path);

    public abstract boolean isFile(String path);

    public abstract boolean isDirectory(String path);

    public abstract JSNumber open(String path, String flag);

    public abstract void close(JSNumber fd);

    public abstract void mkdir(String path);

    public abstract void unlink(String path);

    public abstract JSDate mtime(String path);

    public abstract JSDate mtime(String path, double time);

    public abstract int length(String path);

    public abstract int flength(JSNumber fd);

    public abstract int read(JSNumber fd, Buffer buffer, int offset, int length, int pos);

    public abstract int write(JSNumber fd, Buffer from, int offset, int length, int pos);

    public abstract void ftruncate(JSNumber fd, int size);
}
