package web.impl.js;

import client.Entity;
import client.Model;
import client.ObjStackEntity;
import client.ObjType;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSObject;
import org.teavm.jso.browser.Location;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLImageElement;
import org.teavm.jso.typedarrays.Uint8Array;
import web.*;
import web.impl.js.sound.JSSoundEngine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class JSPlatform extends Platform {

    private static final JSAllocator alloc = new JSAllocator();
    private static final JSSoundEngine sound = new JSSoundEngine();
    private static JSComponent component;
    private static int portOffset;
    private static String codebase;

    private static final HTMLCanvasElement imgCanvas = Window.current().getDocument().createElement("canvas").cast();

    public static void init(String canvasId) {
        init(canvasId, 10000);
    }

    public static void init(String canvasId, int offset) {
        if (component != null) {
            return;
        }
        HTMLCanvasElement canvas = Window.current().getDocument().getElementById(canvasId).cast();
        component = new JSComponent(canvas);
        portOffset = offset;

        initCodebase();

        sound.init();
    }

    private static void initCodebase() {
        HTMLCanvasElement canvas = component.getCanvas();
        Location location = Window.current().getLocation();
        codebase = location.getProtocol() + "//" + location.getHost() + "/";
        if (JSMethods.hasData(canvas, "codebase")) {
            codebase = concatCodebase(codebase, JSMethods.getData(canvas, "codebase"));
        } else if (JSConfig.get().hasCodebase()) {
            codebase = concatCodebase(codebase, JSConfig.get().getCodebase());
        } else {
            codebase = location.getFullURL();
            codebase = codebase.substring(0, codebase.lastIndexOf('/'));
        }
        if (!codebase.endsWith("/")) {
            codebase = codebase + "/";
        }
    }

    @Async
    private static native HTMLImageElement load(String url);

    private static void load(String url, AsyncCallback<HTMLImageElement> callback) {
        HTMLImageElement img = Window.current().getDocument().createElement("img").cast();
        img.addEventListener("load", evt -> callback.complete(img));
        img.setSrc(url);
    }

    private static String concatCodebase(String base, String codebase) {
        try {
            new URL(codebase);
            return codebase;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return base + codebase;
        }
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
        Uint8Array arr = Uint8Array.create(data.length);
        arr.set(data);
        JSObject blob = JSMethods.blobify(arr, "image/png");
        String objUrl = JSMethods.createObjectUrl(blob);
        HTMLImageElement img = load(objUrl);
        imgCanvas.setWidth(img.getWidth());
        imgCanvas.setHeight(img.getHeight());
        CanvasRenderingContext2D ctx = imgCanvas.getContext("2d").cast();
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
        CanvasRenderingContext2D ctx = imgCanvas.getContext("2d").cast();
        ImageData data = ctx.createImageData(width, height);
        return new JSImage(data);
    }

    @Override
    public IImage<?> createImage(int width, int height, int[] pixels) {
        return new JSImage(pixels, width, height);
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

    @Override
    public String getCodeBase() {
        return codebase;
    }

    @Override
    public Model getModel(Entity entity) {
        JSObject obj = (JSObject) entity;
        if(JSMethods.isStackEntity(obj)) {
            ObjStackEntity e = (ObjStackEntity) entity;
            return ObjType.get(e.id).getModel(e.amount);
        }
        JSMethods.export("entity", (JSObject) entity);
        return null;
    }


}
