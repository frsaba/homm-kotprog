package Managers;

import Display.Display;
import Interface.Drawable;
import Interface.View;
import Units.Unit;
import Utils.Rect;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TurnManager extends View {

    int offset;
    int turn;
    List<Unit> units;

    public int getTurn() {
        return turn;
    }

    public TurnManager(Rect rect) {
        super(rect);
        this.turn = 1;
        this.units = new ArrayList<>();
    }

    public void setUnits(List<Unit> units) {
        this.units = units.stream().sorted(Comparator.comparingInt(
                u -> (-u.getInitiative()))).toList();
    }

    public List<Unit> getUnits() {
        return units;
    }

    public Unit getCurrentUnit() {
        return units.get(offset % units.size());
    }

    public void nextUnit() {
        offset++;
        draw();
        if (isNewTurn()) turn++;
    }

    public boolean isNewTurn() {
        return offset % units.size() == 0;
    }

    @Override
    public void draw(int top, int left) {
        super.draw(top, left);
        Display.write("Körsorrend [kezdeményezés]:", this.top + top - 1, this.left + 1);
        for (int i = 0; i < getHeight(); i++) {
            Display.clearToEndOfLine(this.top + top + i, this.left + left);

            Unit unit = units.get((i + offset) % units.size());
            String pre = (i == 0 ? "  -> " : "  -  ");

            Display.write(
                    String.format("%s [%2d] %s ", pre, unit.getInitiative(), unit.getColoredName()),
                    this.top + top + i, this.left + left + 1);
        }
    }
}
