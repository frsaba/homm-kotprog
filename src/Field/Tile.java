package Field;

import Units.Unit;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Tile {
    public Unit unit = null;
    public List<Tile> neighbors = new ArrayList<>();
    int row;
    int col;

    public Tile(int row, int col){
        this.row = row;
        this.col = col;
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
}
