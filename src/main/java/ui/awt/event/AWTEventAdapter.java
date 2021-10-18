package ui.awt.event;

import ui.IComponent;

import java.awt.event.*;

public class AWTEventAdapter implements FocusListener, KeyListener, MouseMotionListener, MouseListener {

    private final IComponent target;
    public AWTEventAdapter(IComponent target) {
        this.target = target;
    }

    @Override
    public void focusGained(FocusEvent e) {
        target.dispatch(new AWTFocusEvent(e));
    }

    @Override
    public void focusLost(FocusEvent e) {
        target.dispatch(new AWTFocusEvent(e));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        target.dispatch(new AWTKeyEvent(e));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        target.dispatch(new AWTKeyEvent(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        target.dispatch(new AWTKeyEvent(e));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        target.dispatch(new AWTMouseEvent(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        target.dispatch(new AWTMouseEvent(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        target.dispatch(new AWTMouseEvent(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        target.dispatch(new AWTMouseEvent(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        target.dispatch(new AWTMouseEvent(e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        target.dispatch(new AWTMouseEvent(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        target.dispatch(new AWTMouseEvent(e));
    }
}
