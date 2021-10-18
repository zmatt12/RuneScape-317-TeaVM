package ui.tea;


import org.teavm.classlib.fs.VirtualFileSystemProvider;
import ui.tea.fs.VirtualIndexedFileSystem;

import java.io.File;
import java.io.RandomAccessFile;

public class Entry {

    //Don't actually try to run this, you'd be in for a bad time.
    public static void main(String[] args) throws Exception {
//        if(IDBFactory.isSupported()) {
//            IDBFactory factory = IDBFactory.getInstance();
//            IDBDatabase db= factory.open(args[0], 1).getResult();
//                    VirtualFileSystemProvider.setInstance(new VirtualIndexedFileSystem(db));
//        }else{
//            System.err.println("IDB not supported, you'll be downloading the whole game every start!");
//        }
        if (args.length > 1) {
            TeaEngine.init(args[0], Integer.parseInt(args[1]));
        } else {
            TeaEngine.init(args[0]);
        }
        //We need to do this due to a bug in TeaVM, where randomaccess files aren't created when opened
        File cacheDir = new File("/tmp/.file_store_32");
        createCache(cacheDir);

        client.Game.main(new String[]{"0", "0", "highmem", "free", "0"});
    }

    private static void createCache(File cacheDir) throws Exception {
        if(!cacheDir.mkdirs()){
            throw new Exception("Unable to create cache root");
        }
        File cache_dat = new File(cacheDir, "main_file_cache.dat");
        if(!cache_dat.createNewFile()){
            throw new Exception("Unable to make main_file_cache");
        }
        for (int j = 0; j < 5; j++) {
            File idx = new File(cacheDir, "main_file_cache.idx" + j);
            if(!idx.createNewFile()){
                throw new Exception("Unable to make index:" + idx);
            }
        }
    }
}
