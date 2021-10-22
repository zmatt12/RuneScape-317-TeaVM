package web;

public interface IFont {

    int PLAIN = 0;
    int BOLD = 1;
    int ITALIC = 2;

    static IFont get(String name, int style, int size) {
        return Platform.getDefault().getFont(name, style, size);
    }
}
