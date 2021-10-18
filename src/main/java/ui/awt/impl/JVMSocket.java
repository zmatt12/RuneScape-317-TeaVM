package ui.awt.impl;

import ui.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

final class JVMSocket implements ISocket {

    private Socket socket;

    JVMSocket(Socket socket){
        this.socket = socket;
    }

    @Override
    public void setSoTimeout(int timeout) throws IOException{
        this.socket.setSoTimeout(timeout);
    }

    @Override
    public void setTcpNoDelay(boolean nodelay) throws IOException {
        this.socket.setTcpNoDelay(nodelay);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
    }
}
