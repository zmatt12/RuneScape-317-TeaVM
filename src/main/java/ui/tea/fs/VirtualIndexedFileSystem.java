package ui.tea.fs;

import org.teavm.classlib.fs.VirtualFile;
import org.teavm.classlib.fs.VirtualFileSystem;
import org.teavm.jso.indexeddb.IDBDatabase;

public class VirtualIndexedFileSystem implements VirtualFileSystem {

    private final IDBDatabase db;

    IndexedDirectory root = new IndexedDirectory("");

    private String userDir = "/";

    public VirtualIndexedFileSystem(IDBDatabase db) {
        this.db = db;
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
        System.out.println(path);
        return new VirtualFileImpl(this, path);
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
