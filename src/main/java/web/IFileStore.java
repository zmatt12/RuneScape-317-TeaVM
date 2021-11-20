package web;

public interface IFileStore {
    byte[] read(int fileId);

    void write(byte[] data, int fileId, int length);
}
