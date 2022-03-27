package Interface;

import Display.Display;

import java.util.Collections;

public class Border implements Drawable{

    String style;
    int width;
    int height;


    public Border(String style, int width, int height) {
        this.width = width + 2;
        this.height = height + 2;
        this.style = style;
    }
    public void draw(int top, int left) {
        top = top - 1; //körbe akarjuk rajzolni, szóval eggyel bentebbről kezdünk
        left = left - 1;

        Display.write(String.join("", Collections.nCopies(width, style)), top, left);

        Display.write(String.join("", Collections.nCopies(width, style)), top + height - 1, left);
        for (int i = 0; i < height - 1; i++) {

                Display.write(style, top + i, left);

            Display.write(style, top + i, left + width - 1);
        }
    }
}
