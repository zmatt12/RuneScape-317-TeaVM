package web.impl.jvm;

import client.Game;
import client.Signlink;
import web.impl.jvm.impl.JVMComponent;

import javax.swing.*;

public class Entry {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Signlink.storeid = 32;
        Signlink.startpriv("127.0.0.1");

        Game g = new Game();
        g.init(765, 503);
        frame.add(((JVMComponent) g.getComponent()).getRealComponent());

        frame.pack();
        frame.setVisible(true);
    }
}
