package ui.tea;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import org.teavm.jso.websocket.WebSocket;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class WebSocketInputStream extends InputStream {

    private final WebSocket socket;
    private final List<Uint8ClampedArray> buffers = new LinkedList<>();
    private Uint8ClampedArray curr = null;
    private int index = 0;
    private AsyncCallback<CallbackResult> callback = null;
    private boolean closed = false;

    public WebSocketInputStream(WebSocket socket) {
        this.socket = socket;
        socket.onMessage(evt -> {
            Uint8ClampedArray arr = Uint8ClampedArray.create(evt.getDataAsArray());
            System.out.println("Received Data:" + arr.getLength());
            buffers.add(arr);
            completeAndDelete(CallbackResult.READ);
        });
        socket.onError(evt -> {
            errorAndDelete(new IOException("Socket Error"));
        });
    }

    private void errorAndDelete(Throwable t) {
        AsyncCallback<CallbackResult> c = this.callback;
        if (c != null) {
            this.callback = null;
            c.error(t);
        }
    }

    private void completeAndDelete(CallbackResult res) {
        AsyncCallback<CallbackResult> c = this.callback;
        if (c != null) {
            this.callback = null;
            c.complete(res);
        }
    }

    @Override
    public int available() throws IOException {
        int i = curr != null ? curr.getLength() - index : 0;
        for (int j = 0; j < buffers.size(); j++) {
            i += buffers.get(i).getLength();
        }
        return i;
    }

    @Override
    public int read() throws IOException {
        if (closed) {
            return -1;
        }
        if (curr != null && index < curr.getLength()) {
            return curr.get(index++);
        }
        if (!buffers.isEmpty()) {
            curr = buffers.remove(0);
            index = 0;
            return read(); // re try the read with the new buffer
        }
        CallbackResult result = awaitBuffer();
        if (result == CallbackResult.READ) {
            return read(); // new buffer received, re-try again
        }
        //TODO throw error if result == ERROR
        return -1; // socket closed
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (closed) {
            return -1;
        }
        if (curr != null && index < curr.getLength()) {
            int count = Math.min(curr.getLength() - index, len);
            for (int i = 0; i < count; i++) {
                b[off + i] = (byte) curr.get(index++);
            }
            return count;
        }
        if (!buffers.isEmpty()) {
            curr = buffers.remove(0);
            index = 0;
            return read(b, off, len); // re try the read with the new buffer
        }
        CallbackResult result = awaitBuffer();
        if (result == CallbackResult.READ) {
            return read(b, off, len); // new buffer received, re-try again
        }
        //TODO throw error if result == ERROR
        return -1; // socket closed
    }

    @Override
    public void close() throws IOException {
        completeAndDelete(CallbackResult.CLOSED);
        closed = true;
        super.close();
    }

    @Async
    private native CallbackResult awaitBuffer() throws IOException;

    private void awaitBuffer(AsyncCallback<CallbackResult> callback) {
        this.callback = callback;
    }

    private enum CallbackResult {
        READ, CLOSED
    }
}
