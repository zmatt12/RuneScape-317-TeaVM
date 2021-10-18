package ui;

import ui.event.Event;
import ui.event.EventListener;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractComponent implements IComponent{

    private List<EventListener> listeners = new LinkedList<>();

    public void dispatch(Event e){
        listeners.forEach(listener -> {
            if(!e.consumed()) {
                listener.onEvent(e);
            }
        });
    }

    public void addListener(EventListener eventListener){
        this.listeners.add(eventListener);
    }
}
