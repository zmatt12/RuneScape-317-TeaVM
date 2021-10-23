package web.impl.js;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSObject;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLImageElement;
import org.teavm.jso.typedarrays.Uint8Array;
import web.*;
import web.impl.js.sound.JSSoundEngine;

import java.io.IOException;

public final class JSPlatform extends Platform {

    private static JSComponent component;
    private static final JSAllocator alloc = new JSAllocator();
    private static final JSSoundEngine sound = new JSSoundEngine();
    private static int portOffset;

    public static void init(String canvasId) {
        init(canvasId, 10000);
    }

    public static void init(String canvasId, int offset) {
        if (component != null) {
            return;
        }
        HTMLCanvasElement canvas = Window.current().getDocument().getElementById(canvasId).cast();
        CanvasRenderingContext2D context = (CanvasRenderingContext2D) canvas.getContext("2d");
        component = new JSComponent(canvas, context);
        portOffset = offset;

        sound.init();
    }

    @Async
    private static native HTMLImageElement load(String url);

    private static void load(String url, AsyncCallback<HTMLImageElement> callback) {
        HTMLImageElement img = Window.current().getDocument().createElement("img").cast();
        img.addEventListener("load", evt -> callback.complete(img));
        img.setSrc(url);
    }

    @Override
    public IComponent createComponent() {
        return component;
    }

    @Override
    public IFont getFont(String name, int style, int size) {
        return new ImmutableFont(name, style, size);
    }

    @Override
    public IImage createImage(byte[] data) {
        HTMLCanvasElement c = Window.current().getDocument().createElement("canvas").cast();
        Uint8Array arr = Uint8Array.create(data.length);
        arr.set(data);
        JSObject blob = JSMethods.blobify(arr, "image/png");
        String objUrl = JSMethods.createObjectUrl(blob);
        HTMLImageElement img = load(objUrl);
        c.setWidth(img.getWidth());
        c.setHeight(img.getHeight());
        CanvasRenderingContext2D ctx = c.getContext("2d").cast();
        ctx.drawImage(img, 0, 0);
        ImageData iData = ctx.getImageData(0, 0, img.getWidth(), img.getHeight());
        JSMethods.revokeObjectURL(objUrl);
        return new JSImage(iData);
    }

    @Override
    public IImage createImage(int width, int height, int type) {
        if (type != IImage.TYPE_INT_RGB) {
            throw new RuntimeException("Bad image type");
        }
        ImageData data = component.getContext().createImageData(width, height);
        return new JSImage(data);
    }

    @Override
    public ISocket openSocket(String server, int port) throws IOException {
        port -= portOffset;
        return JSSocket.open(server, port);
    }

    @Override
    public IAllocator alloc() {
        return alloc;
    }

    @Override
    public SoundEngine sound() {
        return sound;
    }


}
