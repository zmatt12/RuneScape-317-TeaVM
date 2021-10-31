package web.util;

public final class Color {

    public static final Color black = new Color(0, 0, 0);
    public static final Color white = new Color(255, 255, 255);
    public static Color yellow = new Color(255, 255, 0);

    private final float red, green, blue, alpha;

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(int r, int g, int b, int a) {
        this.red = r / 255f;
        this.green = g / 255f;
        this.blue = b / 255f;
        this.alpha = a / 255f;
    }

    public Color(int rgb){
        this.red = ((rgb >> 16) & 0xFF ) / 255f;
        this.green = ((rgb >> 8) & 0xFF) / 255f;
        this.blue = (rgb & 0xFF ) / 255f;
        this.alpha = 1.0f;
    }

    public int getRed() {
        return (int) (red * 255f + 0.5f);
    }

    public int getGreen() {
        return (int) (green * 255f + 0.5f);
    }

    public int getBlue() {
        return (int) (blue * 255f + 0.5f);
    }

    public int getAlpha() {
        return (int) (alpha * 255f + 0.5f);
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
