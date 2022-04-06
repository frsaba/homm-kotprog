package Players;

import Display.Display;
import Field.Tile;
import Interface.Drawable;
import Managers.Controller;
import Managers.Game;
import Spells.Spell;
import Units.Unit;

import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Harcban résztvevő fél. Tárolja a sereget, a hőst és a varázslatokat. A kirajzolása gyakorlatilag egy seregszemle.
 */
public class Force implements Drawable {
    public Hero hero;

    Controller controller;
    public List<Unit> units = new ArrayList<>();
    public List<Unit> deadUnits = new ArrayList<>();
    public int gold;

    public List<Spell> spells = new ArrayList<>();

    final Color teamColor;

    public Color getTeamColor() {
        return teamColor;
    }

    public Force(Controller controller, int startingGold, Color color){
        this.controller = controller;
        controller.setForce(this);
        hero = new Hero( this);
        gold = startingGold == 0 ? 1000 : startingGold;
        teamColor = color;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        controller.setForce(this);
        this.controller = controller;
    }

    public void addSpell(Spell s){
        spells.add(s);
    }

    public void addUnit(Unit u){
        units.add(u);
        u.force = this;
    }


    public void unitDied(Unit unit){
        units.remove(unit);
        deadUnits.add(unit);

        Game.unitDied(unit);
    }

    public void resurrect(Unit unit, Tile targetTile){
        deadUnits.remove(unit);
        units.add(unit);
        unit.moveTo(targetTile);
    }

    public boolean hasLost(){
        return units.size() == 0;
    }

    public void nextMove(Unit unit){
        controller.nextMove(unit);
    }

    @Override
    public void draw(int top, int left) {
        Display.write(controller + " serege", top, left);

        Display.drawList(hero.skills.values().stream().map(
                s -> String.format("%10s - %2d pont", s.getDisplayName(), s.getSkill())).toList(),
                top + 2, left + 2,   "Hős tulajdonságai");

        Display.drawList(spells, top + 2, left + 28, "Varázslatok");

        Display.drawList(units.stream().map((u -> MessageFormat.format("{0} ({1} db)", u.getColoredName(), u.getCount()))).toList(),
                top + 2, left + 50, "Megvásárolt egységek");
    }
}
