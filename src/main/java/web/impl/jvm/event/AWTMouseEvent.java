package web.impl.jvm.event;

import web.event.MouseEvent;

import javax.swing.*;

public class AWTMouseEvent extends MouseEvent<Object> {

    private final java.awt.event.MouseEvent event;
    private final int wheelRotation;

    public AWTMouseEvent(java.awt.event.MouseWheelEvent event){
        super(event.getSource());
        this.event = event;
        this.wheelRotation = event.getWheelRotation();
    }

    public AWTMouseEvent(java.awt.event.MouseEvent event) {
        super(event.getSource());
        this.event = event;
        this.wheelRotation = -1;
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
    public int getX() {
        return event.getX();
    }

    @Override
    public int getY() {
        return event.getY();
    }

    @Override
    public boolean isPopupTrigger() {
        return event.isPopupTrigger();
    }

    @Override
    public int getButton() {
        return event.getButton();
    }

    @Override
    public int getClickCount() {
        return event.getClickCount();
    }

    @Override
    public int getWheelRotation() {
        return wheelRotation;
    }
}
