package web.impl.js.fs;

import org.teavm.classlib.fs.VirtualFile;
import org.teavm.classlib.fs.VirtualFileAccessor;
import org.teavm.jso.core.*;
import web.impl.js.fs.generic.GenericFileSystem;

import java.io.IOException;

public class VirtualFileImpl implements VirtualFile {
    private final GenericFileSystem fs;
    private final String path;

    public VirtualFileImpl(GenericFileSystem fs, String path) {
        this.fs = fs;
        this.path = path;
    }

    @Override
    public String getName() {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    @Override
    public boolean isDirectory() {
        return fs.isDirectory(path);
    }

    @Override
    public boolean isFile() {
        return fs.isFile(path);
    }

    @Override
    public String[] listFiles() {
        if(!isDirectory()){
            return null;
        }
        return fs.readdir(path);
    }

    @Override
    public VirtualFileAccessor createAccessor(boolean readable, boolean writable, boolean append) {
        if(isDirectory()){
            return null;
        }
        System.out.println(path + "," + readable + "," + writable + "," + append);
        String flag = "r+";
        //TODO not hardcode flags
        return new FileAccessor(fs.open(path, flag), fs, append ? length() : 0);
    }

    @Override
    public boolean createFile(String fileName) throws IOException {
        if(!path.endsWith("/") && !fileName.startsWith("/")) {
            fileName = "/" + fileName;
        }
        String p = this.path + fileName;
        JSNumber fd = fs.open(p, "w");
        if(fd.intValue() != -1) {
            fs.close(fd);
            return true;
        }
        return false;
    }

    @Override
    public boolean createDirectory(String fileName) {
        if(!fileName.endsWith("/")) {
            fileName = fileName + "/";
        }
        System.out.println(path + fileName);
        fs.mkdir(path + fileName);
        return true;
    }

    @Override
    public boolean delete() {
        fs.unlink(this.path);
        return true;
    }

    @Override
    public boolean adopt(VirtualFile file, String fileName) {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public boolean canRead() {
        return true;
    }

    @Override
    public boolean canWrite() {
        return true;
    }

    @Override
    public long lastModified() {
        return (long) fs.mtime(path).getTime();
    }

    @Override
    public boolean setLastModified(long lastModified) {
        fs.mtime(path, (double) lastModified);
        return true;
    }

    @Override
    public boolean setReadOnly(boolean readOnly) {
        //throw new UnsupportedOperationException("unimplemented");
        return false;
    }

    @Override
    public int length() {
        return fs.length(path);
    }
}
