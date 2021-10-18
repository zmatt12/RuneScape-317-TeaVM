package ui.tea.fs;

import org.teavm.classlib.fs.VirtualFile;
import org.teavm.classlib.fs.VirtualFileAccessor;

import java.io.IOException;

public class VirtualFileImpl implements VirtualFile {
    private VirtualIndexedFileSystem fs;
    private String path;

    public VirtualFileImpl(VirtualIndexedFileSystem fs, String path) {
        this.fs = fs;
        this.path = path;
    }

    @Override
    public String getName() {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    @Override
    public boolean isDirectory() {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null && inMemory.isDirectory();
    }

    @Override
    public boolean isFile() {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null && inMemory.isFile();
    }

    @Override
    public String[] listFiles() {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null ? inMemory.listFiles() : null;
    }

    @Override
    public VirtualFileAccessor createAccessor(boolean readable, boolean writable, boolean append) {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null ? inMemory.createAccessor(readable, writable, append) : null;
    }

    @Override
    public boolean createFile(String fileName) throws IOException {
        AbstractIndexedFile inMemory = findInMemory();
        if (inMemory == null) {
            throw new IOException("Directory does not exist");
        }
        return inMemory.createFile(fileName) != null;
    }

    @Override
    public boolean createDirectory(String fileName) {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null && inMemory.createDirectory(fileName) != null;
    }

    @Override
    public boolean delete() {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null && inMemory.delete();
    }

    @Override
    public boolean adopt(VirtualFile file, String fileName) {
        AbstractIndexedFile inMemory = findInMemory();
        if (inMemory == null) {
            return false;
        }
        AbstractIndexedFile fileInMemory = ((VirtualFileImpl) file).findInMemory();
        if (fileInMemory == null) {
            return false;
        }
        return inMemory.adopt(fileInMemory, fileName);
    }

    @Override
    public boolean canRead() {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null && inMemory.canRead();
    }

    @Override
    public boolean canWrite() {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null && inMemory.canWrite();
    }

    @Override
    public long lastModified() {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null ? inMemory.lastModified() : 0;
    }

    @Override
    public boolean setLastModified(long lastModified) {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null && inMemory.setLastModified(lastModified);
    }

    @Override
    public boolean setReadOnly(boolean readOnly) {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null && inMemory.setReadOnly(readOnly);
    }

    @Override
    public int length() {
        AbstractIndexedFile inMemory = findInMemory();
        return inMemory != null ? inMemory.length() : 0;
    }

    AbstractIndexedFile findInMemory() {
        AbstractIndexedFile file = fs.root;
        int i = 0;
        if (path.startsWith("/")) {
            i++;
        }

        while (i < path.length()) {
            int next = path.indexOf('/', i);
            if (next < 0) {
                next = path.length();
            }

            file = file.getChildFile(path.substring(i, next));
            if (file == null) {
                break;
            }

            i = next + 1;
        }

        return file;
    }
}
