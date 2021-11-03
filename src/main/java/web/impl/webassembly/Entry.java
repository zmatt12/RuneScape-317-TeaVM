package web.impl.webassembly;

import client.Game;
import client.Signlink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.Platform;


public class Entry {

    public static void main(String[] args){
        Platform.setDefaultPlatform(new WebAssemblyPlatform());
        Signlink.storeid = 32;
        String server = "localhost";

        Signlink.startpriv(server);
        Game.server = server;


        Game g = new Game();
        g.init(765, 503);
    }
}
