package Units.Types;

import Managers.Game;
import Units.Unit;
import Units.UnitProperties;
import Utils.ColorHelpers;

import java.awt.*;


/**
 * Földműves osztály
 */
public class Peasant extends Unit {
    public Peasant(){
        props = new UnitProperties("Földműves", "Nincs. Egy tanyán lakik.", 2, 1, 1, 3 ,4, 8 );
    }

    @Override
    public Color getTeamColor() {
        return ColorHelpers.applyFilter(super.getTeamColor(), Color.getHSBColor(0.5f,0.5f,0.45f));
    }
}
