package ui;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISocket extends Closeable {
    void setSoTimeout(int timeout) throws IOException;

    void setTcpNoDelay(boolean nodelay) throws IOException;

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;
}
