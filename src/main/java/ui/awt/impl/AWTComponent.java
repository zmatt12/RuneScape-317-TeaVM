package ui.awt.impl;

import ui.AbstractComponent;
import ui.IFont;
import ui.IFontMetrics;
import ui.IGraphics;

import java.awt.*;

public class AWTComponent extends AbstractComponent {

    private final Component component;
    private IGraphics graphics;

    AWTComponent(Component c) {
        this.component = c;
    }

    public Component getAwtComponent() {
        return component;
    }

    @Override
    public IGraphics getGraphics() {
        if (this.graphics == null) {
            Graphics g = component.getGraphics();
            if (g == null) {
                return null;
            }
            this.graphics = new AWTGraphics(g);
        }
        return graphics;
    }

    @Override
    public void setSize(int width, int height) {
        component.setSize(width, height);
    }

    @Override
    public ui.util.Dimension getSize() {
        return AWTUtils.fromAwt(component.getSize());
    }

    @Override
    public void setPreferredSize(ui.util.Dimension dimensions) {
        component.setPreferredSize(AWTUtils.toAwt(dimensions));
    }

    @Override
    public void requestFocus() {
        component.requestFocus();
    }

    @Override
    public void update(IGraphics g) {
        component.update(((AWTGraphics) g).getGraphics());
    }

    @Override
    public void paint(IGraphics g) {
        component.paint(((AWTGraphics) g).getGraphics());
    }

    @Override
    public void repaint() {
        component.repaint();
    }

    @Override
    public IFontMetrics getFontMetrics(IFont font) {
        return new AWTFontMetrics(component.getFontMetrics(((AWTFont) font).getFont()));
    }
}
