package ui.tea;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.typedarrays.Uint8Array;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import org.teavm.jso.websocket.WebSocket;

public final class JSMethods {

    private JSMethods() {

    }

    @JSBody(params = {"ctx"}, script = "return ctx.webkitBackingStorePixelRatio ||\n" +
            "ctx.mozBackingStorePixelRatio ||\n" +
            "ctx.msBackingStorePixelRatio ||\n" +
            "ctx.oBackingStorePixelRatio ||\n" +
            "ctx.backingStorePixelRatio || 1")
    public static native double getBackingStoreRatio(CanvasRenderingContext2D ctx);

    @JSBody(params = {"canvas", "width", "height"}, script = "canvas.width = width; canvas.height=height;")
    public static native void setSize(HTMLCanvasElement canvas, double width, double height);

    @JSBody(params = {"ws", "data"}, script = "return ws.send(data);")
    public static native void send(WebSocket ws, Uint8Array data);

    @JSBody(params = {"arr", "type"}, script = "return new Blob([arr], {type:type});")
    public static native JSObject blobify(Uint8Array arr, String type);

    @JSBody(params = {"blob"}, script = "return (window.URL || window.webkitURL).createObjectURL(blob);")
    public static native String createObjectUrl(JSObject blob);

    @JSBody(params = { "name", "value"}, script = "return window[name] = value")
    public static native void export(String name, JSObject value);
}
