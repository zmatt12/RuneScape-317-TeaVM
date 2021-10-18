package ui;

public interface IFont {

    int PLAIN = 0;
    int BOLD = 1;
    int ITALIC = 2;

    static IFont get(String name, int style, int size){
        return WindowEngine.getDefault().getFont(name, style, size);
    }
}
