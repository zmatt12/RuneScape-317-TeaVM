package ui.tea;


import org.teavm.classlib.fs.VirtualFileSystemProvider;
import ui.poly.InputStreamPolyFill;
import ui.tea.fs.BrowserFsFileSystem;
import ui.tea.fs.bfs.BrowserFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Entry {

    //Don't actually try to run this, you'd be in for a bad time.
    public static void main(String[] args) throws Exception {
        if(BrowserFileSystem.isSupported()){
            System.out.println("Using browser fs!");
            VirtualFileSystemProvider.setInstance(new BrowserFsFileSystem());
        }else{
            System.out.println("Using teavm fs, no data will be saved");
        }
        if (args.length > 1) {
            TeaEngine.init(args[0], Integer.parseInt(args[1]));
        } else {
            TeaEngine.init(args[0]);
        }
        //We need to do this due to a bug in TeaVM, where randomaccess files aren't created when opened
        File cacheDir = new File("/tmp/.file_store_32");
        createCache(cacheDir);
        for(File s : cacheDir.listFiles()){
            System.out.println(s.getName() + " - " + s.length());
        }
        client.Game.main(new String[]{"0", "0", "highmem", "free", "0"});
    }

    private static void createCache(File cacheDir) throws Exception {
        if(!cacheDir.exists()) {
            if (!cacheDir.mkdirs()) {
                throw new Exception("Unable to create cache root");
            }
        }
        File cache_dat = new File(cacheDir, "main_file_cache.dat");
        if(cache_dat.exists()){
            return;
        }
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
