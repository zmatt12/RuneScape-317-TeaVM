package web.impl.webassembly;

import web.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WASocket implements ISocket {

    private final int fd;

    public WASocket(int fd){
        this.fd = fd;
    }
    @Override
    public void setSoTimeout(int timeout) throws IOException {

    }

    @Override
    public void setTcpNoDelay(boolean nodelay) throws IOException {

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
