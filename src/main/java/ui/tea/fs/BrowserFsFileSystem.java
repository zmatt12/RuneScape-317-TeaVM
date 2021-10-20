package ui.tea.fs;

import org.teavm.classlib.fs.VirtualFile;
import org.teavm.classlib.fs.VirtualFileSystem;
import ui.tea.fs.bfs.BrowserFileSystem;
import ui.tea.fs.bfs.Stats;

public class BrowserFsFileSystem implements VirtualFileSystem {

    private final BrowserFileSystem fs;

    private String userDir = "/";

    public BrowserFsFileSystem() {
        if (!BrowserFileSystem.isSupported()) {
            throw new UnsupportedOperationException("BrowserFS needs to be installed!");
        }
        this.fs = BrowserFileSystem.getRootFileSystem();
    }

    public BrowserFileSystem getFs(){
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
