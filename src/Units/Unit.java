package Units;

import Display.Display;
import Field.Tile;
import Interface.Drawable;
import Players.Force;
import Players.Hero;
import Store.Purchasable;
import Units.Types.UnitProperties;
import Managers.Game;
import Utils.Colors;

import java.awt.*;
import java.text.MessageFormat;
import java.util.Random;

public abstract class Unit implements Purchasable, Drawable {
    public UnitProperties props;

    public Force force;
    protected Tile occupiedTile;
    int originalCount;
    int totalHealth;

    boolean hasRetaliatedThisTurn = false;

    public String getName(){
        return props.name();
    }
    public String getDescription(){
        return props.description();
    }
    public int getPrice(){
        return props.price();
    }
    public Color getTeamColor(){
        if(force == null) return Colors.grayTeamAccent;
        return force.getTeamColor();
    }

    public int getCount() {
        return (int) Math.ceil ((double)totalHealth / props.health());
    }

    Random random = new Random();

    public void moveTo(Tile t){
        if(occupiedTile != null) occupiedTile.unit = null;
        occupiedTile = t;
        t.unit = this;
    }

    protected int attack(Unit target, double dmgMultiplier, boolean canRetaliate){
        if(!isInRange(target)){
            System.err.println(target + " nincs " + this + " hatótávolságán belül!");
            return 0;
        }

        int min = props.minDamage();
        int max = props.maxDamage();
        int diff = max - min;

        int baseDamage = getCount() * (min + (diff == 0 ? 0 : random.nextInt(diff)));

        int dmg = (int) Math.round(baseDamage * this.force.hero.getAttackMultiplier() * target.force.hero.getDefenseMultiplier() * dmgMultiplier);
        target.takeDamage(dmg, this);
        if(canRetaliate) target.counterAttack(this);

        return dmg;
    }

    public int attack(Unit target){
        if(Game.getRandomDouble() > 0.5 * force.hero.getLuckMultiplier()){
            Game.log("Kritikus támadás!");
            return criticalAttack(target);
        }

        return this.attack(target, 1.0, true);
    }

    protected int counterAttack(Unit target){
        if(this.hasRetaliatedThisTurn) return 0;
        this.hasRetaliatedThisTurn = true;
        Game.log("{0} visszatámad!", getName());
        return this.attack(target, 0.5, false);

    }

    public int criticalAttack(Unit target){
        return this.attack(target, 2.0, true);
    }

    public boolean isInRange(Unit other){
        //return occupiedTile.neighbors.contains(other.occupiedTile);
        return true;
    }

    public void takeDamage(int damage, Object source){
        this.totalHealth -= damage;
        if(totalHealth <= 0) {
            die();
            return;
        }
//        if(source != null) counterAttack(source);

        Game.log("{0} támad: {1} sebzés --> {2}", source, damage, this);
    }

    public void heal(int amount){
        totalHealth = Math.min(totalHealth + amount , originalCount * props.health());
    }

    private void die(){
        Game.log("{0} Meghalt!", getName());
    }

    public void setAmount(int amount){
        originalCount = amount;
        heal(amount * props.health());
    }

    @Override
    public String toString() {
        String ansiColor =  Display.getColorString( Color.black, getTeamColor());
        return MessageFormat.format(
                "{0} {1} ({2,number,#}/{3,number,#}) {4}",
                Display.getColorString( Color.black, getTeamColor()),
                props.name(), totalHealth, originalCount * props.health(),
                Display.ANSI_RESET);
    }

    public void beginTurn(){
        hasRetaliatedThisTurn = false;
    }

    @Override
    public void draw(int top, int left) {
        Display.write(props.name().substring(0,Math.min(5,props.name().length())), top, left);
        Display.write(String.valueOf(getCount()), top + 1, left);
    }
}
