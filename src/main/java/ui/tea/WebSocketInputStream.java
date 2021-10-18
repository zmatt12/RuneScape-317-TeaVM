package ui.tea;

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

    public WebSocketInputStream(WebSocket socket) {
        this.socket = socket;
        socket.onMessage(evt -> {
            Uint8ClampedArray arr = Uint8ClampedArray.create(evt.getDataAsArray());
            System.out.println("Received Data:" + arr.getLength());
            buffers.add(arr);
        });
    }

    @Override
    public int read() throws IOException {
        while ((curr == null || index == curr.getLength()) && buffers.isEmpty() && socket.getReadyState() < 3) {
            Thread.yield();
        }
        if ((curr == null || index == curr.getLength())) {
            if (buffers.isEmpty()) {
                return -1;
            }
            curr = buffers.remove(0);
            index = 0;
        }
        return curr.get(index++) & 0xFF;
    }
}
