package ui.tea;


import client.Game;
import client.Signlink;
import org.teavm.classlib.fs.VirtualFileSystemProvider;
import ui.poly.InputStreamPolyFill;
import ui.tea.fs.BrowserFsFileSystem;
import ui.tea.fs.bfs.BrowserFileSystem;

import java.io.File;

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
            FileSystemViewer viewer = new FileSystemViewer(args[1], args[2]);
            viewer.setCurrentId(args[3]);
            viewer.refresh();
            TeaEngine.init(args[0], Integer.parseInt(args[4]));
        } else {
            TeaEngine.init(args[0]);
        }
        //We need to do this due to a bug in TeaVM, where randomaccess files aren't created when opened
        File cacheDir = new File("/tmp/.file_store_32");
        createCache(cacheDir);

        Signlink.storeid = 32;
        Signlink.startpriv("127.0.0.1");

        Game g = new Game();
        g.init(765, 503);
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
