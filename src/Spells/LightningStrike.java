package Spells;

import Field.Tile;
import Players.Hero;
import Units.Unit;




/**
 * Villámcsapás varázslat: Egy kiválasztott ellenséges egységre (varázserő * 22) sebzés okozása
 */
public class LightningStrike extends Spell{
    static final int dmgMultiplier = 22;

    public LightningStrike() {
        super(80, 7,
                "Villámcsapás",
                "Sebzés: varázserő * " + dmgMultiplier);
    }

    @Override
    public boolean isValidTarget(Tile tile, Hero caster) {
        return tile.hasUnit() && tile.unit.force.hero != caster; //Ellenséges egység
    }

    @Override
    public void use(Hero caster) {
        Unit target = caster.getForce().getController().pickUnit(false);

        target.takeDamage(caster.getMagic() * dmgMultiplier, this, false);
    }




}
