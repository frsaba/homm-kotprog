package Units.Types;

import Units.Unit;
import Units.UnitProperties;
import Utils.ColorHelpers;

import java.awt.*;

public class Gecko extends Unit {
    double damageMultiplier = 1;

    public Color getTeamColor() {
        return ColorHelpers.applyFilter(super.getTeamColor(), Color.getHSBColor(0.4f,0.2f,0.5f));
    }

    public Gecko(){
        props = new UnitProperties("Gecko", "Minden köre elején gyógyul az elszenvedett sebzés felével", 4, 2, 3, 4 ,6, 10 );
    }

    @Override
    public void beginTurn() {
        heal((getOriginalHealth() - totalHealth) / 2);
    }
}
