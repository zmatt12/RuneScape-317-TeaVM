package web.impl.js.fs;

import org.teavm.classlib.fs.VirtualFile;
import org.teavm.classlib.fs.VirtualFileAccessor;
import org.teavm.jso.core.*;
import web.impl.js.fs.bfs.Stats;

import java.io.IOException;

public class VirtualFileImpl implements VirtualFile {
    private final BrowserFsFileSystem fs;
    private final String path;

    public VirtualFileImpl(BrowserFsFileSystem fs, String path) {
        this.fs = fs;
        this.path = path;
    }

    private Stats stats(){
        try {
            return fs.getFs().statSync(path);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String getName() {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    @Override
    public boolean isDirectory() {
        Stats s = stats();
        return s != null && s.isDirectory();
    }

    @Override
    public boolean isFile() {
        Stats s = stats();
        return s != null && s.isFile();
    }

    @Override
    public String[] listFiles() {
        if(!isDirectory()){
            return null;
        }
        return fs.getFs().readdirSync(this.path);
    }

    @Override
    public VirtualFileAccessor createAccessor(boolean readable, boolean writable, boolean append) {
        if(isDirectory()){
            return null;
        }
        System.out.println(path + "," + readable + "," + writable + "," + append);
        String flag = "a+";
        //TODO not hardcode flags
        return new FileAccessor(fs.getFs().openSync(path, flag), fs.getFs(), append ? length() : 0);
    }

    @Override
    public boolean createFile(String fileName) throws IOException {
        if(!path.endsWith("/") && !fileName.startsWith("/")) {
            fileName = "/" + fileName;
        }
        String p = this.path + fileName;
        JSNumber fd = fs.getFs().openSync(p, "w");
        if(fd.intValue() != -1) {
            fs.getFs().closeSync(fd);
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
        fs.getFs().mkdirSync(path + fileName);
        return true;
    }

    @Override
    public boolean delete() {
        fs.getFs().unlinkSync(this.path);
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
        Stats s = stats();
        return s != null ? s.getMtime() : -1;
    }

    @Override
    public boolean setLastModified(long lastModified) {
        Stats s = stats();
        if(s == null){
            return false;
        }
        fs.getFs().utimesSync(this.path, s.getAtime(), (int) lastModified);
        return true;
    }

    @Override
    public boolean setReadOnly(boolean readOnly) {
        //throw new UnsupportedOperationException("unimplemented");
        return false;
    }

    @Override
    public int length() {
        Stats s = stats();
        return s != null ? s.getSize() : -1;
    }
}
