package ui.tea.fs;

import org.teavm.classlib.fs.VirtualFileAccessor;

import java.io.IOException;

public abstract class AbstractIndexedFile {
    String name;
    IndexedDirectory parent;
    long lastModified = System.currentTimeMillis();
    boolean readOnly;

    public AbstractIndexedFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean delete() {
        if (parent == null || (isDirectory() && listFiles().length > 0)) {
            return false;
        }

        if (parent != null && !parent.canWrite()) {
            return false;
        }

        parent.children.remove(name);
        parent.modify();
        parent = null;
        return true;
    }

    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public abstract String[] listFiles();

    public boolean canRead() {
        return true;
    }

    public boolean canWrite() {
        return !readOnly;
    }

    public long lastModified() {
        return lastModified;
    }

    public boolean setLastModified(long lastModified) {
        if (readOnly) {
            return false;
        }
        this.lastModified = lastModified;
        return true;
    }

    public boolean setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return true;
    }

    void modify() {
        lastModified = System.currentTimeMillis();
    }

    public abstract AbstractIndexedFile getChildFile(String fileName);

    public abstract VirtualFileAccessor createAccessor(boolean readable, boolean writable, boolean append);

    public abstract IndexedFile createFile(String fileName) throws IOException;

    public abstract IndexedDirectory createDirectory(String fileName);

    public abstract boolean adopt(AbstractIndexedFile file, String fileName);

    public int length() {
        return 0;
    }
}
