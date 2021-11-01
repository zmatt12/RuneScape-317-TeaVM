package web.impl.jvm.event;

import web.event.FocusEvent;

public class AWTFocusEvent extends FocusEvent {

    private final java.awt.event.FocusEvent event;

    public AWTFocusEvent(java.awt.event.FocusEvent event) {
        this.event = event;
    }

    @Override
    public int getEventType() {
        return event.getID();
    }
}
