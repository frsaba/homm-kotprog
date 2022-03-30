package Units.Types;

import Managers.Game;
import Units.Unit;
import Units.UnitProperties;
import Utils.GameConstants;

public class Thorns extends Unit {

    public Thorns(){
        props = new UnitProperties("Tüskés Vazul","Kritikus sebzéssel támad vissza",5, 2, 3, 4 ,3, 16);
    }

    @Override
    protected int retaliate(Unit target) {
        if(this.hasRetaliatedThisTurn) return 0;
        this.hasRetaliatedThisTurn = true;
        Game.log("{0} visszatámad, de nagyon!", getName());
        return this.attack(target, GameConstants.CRITICAL_DMG_MULTIPLIER, false);
    }
}
