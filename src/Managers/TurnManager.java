package Managers;

import Display.Display;
import Interface.Drawable;
import Interface.View;
import Units.Unit;
import Utils.Rect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TurnManager extends View {

    int offset;
    List<Unit> units;

    public TurnManager(Rect rect) {
        super(rect);
        this.units = new ArrayList<>();
    }

    public void setUnits(List<Unit> units){
        this.units = units.stream().sorted(Comparator.comparingInt(
                u -> (u.props.initiative() + u.force.hero.getMorale()))).toList();
    }

    public Unit getCurrentUnit(){
        return units.get(offset % units.size());
    }

    public void nextUnit(){
        offset++;
    }

    @Override
    public void draw(int top, int left) {
        super.draw(top,left);
        Display.write("Körsorrend:", this.top + top - 1, this.left + 1);
        for (int i = 0; i < getHeight(); i++) {
            Display.write( (i == 0 ? "  ->" : " [" + (i+1) + "]") +
                    units.get(i % units.size()).getColoredName(),
                    this.top + top + i, this.left + left+1);
        }
    }
}
