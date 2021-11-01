package web.impl.jvm.event;

import web.event.MouseEvent;

import javax.swing.*;

public class AWTMouseEvent extends MouseEvent {

    private final java.awt.event.MouseEvent event;

    public AWTMouseEvent(java.awt.event.MouseEvent event) {
        super(event.getX(), event.getY(), event.getButton(), event.getClickCount());
        this.event = event;
    }

    @Override
    public int getEventType() {
        return event.getID();
    }

    @Override
    public boolean isRightMouseButton() {
        return SwingUtilities.isRightMouseButton(event);
    }

    @Override
    public boolean isPopupTrigger() {
        return event.isPopupTrigger();
    }
}
