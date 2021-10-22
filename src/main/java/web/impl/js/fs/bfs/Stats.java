package web.impl.js.fs.bfs;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

public interface Stats extends JSObject {

    @JSProperty
    int getAtime();

    @JSProperty
    int getMtime();

    @JSProperty
    int getCtime();

    @JSProperty
    int getBirthtime();

    @JSProperty
    int getUid();

    @JSProperty
    void setUid(int uid);

    @JSProperty
    int getGid();

    @JSProperty
    void setGid(int gid);

    @JSProperty
    int getSize();

    @JSProperty
    void setSize(int size);

    @JSProperty
    int getMode();

    @JSProperty
    void setMode(int mode);

    boolean isFile();

    boolean isDirectory();

    boolean isSymbolicLink();

    void chmod(int mode);

    boolean isSocket();

    boolean isBlockDevice();

    boolean isCharacterDevice();

    boolean isFIFO();
}
