package web.impl.js;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.browser.Window;

public abstract class JSConfig implements JSObject {

    private JSConfig() {

    }
    public static boolean exists(){
        return JSMethods.has(Window.current(), "config317");
    }

    public boolean logEvents(){
        return JSMethods.has(this, "logEvents") && _logEvents();
    }

    @JSProperty("logEvents")
    private native boolean _logEvents();

    @JSBody(script = "return window.config317;")
    public static native JSConfig get();

    @JSProperty("timidity")
    public native TimidityConfig timidity();

    @JSProperty("file")
    public native FileViewerConfig file();

    @JSProperty
    public native String getCanvasId();

    @JSProperty
    public abstract int getPortOffset();

    public boolean hasFile(){
        return JSMethods.has(this, "file");
    }

    public boolean hasServer(){
        return JSMethods.has(this, "server");
    }

    public boolean hasPortOffset(){
        return JSMethods.has(this, "portOffset");
    }

    @JSProperty("server")
    public abstract String getServer();

    public static abstract class TimidityConfig implements JSObject {
        private TimidityConfig() {

        }

        @JSProperty
        public native String getBaseUrl();
    }

    public static abstract class FileViewerConfig implements JSObject {

        private FileViewerConfig() {

        }

        @JSProperty
        public abstract String getTableId();

        @JSProperty
        public abstract String getNumberId();

        @JSProperty
        public abstract String getCurrentId();
    }
}
