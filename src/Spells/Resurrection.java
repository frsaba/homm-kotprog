package Spells;

import Field.Tile;
import Players.Hero;
import Units.Unit;

/**
 * Feltámasztás varázslat
 */
public class Resurrection extends Spell{

    public Resurrection() {
        super(120, 6,
                "Feltámasztás",
                "Egy kiválasztott saját egység feltámasztása. " +
                        "Maximális gyógyítás mértéke: (varázserő * 50), " +
                        "de az eredeti egységszámnál több nem lehet)\n");
    }

    @Override
    public boolean isValidTarget(Tile target, Hero caster) {
        if(!target.hasUnit()) return false;
        Unit u = target.unit;
        return  u.force.hero == caster && u.getOriginalHealth() != u.getHealth(); //Barátságos és nem teljes életerős egység
    }

    @Override
    public void use(Hero caster) {
        Unit target = caster.getForce().getController().pickUnit( true, "Gyógyítás célpontja:");
        target.heal(caster.getMagic() * 50);
    }
}
