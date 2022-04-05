package Spells;

import Field.Tile;
import Players.Hero;
import Units.Unit;


/**
 * Villámcsapás varázslat
 */
public class LightningStrike extends Spell{

    public LightningStrike() {
        super(60, 5,
                "Villámcsapás",
                "Sebzés: varázserő * 30");
    }

    @Override
    public boolean isValidTarget(Tile tile, Hero caster) {
        return tile.hasUnit() && tile.unit.force.hero != caster; //Ellenséges egység
    }

    @Override
    public void use(Hero caster) {
        Unit target = caster.getForce().getController().pickUnit(false);

        target.takeDamage(caster.getMagic() * 30, this, false);
    }
}
