package ui.awt;

import client.Game;
import ui.awt.impl.AWTComponent;

import javax.swing.*;

public class Entry {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game g = new Game();
        g.init(765, 503);
        frame.add(((AWTComponent) g.getComponent()).getAwtComponent());

        frame.pack();
        frame.setVisible(true);
    }
}
