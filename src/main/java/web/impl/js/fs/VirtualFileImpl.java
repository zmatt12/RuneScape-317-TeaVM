package web.impl.js.fs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teavm.classlib.fs.VirtualFile;
import org.teavm.classlib.fs.VirtualFileAccessor;
import org.teavm.jso.core.*;
import web.impl.js.fs.generic.FSConstants;
import web.impl.js.fs.generic.GenericFileSystem;

import java.io.IOException;

public class VirtualFileImpl implements VirtualFile {

    private static final Logger logger = LoggerFactory.getLogger(VirtualFileImpl.class);

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
        FSConstants constants = fs.constants();
        int mode = constants.O_RDWR();
//        if(readable && !writable){
//            mode = constants.O_RDONLY();
//        }else if(!readable && writable){
//            mode = constants.O_WRONLY();
//        }else{
//            mode = constants.O_RDWR();
//        }

        if(append){
            mode |= constants.O_APPEND();
        }
        logger.info("{}, flags: {}, {}, {} - mode:{}", path, readable, writable, append, mode);
        //return new BufferedFileAccessor(fs.open(path, mode), fs);
        return new FileAccessor(fs.open(path, mode), fs);
    }

    @Override
    public boolean createFile(String fileName) throws IOException {
        if(!path.endsWith("/") && !fileName.startsWith("/")) {
            fileName = "/" + fileName;
        }
        String p = this.path + fileName;
        FSConstants constants = fs.constants();
        int flag = constants.O_WRONLY() | constants.O_CREAT();
        int fd = fs.open(p, flag);
        if(fd != -1) {
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
