package web;


import web.util.Dimension;

import java.net.MalformedURLException;
import java.net.URL;

public class Window<T extends IComponent> {

    private final T component = (T) Platform.getDefault().createComponent();

    public final T getComponent() {
        return component;
    }

    public IGraphics getGraphics() {
        return component.getGraphics();
    }

    public void setSize(int width, int height) {
        component.setSize(width, height);
    }

    public Dimension getSize() {
        return component.getSize();
    }

    public void setPreferredSize(Dimension dimensions) {
        component.setPreferredSize(dimensions);
    }

    public void requestFocus() {
        component.requestFocus();
    }

    public void update(IGraphics g) {
        component.update(g);
    }

    public void paint(IGraphics g) {
        component.paint(g);
    }

    public void repaint() {
        component.repaint();
    }

    public final IFontMetrics getFontMetrics(IFont font) {
        return component.getFontMetrics(font);
    }

    public final URL getCodeBase() throws MalformedURLException {
        return new URL(Platform.getDefault().getCodeBase());
    }
}
