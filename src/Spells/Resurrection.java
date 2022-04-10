package Spells;

import Field.Tile;
import Managers.Game;
import Players.Hero;
import Units.Unit;

/**
 * Feltámasztás varázslat: "Egy kiválasztott saját egység feltámasztása.
 * Maximális gyógyítás mértéke: (varázserő * 50), de az eredeti egységszámnál több nem lehet)"
 */
public class Resurrection extends Spell{

    public Resurrection() {
        super(100, 6,
                "Feltámasztás",
                "Gyógyítás: varázserő * 50");
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
        int amt = caster.getMagic() * 50;
        target.heal(amt);
        target.getOccupiedTile().draw();
        Game.log("Feltámasztás -> {0}  (+{1} hp)", target, amt);
    }
}
