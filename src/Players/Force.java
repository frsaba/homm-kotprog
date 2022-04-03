package Players;

import Units.Unit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Force {
    public Hero hero;
    public List<Unit> units = new ArrayList<Unit>();
    public int gold;

    final Color teamColor;

    public Color getTeamColor() {
        return teamColor;
    }

    public Force( int startingGold, Color color){
        hero = new Hero( this);
        gold = startingGold;
        teamColor = color;
    }

    public void addUnit(Unit u){
        units.add(u);
        u.force = this;
    }

}
