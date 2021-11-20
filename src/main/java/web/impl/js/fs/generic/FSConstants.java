package web.impl.js.fs.generic;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

public abstract class FSConstants implements JSObject {

    private FSConstants(){

    }

    @JSProperty("COPYFILE_EXCL")
    public native int COPYFILE_EXCL();

    @JSProperty("COPYFILE_FICLONE")
    public native int COPYFILE_FICLONE();

    @JSProperty("COPYFILE_FICLONE_FORCE")
    public native int COPYFILE_FICLONE_FORCE();

    @JSProperty("F_OK")
    public native int F_OK();

    @JSProperty("O_APPEND")
    public native int O_APPEND();

    @JSProperty("O_CREAT")
    public native int O_CREAT();

    @JSProperty("O_EXCL")
    public native int O_EXCL();

    @JSProperty("O_RDONLY")
    public native int O_RDONLY();

    @JSProperty("O_RDWR")
    public native int O_RDWR();

    @JSProperty("O_TRUNC")
    public native int O_TRUNC();

    @JSProperty("O_WRONLY")
    public native int O_WRONLY();

    @JSProperty("R_OK")
    public native int R_OK();

    @JSProperty("S_IFCHR")
    public native int S_IFCHR();

    @JSProperty("S_IFDIR")
    public native int S_IFDIR();

    @JSProperty("S_IFLNK")
    public native int S_IFLNK();

    @JSProperty("S_IFMT")
    public native int S_IFMT();

    @JSProperty("S_IFREG")
    public native int S_IFREG();

    @JSProperty("UV_DIRENT_BLOCK")
    public native int UV_DIRENT_BLOCK();

    @JSProperty("UV_DIRENT_CHAR")
    public native int UV_DIRENT_CHAR();

    @JSProperty("UV_DIRENT_DIR")
    public native int UV_DIRENT_DIR();

    @JSProperty("UV_DIRENT_FIFO")
    public native int UV_DIRENT_FIFO();

    @JSProperty("UV_DIRENT_FILE")
    public native int UV_DIRENT_FILE();
    
    @JSProperty("UV_DIRENT_LINK")
    public native int UV_DIRENT_LINK();

    @JSProperty("UV_DIRENT_SOCKET")
    public native int UV_DIRENT_SOCKET();

    @JSProperty("UV_DIRENT_UNKNOWN")
    public native int UV_DIRENT_UNKNOWN();

    @JSProperty("UV_FS_COPYFILE_EXCL")
    public native int UV_FS_COPYFILE_EXCL();

    @JSProperty("UV_FS_COPYFILE_FICLONE")
    public native int UV_FS_COPYFILE_FICLONE();

    @JSProperty("UV_FS_COPYFILE_FICLONE_FORCE")
    public native int UV_FS_COPYFILE_FICLONE_FORCE();

    @JSProperty("UV_FS_O_FILEMAP")
    public native int UV_FS_O_FILEMAP();

    @JSProperty("UV_FS_SYMLINK_DIR")
    public native int UV_FS_SYMLINK_DIR();

    @JSProperty("UV_FS_SYMLINK_JUNCTION")
    public native int UV_FS_SYMLINK_JUNCTION();

    @JSProperty("W_OK")
    public native int W_OK();

    @JSProperty("X_OK")
    public native int X_OK();
}
