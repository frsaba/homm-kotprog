package Spells;

import Field.Tile;
import Managers.Game;
import Players.Hero;

import java.util.List;

/**
 * Tűzlabda varázslat
 */
public class Fireball extends Spell {
    public Fireball() {
        super(120, 9,
                "Tűzlabda",
                "Egy kiválasztott mező körüli 3x3-as területen lévő összes " +
                        "(saját, illetve ellenséges) egységre (varázserő * 20) sebzés okozása");
    }

    @Override
    public boolean isValidTarget(Tile tile, Hero caster) {
        return true;
    }

    @Override
    public void use(Hero caster) {
        Tile target = caster.getForce().getController().pickTile("Tűzlabda célpontja [A0-L9]:");

        List<Tile> area = target.getNeighbors();
        area.add(target);


        try {
            target.select();
            Game.redrawField();
            Thread.sleep(100);

            area.forEach(Tile::select);
            target.deselect();
            Game.redrawField();
            Thread.sleep(100);

            area.forEach(Tile::deselect);
            Game.redrawField();

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        for (Tile t : area) {
            if (t.hasUnit()) {
                t.unit.takeDamage(caster.getMagic() * 20, this, false);
            }
    }


    }
}
