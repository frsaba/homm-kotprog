package Field;

import Display.Display;
import Interface.Drawable;
import Managers.Game;
import Units.Unit;
import Utils.Colors;
import static Utils.Sleep.sleep;

import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A játéktábla egy mezőjét leíró osztály. Tárolja a szomszédsági kapcsolatait és  a rajta lévő egységet (ha van)
 */
public class Tile implements Drawable {
    public static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase(Locale.ROOT).toCharArray();

    public Unit unit = null;
    List<Tile> neighbors = new ArrayList<>();
    boolean selected;
    int row;
    int col;

    public List<Tile> getNeighbors() {
        return new ArrayList<>(neighbors);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private final int width = 5;
    private final int height = 2;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void addNeighbor(Tile t) {
        if (t == null) return;
        if (neighbors.contains(t)) return;
        neighbors.add(t);
        t.addNeighbor(this);
    }

    public boolean hasUnit() {
        return unit != null;
    }

    @Override
    public String toString() {
        return MessageFormat.format("T({0}-{1}) - {2}", row, col, hasUnit() ? unit : "Üres");
    }

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    @Override
    public void draw(int top, int left) {
        int r = top + row * height;
        int c = left + col * width;

        Color bg1 = selected ? Colors.tileSelected : hasUnit() ? unit.getTeamColor() : Colors.boardBase;
        Color bg2 = selected ? Colors.tileSelected : hasUnit() ? unit.getTeamColor() : Colors.boardAlt;
        Color fg = hasUnit() ? Color.black : Colors.boardMarkers;

        Display.setColor(fg, (row + col) % 2 == 1 ? bg1 : bg2);
        for (int i = 0; i < height; i++) {
            Display.write(String.join("", " ".repeat(width)), r + i, c);
        }

        if (hasUnit()) {
            unit.draw(r, c);
        } else {
            Display.write(getCoordinates(), r, c+ 1);
        }

        Display.resetStyling();
    }

    public void draw(){
        draw(Game.FIELD_TOP, Game.FIELD_LEFT);
    }

    public void flash(){
        flash(1000);
    }

    public void flash(int millis){
        select();
        draw();
        
        sleep(millis);

        deselect();
        draw();
    }

    public String getCoordinates(){
        return alphabet[col] + String.valueOf(row);
    }
}
