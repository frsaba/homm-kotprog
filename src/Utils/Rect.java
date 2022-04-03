package Utils;

/**
 * Téglalap osztály. A koordinátákat a bal felső saroktól számolja.
 */
public class Rect {

    public int top;
    public int left;
    public int bottom;
    public int right;

    public int getHeight() {
        return bottom - top + 1;
    }

    public int getWidth() {
        return right - left + 1;
    }

    public Rect(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public Rect(Rect rect) {
        this.top = rect.top;
        this.left = rect.left;
        this.bottom = rect.bottom;
        this.right = rect.right;
    }
}
