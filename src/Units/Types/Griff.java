package Units.Types;

import Units.Unit;
import Units.UnitProperties;

public class Griff extends Unit {
    public Griff(){
//        super(amount);
        props = new UnitProperties("Griff","Végtelen visszatámadás",15, 5, 10, 30,7, 15);
//        heal(amount * props.health());
    }


    //Képesség - végtelen visszatámadás - kihagyjuk a visszatámadottemárebbenakörben csekket
    @Override
    protected int retaliate(Unit target) {
        hasRetaliatedThisTurn = false;
        return super.retaliate(target);
    }
}