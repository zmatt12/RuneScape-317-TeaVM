package web.impl.webassembly;

import web.*;
import web.impl.webassembly.modules.ComponentModule;
import web.util.Color;
import web.util.Dimension;

public class WAComponent extends AbstractComponent {

    private final int id;
    private final IGraphics graphics;
    private final Dimension dimension;

    public WAComponent(){
        id = ComponentModule.createComponent();
        //TODO check if id == -1, if so throw exception or something
        dimension = new Dimension(getWidth(), getHeight());
        graphics = new GraphicsImpl();
    }

    private int getHeight() {
        return ComponentModule.getHeight(id);
    }

    private int getWidth() {
        return ComponentModule.getWidth(id);
    }

    @Override
    public IGraphics getGraphics() {
        return graphics;
    }

    @Override
    public void setSize(int width, int height) {
        ComponentModule.setSize(id, width, height);
        dimension.setSize(width, height);
    }

    @Override
    public Dimension getSize() {
        return dimension;
    }

    @Override
    public void setPreferredSize(Dimension dimensions) {
        setSize(dimensions.width, dimensions.height);
    }

    @Override
    public void requestFocus() {
        ComponentModule.requestFocus(id);
    }

    @Override
    public void update(IGraphics g) {

    }

    @Override
    public void paint(IGraphics g) {

    }

    @Override
    public void repaint() {

    }

    @Override
    public IFontMetrics getFontMetrics(IFont font) {
        return null;
    }

    private class GraphicsImpl implements IGraphics{

        @Override
        public void setColor(Color color) {
            ComponentModule.setColor(id, color.toHex());
        }

        @Override
        public void fillRect(int x, int y, int width, int height) {
            ComponentModule.filLRect(id, x, y, width, height);
        }

        @Override
        public void drawRect(int x, int y, int width, int height) {
            ComponentModule.drawRect(id, x, y, width, height);
        }

        @Override
        public void setFont(IFont font) {
            ImmutableFont f = (ImmutableFont) font;
            ComponentModule.setFont(id, f.toHtml());
        }

        @Override
        public void drawString(String str, int x, int y) {
            ComponentModule.drawString(id, str, x, y);
        }

        @Override
        public void drawImage(IImage img, int x, int y, Object observer) {
            ComponentModule.drawPixels(id, x, y, img.getWidth(), img.getHeight(), img.getBufferAsIntegers());
        }
    }
}
