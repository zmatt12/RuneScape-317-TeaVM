package ui.poly;

public final class Color {

    public static final Color black = new Color(0, 0, 0);
    public static final Color white = new Color(255, 255, 255);
    public static Color yellow = new Color(255, 255, 0);

    private final int red, green, blue, alpha;

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(int r, int g, int b, int a) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public int toRGBA() {
        return (red << 24) | (blue << 16) | (green << 8) | alpha;
    }

    public String toHex() {
        int rgba = toRGBA();
        return String.format("#%08X", rgba);
    }
}
