package Spells;

import Field.Tile;
import Players.Hero;

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
        return target.hasUnit() && target.unit.force.hero == caster; //Barátságos egység
    }

    @Override
    public void use(Tile target, Hero caster) {
        target.unit.heal(caster.getMagic() * 50);
    }
}
