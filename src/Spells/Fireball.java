package Spells;

import Field.Tile;
import Managers.Game;
import Players.Hero;

import java.util.List;
import static Utils.Sleep.sleep;

/**
 * Tűzlabda varázslat: Egy kiválasztott mező körüli 3x3-as területen lévő összes
 * (saját, illetve ellenséges) egységre (varázserő * 20) sebzés okozása"
 */
public class Fireball extends Spell {
    public Fireball() {
        super(120, 9,
                "Tűzlabda",
                "Területi sebzés: varázserő * 20");
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

        // region visualization

        target.select();
        target.draw();
        sleep(200);

        area.forEach(Tile::select);
        target.deselect();
        Game.redrawField();
        sleep(200);

        area.forEach(Tile::deselect);
        Game.redrawField();

        // endregion

        for (Tile t : area) {
            if (t.hasUnit()) {
                t.unit.takeDamage(caster.getMagic() * 20, this, false);
            }
        }

    }
}
