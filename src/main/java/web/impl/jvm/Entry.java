package web.impl.jvm;

import client.Game;
import client.Signlink;
import web.Platform;
import web.impl.jvm.impl.JVMComponent;
import web.impl.jvm.impl.JVMPlatform;

import javax.swing.*;
import java.awt.*;

public class Entry {

    public static void main(String[] args) {
        Platform.setDefaultPlatform(new JVMPlatform());
        JFrame frame = new JFrame("Lol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Signlink.storeid = 32;
        Signlink.startpriv("127.0.0.1");

        Game g = new Game();

        int width = 765;
        int height = 503;

        frame.add(((JVMComponent) g.getComponent()).getRealComponent());
        frame.setMinimumSize(new Dimension(width, height));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        g.init(width, height);
    }
}
