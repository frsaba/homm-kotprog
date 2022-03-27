package Field;

import Display.Display;
import Interface.Border;
import Interface.Drawable;
import Units.Unit;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Tile implements Drawable {
    public Unit unit = null;
    public List<Tile> neighbors = new ArrayList<>();
    int row;
    int col;

    private final Border border;
    private final int width = 5;
    private final int height = 2;

    public Tile(int row, int col){
        this.row = row;
        this.col = col;
        border = new Border("#", width, height);
    }

    public void addNeighbor(Tile t){
        if(t == null) return;
        if(neighbors.contains(t)) return;
        neighbors.add(t);
        t.addNeighbor(this);
    }

    public boolean hasUnit(){
        return unit != null;
    }

    @Override
    public String toString() {
        return MessageFormat.format("T({0}-{1}) - {2}", row, col, hasUnit() ? unit : "Üres");
    }

    @Override
    public void draw(int top, int left) {
        int r = top + row * (height + 1);
        int c = left + col * (width + 1);
        border.draw(r, c);
        if(hasUnit()) unit.draw(r, c);
    }
}
