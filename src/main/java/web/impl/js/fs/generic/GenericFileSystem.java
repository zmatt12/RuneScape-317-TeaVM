package web.impl.js.fs.generic;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.core.JSDate;

public abstract class GenericFileSystem implements JSObject {

    @JSBody(script = "return fs;")
    public static native GenericFileSystem getRootFileSystem();

    @JSBody(script = "return typeof fs != 'undefined'")
    public static native boolean isSupported();

    @JSProperty("constants")
    public native FSConstants constants();

    public abstract String[] readdir(String path);

    public abstract boolean isFile(String path);

    public abstract boolean isDirectory(String path);

    public abstract int open(String path, int flag);

    public abstract void close(int fd);

    public abstract void mkdir(String path);

    public abstract void unlink(String path);

    public abstract JSDate mtime(String path);

    public abstract JSDate mtime(String path, double time);

    public abstract int length(String path);

    public abstract int flength(int fd);

    public abstract int read(int fd, Buffer buffer, int offset, int length, int pos);

    public abstract int write(int fd, Buffer from, int offset, int length, int pos);

    public abstract void ftruncate(int fd, int size);

    public abstract boolean exists(String path);

    public abstract void createFile(String path);
}
