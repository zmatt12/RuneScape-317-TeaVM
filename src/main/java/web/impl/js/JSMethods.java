package web.impl.js;

import client.Entity;
import client.ObjStackEntity;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Int32Array;
import org.teavm.jso.typedarrays.Uint8Array;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import org.teavm.jso.websocket.WebSocket;

public final class JSMethods {

    private JSMethods() {

    }

    @JSBody(params = {"obj", "propertyName"}, script = "return typeof obj[propertyName] != 'undefined'")
    public static native boolean has(JSObject obj, String propertyName);

    @JSBody(params = {"ws", "data"}, script = "return ws.send(data);")
    public static native void send(WebSocket ws, Uint8Array data);

    @JSBody(params = {"arr", "type"}, script = "return new Blob([arr], {type:type});")
    public static native JSObject blobify(Uint8Array arr, String type);

    @JSBody(params = {"elem", "prop"}, script = "return typeof elem.dataset[prop] != 'undefined'")
    public static native boolean hasData(HTMLElement elem, String prop);

    @JSBody(params = {"elem", "prop"}, script = "return elem.dataset[prop];")
    public static native String getData(HTMLElement elem, String prop);

    @JSBody(params = {"blob"}, script = "return (window.URL || window.webkitURL).createObjectURL(blob);")
    public static native String createObjectUrl(JSObject blob);

    @JSBody(params = {"url"}, script = "return (window.URL || window.webkitURL).revokeObjectURL(url);")
    public static native void revokeObjectURL(String url);

    @JSBody(params = { "name", "value"}, script = "return window[name] = value;")
    public static native void export(String name, JSObject value);

    @JSBody(params = {"arr"}, script = "return arr;")
    public static native Int32Array wrap(int[] arr);

    @JSBody(params = {"arr"}, script = "return arr;")
    public static native Uint8ClampedArray wrap(byte[] data);

    @JSBody(params = {"name"}, script = "return new Event(name);")
    public static native Event createEvent(String name);

    @JSBody(params = {"name", "detail"}, script = "return new CustomEvent(name, {detail: detail});")
    public static native Event createEvent(String name, JSObject detail);

    @JSBody(params = {"object"}, script = "return typeof object == 'object' && object.constructor['name'] == 'c_ObjStackEntity'")
    public static native boolean isStackEntity(JSObject object);

    @JSBody(params = { "len"}, script = "return new ArrayBuffer(len);")
    public static native ArrayBuffer createBuffer(int len);

    @JSBody(params = { "buffer"}, script = "return new Int32Array(buffer);")
    public static native int[] intFromBuffer(ArrayBuffer buffer);

    @JSBody(params = { "buffer"}, script = "return new Uint8ClampedArray(buffer);")
    public static native byte[] bytesFromBuffer(ArrayBuffer buffer);
}
