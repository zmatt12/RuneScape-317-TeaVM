package web;

import web.event.Event;
import web.event.EventListener;
import web.util.Dimension;

public interface IComponent {

    IGraphics getGraphics();

    void setSize(int width, int height);

    Dimension getSize();

    void setPreferredSize(Dimension dimensions);

    void requestFocus();

    void update(IGraphics g);

    void paint(IGraphics g);

    void repaint();

    void dispatch(Event event);

    IFontMetrics getFontMetrics(IFont font);

    void addListener(EventListener listener);
}
