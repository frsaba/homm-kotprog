package Spells;

import Field.Field;
import Field.Tile;
import Managers.Game;
import Players.Hero;
import Units.Unit;

import java.util.List;
import java.util.Random;


/**
 * Forgószél varázslat
 */
public class Whirlwind extends Spell{

    public Whirlwind() {
        super(40, 3, "Forgószél", "Az összes egységet véletlenszerűen szétszórja a pályán.");
    }

    @Override
    public boolean isValidTarget(Tile target, Hero caster) {
        return true;
    }

    @Override
    public void use(Hero caster) {
        Random random = new Random();

        Field f = Game.field;

        for(Unit u : Game.getAllUnits()){
            Tile t;
            do{
                t = Game.field.getTile(random.nextInt(f.NUM_ROWS), random.nextInt(f.NUM_COLS));
            } while (t.hasUnit());

            u.moveTo(t);
        }

        Game.redrawField();
    }
}
