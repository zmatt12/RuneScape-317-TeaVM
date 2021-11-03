package web.impl.webassembly.modules;

import org.teavm.interop.Import;

public final class SocketModule {

    public static final String MODULE_NAME = "socket";

    private SocketModule(){

    }

    @Import(module = MODULE_NAME, name = "open")
    public static native int open(String server, int port);
}
