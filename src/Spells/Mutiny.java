package Spells;

import Field.Tile;
import Managers.Game;
import Players.Hero;


/**
 * Zendülés varázslat. A két hadseregben lázadás tör ki, polgárháború indul és puccs megy végbe.
 */
public class Mutiny extends Spell{
    public Mutiny() {
        super(30, 7, "Zendülés", "Minden egység magába sebez egyet");
    }

    @Override
    public boolean isValidTarget(Tile target, Hero caster) {
        return true;
    }

    @Override
    public void use(Hero caster) {
        Game.getAllUnits().forEach(u -> u.takeDamage(u.getMinDamage(), u, false));
    }
}
