package Spells;

import Field.Tile;
import Players.Hero;


/**
 * Villámcsapás varázslat
 */
public class LightningStrike extends Spell{

    public LightningStrike() {
        super(60, 5,
                "Villámcsapás",
                "Egy kiválasztott ellenséges egységre (varázserő * 30) sebzés okozása");
    }

    @Override
    public boolean isValidTarget(Tile tile, Hero caster) {
        return tile.hasUnit() && tile.unit.force.hero != caster; //Ellenséges egység
    }

    @Override
    public void use(Tile target, Hero caster) {
        target.unit.takeDamage(caster.getMagic() * 30, this, false);
    }
}
