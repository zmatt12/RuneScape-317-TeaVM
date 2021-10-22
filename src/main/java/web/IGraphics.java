package web;


import web.util.Color;

public interface IGraphics {
    void setColor(Color color);

    void fillRect(int x, int y, int width, int height);

    void drawRect(int x, int y, int width, int height);

    void setFont(IFont font);

    void drawString(String str, int x, int y);

    void drawImage(IImage img, int width, int height, Object observer);
}
