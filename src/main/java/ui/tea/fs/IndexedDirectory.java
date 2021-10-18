package ui.tea.fs;

import org.teavm.classlib.fs.VirtualFileAccessor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class IndexedDirectory extends AbstractIndexedFile {
    final Map<String, AbstractIndexedFile> children = new LinkedHashMap<>();

    public IndexedDirectory(String name) {
        super(name);
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public String[] listFiles() {
        return children.keySet().toArray(new String[0]);
    }

    @Override
    public AbstractIndexedFile getChildFile(String fileName) {
        return children.get(fileName);
    }

    @Override
    public VirtualFileAccessor createAccessor(boolean readable, boolean writable, boolean append) {
        return null;
    }

    @Override
    public IndexedFile createFile(String fileName) throws IOException {
        if (!canWrite()) {
            throw new IOException("Directory is read-only");
        }
        if (children.containsKey(fileName)) {
            return null;
        }
        IndexedFile file = new IndexedFile(fileName);
        adoptFile(file);
        return file;
    }

    @Override
    public IndexedDirectory createDirectory(String fileName) {
        if (!canWrite() || getChildFile(fileName) != null) {
            return null;
        }
        IndexedDirectory file = new IndexedDirectory(fileName);
        adoptFile(file);
        return file;
    }

    @Override
    public boolean adopt(AbstractIndexedFile file, String fileName) {
        if (!canWrite()) {
            return false;
        }
        if (!file.parent.canWrite()) {
            return false;
        }
        file.parent.children.remove(file.name);
        file.parent = this;
        children.put(fileName, file);
        file.name = fileName;
        return true;
    }

    private void adoptFile(AbstractIndexedFile file) {
        if (children.containsKey(file.name)) {
            throw new IllegalArgumentException("File " + file.getName() + " already exists");
        }
        file.parent = this;
        children.put(file.name, file);
        modify();
    }
}
