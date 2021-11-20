package web.impl.js.fs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teavm.classlib.fs.VirtualFileAccessor;
import org.teavm.jso.core.JSNumber;
import web.impl.js.fs.generic.GenericFileSystem;
import web.impl.js.fs.generic.Buffer;

import java.io.*;

class FileAccessor implements VirtualFileAccessor {

    private int fd;
    private final GenericFileSystem fs;
    private int pos;

    public FileAccessor(int fd, GenericFileSystem fs){
        this.fs = fs;
        this.fd = fd;
        this.pos = 0;
    }

    @Override
    public int read(byte[] bytes, int off, int len) throws IOException {
        int i = fs.read(fd, Buffer.from(bytes), off, len, pos);
        pos += i;
        return i;
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        int i = fs.write(fd, Buffer.from(bytes), off, len, pos);
        pos += i;
    }

    @Override
    public int tell() throws IOException {
        return pos;
    }

    @Override
    public void seek(int i) throws IOException {
        flush();
        pos = i;
    }

    @Override
    public void skip(int i) throws IOException {
        flush();
        pos += i;
    }

    @Override
    public int size() throws IOException {
        return fs.flength(fd);
    }

    @Override
    public void resize(int i) throws IOException {
        fs.ftruncate(fd, i);
    }

    @Override
    public void close() throws IOException {
        flush();
        fs.close(fd);
    }

    @Override
    public void flush() throws IOException {

    }
}
