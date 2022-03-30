package Units.Types;

import Units.Unit;
import Units.UnitProperties;

public class Gecko extends Unit {
    double damageMultiplier = 1;

    public Gecko(){
        props = new UnitProperties("Gecko", "Minden köre elején gyógyul az elszenvedett sebzés felével", 4, 2, 3, 4 ,6, 10 );
    }

    @Override
    public void beginTurn() {
        heal((getOriginalHealth() - totalHealth) / 2);
    }
}
