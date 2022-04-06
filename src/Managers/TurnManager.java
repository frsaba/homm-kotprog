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

/**
 * Körsorrendet felügyelő osztály.
 */
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
        this.units = new ArrayList<Unit>(
                units.stream().sorted(Comparator.comparingInt(
                        u -> (-u.getInitiative()))).toList());
    }

    public List<Unit> getUnits() {
        return units;
    }

    public Unit getCurrentUnit() {
        return units.get(offset % units.size());
    }

    public void nextUnit() {
        offset++;
        if (isNewTurn())
            turn++;
        draw();
    }

    public boolean isNewTurn() {
        return offset % units.size() == 0;
    }

    /**
     * Az adott egységet eltávolítja a körsorrendből úgy, hogy a jelenlegi egység
     * maradjon sorban
     */
    public void removeUnit(Unit unit) {
        Unit currentUnit = getCurrentUnit();

        // if(currentUnit == unit){
        // Game.log("idk");
        // }

        units.remove(unit);

        offset = currentUnit == unit ? offset - 1 : units.indexOf(currentUnit);

        draw();

    }

    @Override
    public void draw(int top, int left) {
        super.draw(top, left);
        Display.write("Körsorrend [kezdeményezés]:", this.top + top - 1, this.left + 1);

        int nextTurn = turn + 1;
        for (int i = 0; i < getHeight(); i++) {
            Display.clearToEndOfLine(this.top + top + i, this.left + left);

            int unitIndex = (i + offset) % units.size();

            Unit unit = units.get(unitIndex);

            String pre = "   -  ";

            if (unitIndex == 0) {

                pre = String.format("%2d.kör", nextTurn);
                // arra az esetre, ha belátható több kör kezdete is
                if (i != 0)
                    nextTurn++;

            }

            if (i == 0)
                pre = "   -> ";

            Display.write(
                    String.format("%s [%2d] %s ", pre, unit.getInitiative(), unit.getColoredName()),
                    this.top + top + i, this.left + left + 1);
        }
    }
}
