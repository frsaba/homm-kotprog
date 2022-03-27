package Interface;

import java.util.ArrayList;
import java.util.List;

public class View implements Drawable{

    int top;
    int left;
    int bottom;
    int right;

    protected List<Drawable> children;

    public void addChild(Drawable child){
        children.add(child);
    }

    public void draw(int top, int left){
        for (Drawable child : children){
            child.draw(this.top + top, this.left + left);
        }
    }

    public void draw(){
        draw(0,0);
    }

    int getHeight(){
        return bottom - top + 1;
    }

    int getWidth(){
        return right - left + 1;
    }

    public View(int top, int left, int bottom, int right, boolean hasBorder) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;

        children = new ArrayList<>();

        if(hasBorder)
            addChild(new Border("#", getWidth(), getHeight()));
    }

    public View(int top, int left, int bottom, int right) {
        this(top,left,bottom,right, true);
    }
}
