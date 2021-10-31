package web.util;

public final class Color {

    public static final Color black = new Color(0, 0, 0);
    public static final Color white = new Color(255, 255, 255);
    public static Color yellow = new Color(255, 255, 0);

    private final float colorspace;

    private final float red, green, blue, alpha;

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(int r, int g, int b, int a) {
        this.colorspace = 255;
        this.red = r / colorspace;
        this.green = g / colorspace;
        this.blue = b / colorspace;
        this.alpha = a / colorspace;
    }

    public Color(int rgb){
        this.colorspace = 127;
        this.red = ((rgb >> 16) & 0x7F ) / colorspace;
        this.green = ((rgb >> 8) & 0x7F) / colorspace;
        this.blue = (rgb & 0x7F ) / colorspace;
        this.alpha = 1.0f;
    }

    public int getRed() {
        return (int) (red * colorspace);
    }

    public int getGreen() {
        return (int) (green * colorspace);
    }

    public int getBlue() {
        return (int) (blue * colorspace);
    }

    public int getAlpha() {
        return (int) (alpha * colorspace);
    }

    public int toRGBA() {
        return (getRed() << 24) | (getBlue() << 16) | (getGreen() << 8) | getAlpha();
    }

    public String toHex() {
        int rgba = toRGBA();
        return String.format("#%08X", rgba);
    }

    public float[] asFloatArray() {
        return new float[]{red, green, blue, alpha};
    }
}
