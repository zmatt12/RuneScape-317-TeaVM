package ui.tea;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.typedarrays.Uint8Array;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import org.teavm.jso.websocket.WebSocket;
import ui.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public final class TeaSocket implements ISocket {
    private final WebSocket socket;
    private final SocketOutputStream out;
    private final SocketInputStream in;

    public TeaSocket(WebSocket socket) {
        this.socket = socket;
        this.out = new SocketOutputStream();
        this.in = new SocketInputStream();
    }

    private void init(){
        socket.onClose(e ->{
            try {
                close(false);
            } catch (IOException ignored) {
            }
        });
        socket.onMessage(evt -> {
            Uint8ClampedArray arr = Uint8ClampedArray.create(evt.getDataAsArray());
            in.buffers.add(arr);
            in.completeAndDelete(CallbackResult.READ);
        });
        socket.onError(event -> {
            try {
                close(false);
            } catch (IOException ignored) {
            }
        });
    }

    public static TeaSocket open(String server, int port) {
        TeaSocket socket = connect(server, port);
        socket.init();
        return socket;
    }

    @Async
    private static native TeaSocket connect(String server, int port);

    private static void connect(String server, int port, AsyncCallback<TeaSocket> callback) {
        WebSocket ws = WebSocket.create("ws://" + server + ":" + port, "binary");
        ws.setBinaryType("arraybuffer");
        TeaSocket s = new TeaSocket(ws);
        ws.onOpen(e -> callback.complete(s));
        ws.onError(e -> callback.error(new IOException("Unable to open socket")));
    }

    @Override
    public void setSoTimeout(int timeout) throws IOException {

    }

    @Override
    public void setTcpNoDelay(boolean nodelay) throws IOException {

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return in;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return out;
    }

    @Override
    public void close() throws IOException {
        close(true);
    }

    public void close(boolean closeUnderlying) throws IOException {
        in.completeAndDelete(CallbackResult.CLOSED);
        if (closeUnderlying) {
            socket.close();
        }
    }

    class SocketInputStream extends InputStream{
        private final List<Uint8ClampedArray> buffers = new LinkedList<>();
        private Uint8ClampedArray curr = null;
        private int index = 0;
        AsyncCallback<CallbackResult> callback = null;

        public int available() throws IOException {
            if(socket.getReadyState() > 1){
                return 0;
            }
            int i = curr != null ? curr.getLength() - index : 0;
            for (int j = 0; j < buffers.size(); j++) {
                i += buffers.get(j).getLength();
            }
            return i;
        }

        @Override
        public int read() throws IOException {
            if(socket.getReadyState() > 1){
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
            return -1; // socket closed
        }

        @Async
        private native CallbackResult awaitBuffer() throws IOException;

        private void awaitBuffer(AsyncCallback<CallbackResult> callback) {
            this.callback = callback;
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
    }

    class SocketOutputStream extends OutputStream{

        @Override
        public void write(int b) throws IOException {
            if(socket.getReadyState() > 1){
                throw new IOException("Closed socket");
            }
            Uint8Array arr = Uint8Array.create(1);
            arr.set(0, (short)b);
            JSMethods.send(socket, arr);
        }
    }

    private enum CallbackResult {
        READ, CLOSED
    }
}
