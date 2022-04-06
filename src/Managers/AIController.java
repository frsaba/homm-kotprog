package Managers;

import Field.Field;
import Field.Tile;
import Players.Force;
import Players.Hero;
import Spells.Resurrection;
import Spells.Spell;
import Units.Types.Archer;
import Units.Types.Griff;
import Units.Types.Thorn;
import Units.Unit;
import Utils.ColorHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static Utils.Sleep.sleep;

public class AIController implements Controller{

    Force force;
    String playerName;

    Resurrection resurrection = new Resurrection();

    public Hero getHero() {
        return force.hero;
    }

    Random random = new Random();

    public Force getForce() {
        if (force == null) throw new RuntimeException("AIControl nem kapott Forcet!");
        return force;
    }


    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void setForce(Force force) {
        this.force = force;
    }

    void buySkill(String skill, int amt){
        for (int i = 0; i < amt; i++) {
            int cost = getHero().getSkillPrice();
            if(getHero().incrementSkill(skill)){
                force.gold -= cost;
            }
        }
    }

    void buyUnit(Unit unit, int maxSpentGold){
        int amt = maxSpentGold / unit.getPrice();

        force.addUnit(unit);
        force.gold -= amt * unit.getPrice();
        unit.setAmount(amt);
    }

    void buySpell(Spell spell){
        force.addSpell(spell);
        force.gold -= spell.getPrice();
    }

    @Override
    public void assembleArmy() {
        buySkill("attack", random.nextInt(2,4));
        buySkill("defense", random.nextInt(1,7));
        buySkill("luck", random.nextInt(6,10));
        buySkill("morale", random.nextInt(1,4));

        buySpell(resurrection);

        buyUnit(new Archer(), force.gold / 2);
        buyUnit(new Griff(), force.gold / 2);
        buyUnit(new Thorn(), force.gold);

    }

    @Override
    public void placeUnits(boolean leftSide) {
        if(leftSide) throw new RuntimeException("Ellenfél a bal oldalon, nemá");

        Field f = Game.field;
        for(Unit unit : force.units){

            while(true){

                //Íjász a hátsó sorba
                Tile t = f.getTile(random.nextInt(f.NUM_ROWS), unit instanceof Archer ? 11 : 10);
                if(!t.hasUnit()){
                    unit.moveTo(t);
                    break;
                }

            }
        }
    }

    List<Unit> getEnemyUnits() {
        return Game.getAllUnits().stream().filter(u -> u.force != force).toList();
    }

    Unit pickUnitToAttack(int expectedDamage){
        //Ha van valami amit egyből megölünk, akkor támadjuk azt
        var willKill = getEnemyUnits().stream().filter(u -> u.getHealth() <= expectedDamage).toList();
        if(willKill.size() > 0) return willKill.get(0);

        //az íjászok kiemelt célpontok
        var archers = getEnemyUnits().stream().filter(u -> u instanceof Archer).toList();
        if(archers.size() > 0) return archers.get(0);

        //máskülönben a leggyengébbel kezdünk
        return getEnemyUnits().stream().min(Comparator.comparingInt(Unit::getHealth)).get();
    }

    void moveUnitTowards(Unit unit, Unit target){
        var empty = target.getOccupiedTile().getNeighbors().stream().filter(t -> !t.hasUnit()).toList();
        if(empty.size() > 0) unit.moveTo(empty.get(0));
    }

    @Override
    public void nextMove(Unit unit) {
        sleep(1250);

        Hero hero = getHero();
        List<Unit> enemyUnits = getEnemyUnits();

        if(!hero.hasActedThisTurn){

            Unit damagedFriendly = pickUnit(true);

            if(hero.canCastSpell(resurrection) && damagedFriendly != null){
                hero.castSpell(resurrection);
            }else{
                getHero().attack(pickUnitToAttack(getHero().getAttackDamage()));
            }

            sleep(750);
        }

        //Ha tudunk támadni, támadjunk
        for(Unit enemy : enemyUnits){
            if(unit.canAttack(enemy)){
                unit.attack(enemy);
                return;
            }
        }

        //Ha bekerítették az íjászt, fussunk
        if(unit instanceof Archer && unit.hasNeighboringEnemy()){
            unit.moveTo(pickTile(t -> !t.hasUnit()));
            return;
        }

        //Máskülönben rámozdulunk a következő célpontra
        moveUnitTowards(unit, pickUnitToAttack(unit.getMinDamage()));


    }


    //Feltámasztásra érdemes barátságos egység
    @Override
    public Unit pickUnit(boolean friendly) {
        var damaged =  force.units.stream().filter(u -> (double ) u.getHealth() / u.getOriginalHealth() < 0.5).toList();
        if(damaged.size() > 0) return damaged.get(0);
        return null;
    }

    @Override
    public Unit pickUnit(boolean friendly, String prompt) {
        return pickUnit(friendly);
    }

    @Override
    public Unit pickUnit(Function<Unit, Boolean> filterFunction, String prompt) {
        return null;
    }

    @Override
    public Tile pickTile(String s) {
        return null;
    }

    public Tile pickTile(Function<Tile, Boolean> filter){
        Field f = Game.field;

        while(true){
            Tile t = f.getTile(random.nextInt(f.NUM_ROWS), random.nextInt(f.NUM_COLS));
            if(filter.apply(t)) return t;
        }
    }


    @Override
    public void newTurn() {
        getHero().beginTurn();
        for (Unit unit : force.units) {
            unit.beginTurn();
        }
    }


    @Override
    public String toString() {
        return ColorHelpers.surroundWithColor(getPlayerName(), force.getTeamColor());
    }

    public AIController(String playerName) {
        this.playerName = playerName;
    }
}
