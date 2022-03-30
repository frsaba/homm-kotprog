package Units.Types;

import Units.Unit;
import Units.UnitProperties;

public class Peasant extends Unit {
    public Peasant(){
        props = new UnitProperties("Földműves", "Nincs. Egy tanyán lakik.", 2, 1, 1, 3 ,4, 8 );
    }
}
