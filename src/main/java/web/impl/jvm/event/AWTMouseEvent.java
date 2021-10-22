package web.impl.jvm.event;

import web.event.MouseEvent;

import javax.swing.*;

public class AWTMouseEvent extends MouseEvent {

    private final java.awt.event.MouseEvent event;

    public AWTMouseEvent(java.awt.event.MouseEvent event) {
        super(event.getX(), event.getY());
        this.event = event;
    }

    @Override
    public int getEventType() {
        switch (event.getID()) {
            case java.awt.event.MouseEvent.MOUSE_CLICKED:
                return TYPE_CLICKED;
            case java.awt.event.MouseEvent.MOUSE_DRAGGED:
                return TYPE_DRAGGED;
            case java.awt.event.MouseEvent.MOUSE_ENTERED:
                return TYPE_ENTERED;
            case java.awt.event.MouseEvent.MOUSE_EXITED:
                return TYPE_EXITED;
            case java.awt.event.MouseEvent.MOUSE_MOVED:
                return TYPE_MOVED;
            case java.awt.event.MouseEvent.MOUSE_PRESSED:
                return TYPE_PRESSED;
            case java.awt.event.MouseEvent.MOUSE_RELEASED:
                return TYPE_RELEASED;
            case java.awt.event.MouseEvent.MOUSE_WHEEL:
                return TYPE_WHEEL;
        }
        return -1;
    }

    @Override
    public boolean isRightMouseButton() {
        return SwingUtilities.isRightMouseButton(event);
    }
}
