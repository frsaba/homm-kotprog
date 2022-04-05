package Units.Types;

import Managers.Game;
import Units.Unit;
import Units.UnitProperties;
import Utils.ColorHelpers;

import java.awt.*;

public class Axolotl extends Unit {
    double damageMultiplier = 1;

    public Color getTeamColor() {
        return ColorHelpers.applyFilter(super.getTeamColor(), Color.getHSBColor(0.4f,0.2f,0.5f));
    }

    public Axolotl(){
        props = new UnitProperties("Axolotl Mehikói", "Minden körben gyógyul", 8, 2, 3, 4 ,6, 10 );
    }

    @Override
    public void beginTurn() {
        super.beginTurn();

        if(totalHealth == getOriginalHealth()) return;

        int amt = (getOriginalHealth() - totalHealth) / 3;
        heal(amt);
        Game.log("{0} visszanöveszti az agyát is. (+{1} hp)", this, amt);
    }
}
