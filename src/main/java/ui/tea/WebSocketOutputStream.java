package ui.tea;

import org.teavm.jso.typedarrays.Uint8Array;
import org.teavm.jso.websocket.WebSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class WebSocketOutputStream extends OutputStream {

    private final WebSocket socket;
    public WebSocketOutputStream(WebSocket socket){
        this.socket = socket;
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b});
    }

    @Override
    public void write(byte[] b) throws IOException {
        Uint8Array arr = Uint8Array.create(b.length);
        arr.set(b);
        JSMethods.send(socket, arr);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        write(Arrays.copyOfRange(b, off, len));
    }
}
