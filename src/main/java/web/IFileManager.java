package web;

public interface IFileManager {

    void init(String path);

    boolean canLoad();

    IFileStore openOrCreateStore(int maxSize, int index);
}
