package Units.Types;

import Managers.Game;
import Units.Unit;
import Units.UnitProperties;
import Utils.ColorHelpers;
import Utils.GameConstants;

import java.awt.*;

public class Thorn extends Unit {

    public Thorn(){
        props = new UnitProperties("Tüskés Vazul","Kritikus sebzéssel támad vissza",5, 2, 3, 4 ,3, 16);
    }

    @Override
    public Color getTeamColor() {
        return ColorHelpers.applyFilter(super.getTeamColor(), Color.getHSBColor(0.45f,0.4f,0.5f));
    }

    @Override
    protected int retaliate(Unit target) {
        if(this.hasRetaliatedThisTurn) return 0;
        this.hasRetaliatedThisTurn = true;
        Game.log("{0} visszatámad, de nagyon!", getColoredName());
        return this.attack(target, GameConstants.CRITICAL_DMG_MULTIPLIER, false);
    }
}
