package Interface;

import Display.Display;

import java.util.Collections;

public class Border extends View{

    String style;

    public Border(String style, int top, int left, int bottom , int right) {
        super(top - 1, left - 1 , bottom + 1, right +1);
        this.style = style;
    }

    @Override
    public void Draw() {

        Display.write(String.join("", Collections.nCopies(getWidth(), style)), top, left);

        Display.write(String.join("", Collections.nCopies(getWidth(), style)), bottom - 1, left);
        for (int i = 0; i < getHeight(); i++) {
            Display.write("#", top + i, left);
            Display.write("#", top + i, right-1);
        }
    }
}
