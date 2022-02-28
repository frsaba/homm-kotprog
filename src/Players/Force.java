package Players;

import Units.Unit;

import java.util.ArrayList;
import java.util.List;

public class Force {
    public Hero hero;
    public List<Unit> units = new ArrayList<Unit>();
    public int gold;

    public Force(Hero hero, int startingGold){
        this.hero = hero;
        gold = startingGold;
    }

    public void addUnit(Unit u){
        units.add(u);
        u.force = this;
    }

}
