package ui.tea.fs;

import org.teavm.classlib.fs.VirtualFile;
import org.teavm.classlib.fs.VirtualFileAccessor;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.*;
import ui.tea.fs.bfs.BFSCallback;
import ui.tea.fs.bfs.OneArgCallback;
import ui.tea.fs.bfs.Stats;

import java.io.IOException;
import java.util.function.Supplier;

public class VirtualFileImpl implements VirtualFile {
    private final BrowserFsFileSystem fs;
    private final String path;

    public VirtualFileImpl(BrowserFsFileSystem fs, String path) {
        this.fs = fs;
        this.path = path;
    }

    private Stats stats(){
        try {
            Stats s = fs.getFs().statSync(path);
            System.out.println("-------");
            System.out.println(path);
            System.out.println("isFile:" + s.isFile() + " - isDir:" + s.isDirectory());
            System.out.println("uid:" + s.getUid());
            System.out.println("gid:" + s.getGid());
            System.out.println("atime:" + s.getAtime());
            System.out.println("mtime:" + s.getMtime());
            System.out.println("ctime:" + s.getCtime());
            System.out.println("size:" + s.getSize());
            return s;
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
        String flag = "rw+";
        if(append){
            flag = "a+";
        }
        //TODO not hardcode flags
        return new FileAccessor(fs.getFs().openSync(path, flag), fs.getFs());
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
        if(s != null && s.isFile()){
            int i = fs.getFs().readFileSync(path).length;
            System.out.println(i);
            return i;
        }
        return s != null ? s.getSize() : -1;
    }
}
