package Players;

import Field.Tile;
import Managers.Controller;
import Units.Unit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Force {
    public Hero hero;

    Controller controller;
    public List<Unit> units = new ArrayList<>();
    public List<Unit> deadUnits = new ArrayList<>();
    public int gold;

    final Color teamColor;

    public Color getTeamColor() {
        return teamColor;
    }

    public Force(Controller controller, int startingGold, Color color){
        this.controller = controller;
        controller.setForce(this);
        hero = new Hero( this);
        gold = startingGold;
        teamColor = color;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        controller.setForce(this);
        this.controller = controller;
    }

    public void addUnit(Unit u){
        units.add(u);
        u.force = this;
    }

    public void unitDied(Unit unit){
        units.remove(unit);
        deadUnits.add(unit);
    }

    public void resurrect(Unit unit, Tile targetTile){
        deadUnits.remove(unit);
        units.add(unit);
        unit.moveTo(targetTile);
    }

    public void nextMove(Unit unit){
        controller.nextMove(unit);
    }

}
