package ui.awt.event;

import ui.event.FocusEvent;

public class AWTFocusEvent extends FocusEvent {

    private final java.awt.event.FocusEvent event;

    public AWTFocusEvent(java.awt.event.FocusEvent event) {
        this.event =event;
    }

    @Override
    public int getEventType() {
        switch(event.getID()){
            case java.awt.event.FocusEvent.FOCUS_LOST:
                return TYPE_LOST;
            case java.awt.event.FocusEvent.FOCUS_GAINED:
                return TYPE_GAINED;
        }
        return -1;
    }
}
