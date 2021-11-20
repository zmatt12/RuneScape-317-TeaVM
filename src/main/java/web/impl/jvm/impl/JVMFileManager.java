package web.impl.jvm.impl;

import client.Signlink;
import client.files.FileStore;
import web.IFileManager;
import web.IFileStore;

import java.io.RandomAccessFile;

final class JVMFileManager implements IFileManager {
    public static RandomAccessFile cache_dat = null;
    public static final RandomAccessFile[] cache_idx = new RandomAccessFile[5];

    @Override
    public void init(String path) {
        try {
            cache_dat = new RandomAccessFile(path + "main_file_cache.dat", "rw");
            for (int j = 0; j < 5; j++) {
                cache_idx[j] = new RandomAccessFile(path + "main_file_cache.idx" + j, "rw");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean canLoad() {
        return cache_dat != null;
    }

    @Override
    public IFileStore openOrCreateStore(int maxSize, int index) {
        return new FileStore(500000, cache_dat, cache_idx[index], index + 1);
    }
}
