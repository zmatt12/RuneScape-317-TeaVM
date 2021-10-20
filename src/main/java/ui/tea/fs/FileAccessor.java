package ui.tea.fs;

import org.teavm.classlib.fs.VirtualFileAccessor;
import org.teavm.jso.core.JSNumber;
import ui.tea.fs.bfs.BrowserFileSystem;

import java.io.IOException;

final class FileAccessor implements VirtualFileAccessor {

    private JSNumber fd;
    private final BrowserFileSystem fs;
    private int pos;

    public FileAccessor(JSNumber fd, BrowserFileSystem fs){
        this.fs = fs;
        this.fd = fd;
        this.pos = 0;
    }

    @Override
    public int read(byte[] bytes, int off, int len) throws IOException {
        int i = fs.readSync(fd, bytes, off, len, pos);
        pos += i;
        return i;
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        int i = fs.writeSync(fd, bytes, off, len, pos);
        pos += i;
        flush();
    }

    @Override
    public int tell() throws IOException {
        return pos;
    }

    @Override
    public void seek(int i) throws IOException {
        pos = i;
    }

    @Override
    public void skip(int i) throws IOException {
        pos += i;
    }

    @Override
    public int size() throws IOException {
        return fs.fstatSync(fd).getSize();
    }

    @Override
    public void resize(int i) throws IOException {
        fs.ftruncateSync(fd, i);
    }

    @Override
    public void close() throws IOException {
        fs.closeSync(fd);
    }

    @Override
    public void flush() throws IOException {
        fs.fstatSync(fd);
    }
}
