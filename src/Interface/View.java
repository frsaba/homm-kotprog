package Interface;

import java.util.ArrayList;
import java.util.List;

public class View {

    int top;
    int left;
    int bottom;
    int right;

    protected List<View> subviews;

    public void addSubView(View view){
        subviews.add(view);
    }

    public void Draw(){
        for (View view : subviews){
            view.Draw();
        }
    }

    int getHeight(){
        return bottom - top;
    }

    int getWidth(){
        return right - left;
    }

    public View(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;

        subviews = new ArrayList<>();
    }
}
