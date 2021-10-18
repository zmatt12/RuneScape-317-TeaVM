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

    public TeaSocket(WebSocket socket){
        this.socket = socket;
        this.out = new WebSocketOutputStream(socket);
        this.in = new WebSocketInputStream(socket);
    }

    @Override
    public void setSoTimeout(int timeout) throws IOException {
        System.out.println("setSoTimeout(" + timeout + ")");
    }

    @Override
    public void setTcpNoDelay(boolean nodelay) throws IOException {
        System.out.println("setTcpNoDelay(" + nodelay + ")");
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
        out.close();
        in.close();
        socket.close();
        System.out.println("close()");
    }

    public static TeaSocket open(String server, int port){
        port -= 10000; // TODO offset for websocket, have it passed in via main
        System.out.println("TeaSocket.open(" + server + "," + port + ")");
        TeaSocket socket = connect(server, port);
        return socket;
    }

    @Async
    private static native TeaSocket connect(String server, int port);
    private static void connect(String server, int port, AsyncCallback<TeaSocket>callback){
        WebSocket ws = WebSocket.create("ws://" + server + ":" + port, "binary");
        ws.setBinaryType("arraybuffer");
        TeaSocket s = new TeaSocket(ws);
        ws.onOpen( e -> callback.complete(s));
        ws.onError(e -> callback.error(new IOException("Unable to open socket")));
        ws.onClose(e ->{
            try {
                s.close();
            } catch (IOException ignored) {
            }
        });
    }
}
