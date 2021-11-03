package web.impl.js;


import client.Game;
import client.Signlink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teavm.classlib.fs.VirtualFileSystemProvider;
import org.teavm.jso.browser.Window;
import web.Platform;
import web.impl.js.fs.GenericVirtualFileSystem;
import web.impl.js.fs.generic.GenericFileSystem;

import java.io.File;

public class Entry {

    private static final Logger logger = LoggerFactory.getLogger(Entry.class);

    //Don't actually try to run this, you'd be in for a bad time.
    public static void main(String[] args) throws Exception {
        Platform.setDefaultPlatform(new JSPlatform());
        if(!JSConfig.exists()){
            Window.alert("No webclient config exists! Please create it!");
            return;
        }
        JSConfig config = JSConfig.get();
        if(GenericFileSystem.isSupported()){
            logger.info("Using generic filesystem driver");
            VirtualFileSystemProvider.setInstance(new GenericVirtualFileSystem());
        }

        if (config.hasFile()) {
            JSConfig.FileViewerConfig file = config.file();
            FileSystemViewer viewer = new FileSystemViewer(file.getTableId(), file.getNumberId());
            viewer.setCurrentId(file.getCurrentId());
        }

        JSPlatform.init(config.getCanvasId(), config.hasPortOffset() ? config.getPortOffset() : 0);

        //We need to do this due to a bug in TeaVM, where randomaccess files aren't created when opened
        File cacheDir = new File("/tmp/.file_store_32");
        createCache(cacheDir);

        Signlink.storeid = 32;

        String server;
        if(config.hasServer()){
            server = config.getServer();
        }else{
            server = Window.current().getLocation().getHostName();
        }

        logger.info("Setting server to '{}'", server);
        Signlink.startpriv(server);
        Game.server = server;

        logger.info("Codebase:{}", Platform.getDefault().getCodeBase());

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
