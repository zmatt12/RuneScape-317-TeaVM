package ui.tea.fs;

import org.teavm.classlib.fs.VirtualFileAccessor;

import java.io.IOException;
import java.util.Arrays;

public class IndexedFile extends AbstractIndexedFile {
    byte[] data = new byte[0];
    int size;

    IndexedFile(String name) {
        super(name);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public String[] listFiles() {
        return null;
    }

    @Override
    public AbstractIndexedFile getChildFile(String fileName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VirtualFileAccessor createAccessor(boolean readable, boolean writable, boolean append) {
        if (parent == null) {
            return null;
        }

        if (writable && readOnly) {
            return null;
        }

        return new VirtualFileAccessor() {
            private int pos;

            {
                if (append) {
                    pos = size;
                } else if (writable) {
                    size = 0;
                }
            }

            @Override
            public int read(byte[] buffer, int offset, int limit) {
                limit = Math.max(0, Math.min(size - pos, limit));
                if (limit > 0) {
                    System.arraycopy(data, pos, buffer, offset, limit);
                    pos += limit;
                }
                return limit;
            }

            @Override
            public void write(byte[] buffer, int offset, int limit) {
                expandData(pos + limit);
                System.arraycopy(buffer, offset, data, pos, limit);
                pos += limit;
                if (pos > size) {
                    size = pos;
                }
                modify();
            }

            @Override
            public int tell() throws IOException {
                return pos;
            }

            @Override
            public void seek(int target) throws IOException {
                pos = target;
            }

            @Override
            public void skip(int amount) throws IOException {
                pos += amount;
            }

            @Override
            public int size() {
                return size;
            }

            @Override
            public void resize(int size) {
                expandData(size);
                IndexedFile.this.size = size;
                modify();
            }

            @Override
            public void close() {
            }

            @Override
            public void flush() {
            }
        };
    }

    @Override
    public IndexedFile createFile(String fileName) throws IOException {
        throw new IOException("Can't create file " + fileName + " since parent path denotes regular file");
    }

    @Override
    public IndexedDirectory createDirectory(String fileName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean adopt(AbstractIndexedFile file, String fileName) {
        return false;
    }

    @Override
    public int length() {
        return size;
    }

    private void expandData(int newSize) {
        if (newSize > data.length) {
            int newCapacity = Math.max(newSize, data.length) * 3 / 2;
            data = Arrays.copyOf(data, newCapacity);
        }
    }
}
