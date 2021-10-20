package ui.tea;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.websocket.WebSocket;
import ui.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class TeaSocket implements ISocket {
    private final WebSocket socket;
    private final OutputStream out;
    private final InputStream in;

    public TeaSocket(WebSocket socket) {
        this.socket = socket;
        this.out = new WebSocketOutputStream(socket);
        this.in = new WebSocketInputStream(socket);
    }

    public static TeaSocket open(String server, int port) {
        TeaSocket socket = connect(server, port);
        socket.socket.onClose(e -> {
            try {
                socket.close(false);
            } catch (IOException ignored) {
            }
        });
        socket.socket.onError(event -> {
            try {
                socket.close(false);
            } catch (IOException ignored) {
            }
        });
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
        out.close();
        in.close();
        if (closeUnderlying) {
            socket.close();
        }
    }
}
