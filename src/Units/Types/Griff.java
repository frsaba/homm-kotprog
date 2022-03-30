package Units.Types;

import Managers.Game;
import Units.Unit;

public class Griff extends Unit {
    public Griff(){
//        super(amount);
        props = new UnitProperties("Griff","Ne etesd az állatokat!",15, 5, 10, 30,7, 15);
//        heal(amount * props.health());
    }


    //Képesség - végtelen visszatámadás - kihagyjuk a visszatámadottemárebbenakörben csekket
    @Override
    protected int counterAttack(Unit target) {
        Game.log(" {0} visszatámad!", getName());
        return attack(target, 0.5, false);
    }
}