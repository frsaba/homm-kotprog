package Units.Types;

import Units.Unit;

public class Peasant extends Unit {
    public Peasant(int amount){
        super(amount);
        props = new UnitProperties("Földműves", "Tanyán lakik", 2, 1, 1, 3 ,4, 8 );

        heal(amount * props.health());
    }
}
