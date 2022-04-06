package Units.Types;

import Units.Unit;
import Units.UnitProperties;
import Utils.ColorHelpers;

import java.awt.*;

/**
 * Griff osztály. Mindig vissza tud támadni, tehát a retaliate-et felüldefiniálja és kiveszi belőle az ellenőrzést
 */
public class Griff extends Unit {
    public Griff(){
//        super(amount);
        props = new UnitProperties("Griff","Végtelen visszatámadás",15, 5, 10, 30,7, 15);
//        heal(amount * props.health());
    }

    @Override
    public Color getTeamColor() {
        return ColorHelpers.applyFilter(super.getTeamColor(), Color.getHSBColor(0.5f,0.2f,0.5f));
    }


    //Képesség - végtelen visszatámadás - kihagyjuk a visszatámadottemárebbenakörben csekket
    @Override
    protected int retaliate(Unit target) {
        hasRetaliatedThisTurn = false;
        return super.retaliate(target);
    }
}