package web.impl.jvm.impl;

import web.AbstractComponent;
import web.IFont;
import web.IFontMetrics;
import web.IGraphics;

import java.awt.*;

public class JVMComponent extends AbstractComponent {

    private final Component component;
    private IGraphics graphics;

    JVMComponent(Component c) {
        this.component = c;
    }

    public Component getRealComponent() {
        return component;
    }

    @Override
    public IGraphics getGraphics() {
        if (this.graphics == null) {
            Graphics g = component.getGraphics();
            if (g == null) {
                return null;
            }
            this.graphics = new JVMGraphics(g);
        }
        return graphics;
    }

    @Override
    public void setSize(int width, int height) {
        component.setSize(width, height);
    }

    @Override
    public web.util.Dimension getSize() {
        return Utils.fromJvm(component.getSize());
    }

    @Override
    public void setPreferredSize(web.util.Dimension dimensions) {
        component.setPreferredSize(Utils.toJvm(dimensions));
    }

    @Override
    public void requestFocus() {
        component.requestFocus();
    }

    @Override
    public void update(IGraphics g) {
        component.update(((JVMGraphics) g).getGraphics());
    }

    @Override
    public void paint(IGraphics g) {
        component.paint(((JVMGraphics) g).getGraphics());
    }

    @Override
    public void repaint() {
        component.repaint();
    }

    @Override
    public IFontMetrics getFontMetrics(IFont font) {
        return new JVMFontMetrics(component.getFontMetrics(((JVMFont) font).getFont()));
    }
}
