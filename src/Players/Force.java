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

    public Force(Hero hero, int startingGold, Color color){
        this.hero = hero;
        gold = startingGold;
        teamColor = color;
    }

    public void addUnit(Unit u){
        units.add(u);
        u.force = this;
    }

}
