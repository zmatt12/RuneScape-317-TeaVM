package web.impl.js.fs;

import org.teavm.classlib.fs.VirtualFile;
import org.teavm.classlib.fs.VirtualFileSystem;
import web.impl.js.fs.generic.GenericFileSystem;

public class GenericVirtualFileSystem implements VirtualFileSystem {

    private final GenericFileSystem fs;

    private String userDir = "/";

    public GenericVirtualFileSystem() {
        this.fs = GenericFileSystem.getRootFileSystem();
    }

    public GenericFileSystem getFs(){
        return fs;
    }

    @Override
    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    @Override
    public VirtualFile getFile(String path) {
        return new VirtualFileImpl(fs, path);
    }

    @Override
    public boolean isWindows() {
        return false;
    }

    @Override
    public String canonicalize(String path) {
        return path;
    }
}
