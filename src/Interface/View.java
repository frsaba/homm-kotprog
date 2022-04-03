package Interface;

import Utils.Rect;

import java.util.ArrayList;
import java.util.List;

public class View extends Rect implements Drawable{

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

    public View(int top, int left, int bottom, int right, boolean hasBorder) {
        super(top, left, bottom, right);

        children = new ArrayList<>();

//        if(hasBorder)
//            addChild(new Border("#", getWidth(), getHeight()));
    }

    public View(Rect rect, boolean hasBorder) {
        super(rect);
        children = new ArrayList<>();

//        if(hasBorder)
            //addChild(new Border("#", getWidth(), getHeight()));
    }

    public View(Rect rect) {
        this(rect, true);

    }

    public View(int top, int left, int bottom, int right) {
        this(top,left,bottom,right, true);
    }
}
