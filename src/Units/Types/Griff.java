package Units.Types;

import Units.Unit;

public class Griff extends Unit {
    public Griff(int amount){
        super(amount);
        props = new UnitProperties("Griff","Ne etesd az állatokat!",15, 5, 10, 30,7, 15);
        heal(amount * props.health());
    }

    @Override
    protected int counterAttack(Unit other) {
        return attack(other, 0.5);
    }
}