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

    private enum CallbackResult{
        READ, ERROR, CLOSED;
    }

    public WebSocketInputStream(WebSocket socket) {
        this.socket = socket;
        socket.onMessage(evt -> {
            Uint8ClampedArray arr = Uint8ClampedArray.create(evt.getDataAsArray());
            System.out.println("Received Data:" + arr.getLength());
            buffers.add(arr);
            fireAndDelete(CallbackResult.READ);
        });
        socket.onError( evt ->{
            fireAndDelete(CallbackResult.ERROR);
        });
    }

    private void fireAndDelete(CallbackResult i) {
        AsyncCallback<CallbackResult> c = this.callback;
        if(c != null){
            this.callback = null;
            c.complete(i);
        }
    }

    @Override
    public int read() throws IOException {
        if(closed){
            return -1;
        }
        if(curr != null && index < curr.getLength()) {
            return curr.get(index++);
        }
        if(!buffers.isEmpty()) {
            curr = buffers.remove(0);
            index = 0;
            return read(); // re try the read with the new buffer
        }
        CallbackResult result = awaitBuffer();
        if(result == CallbackResult.READ) {
            return read(); // new buffer recived, re-try again
        }
        //TODO throw error if result == ERROR
        return -1; // socket closed
    }

    @Override
    public void close() throws IOException {
        fireAndDelete(CallbackResult.CLOSED);
        closed = true;
        super.close();
    }

    @Async
    private native CallbackResult awaitBuffer();
    private void awaitBuffer(AsyncCallback<CallbackResult> callback) {
        this.callback = callback;
    }
}
