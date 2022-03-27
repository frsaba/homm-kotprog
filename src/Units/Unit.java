package Units;

import Display.Display;
import Field.Tile;
import Interface.Drawable;
import Players.Force;
import Store.Purchasable;
import Units.Types.UnitProperties;

import java.util.Random;

public abstract class Unit implements Purchasable, Drawable {
    public UnitProperties props;

    public Force force;
    protected Tile occupiedTile;
    int originalCount;
    int totalHealth;

    boolean hasAttacked = false;

    public String getName(){
        return props.name();
    }
    public String getDescription(){
        return props.description();
    }
    public int getPrice(){
        return props.price();
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

    protected int attack(Unit other, double dmgMultiplier){
        if(!isInRange(other)){
            System.err.println(other + " nincs " + this + " hatótávolságán belül!");
            return 0;
        }

        int min = props.minDamage();
        int max = props.maxDamage();
        int diff = max - min;

        int baseDamage = getCount() * (min + (diff == 0 ? 0 : random.nextInt(diff)));


        int dmg = (int) Math.round(baseDamage * this.force.hero.getAttackMultiplier() * other.force.hero.getDefenseMultiplier() * dmgMultiplier);
        other.takeDamage(dmg, this);

        this.hasAttacked = true;
        return dmg;
    }

    public int attack(Unit other){
        return this.attack(other, 1.0);
    }

    protected int counterAttack(Unit other){
        if(this.hasAttacked ) return 0;
        return this.attack(other, 0.5);

    }

    public int criticalAttack(Unit other){
        return this.attack(other, 2.0);
    }

    public boolean isInRange(Unit other){
        //return occupiedTile.neighbors.contains(other.occupiedTile);
        return true;
    }

    public void takeDamage(int damage, Unit source){
        this.totalHealth -= damage;
        if(totalHealth <= 0) {
            die();
            return;
        }
        if(source != null) counterAttack(source);
    }

    public void heal(int amount){
        totalHealth = Math.min(totalHealth + amount , originalCount * props.health());
    }

    private void die(){

    }

    public void setAmount(int amount){
        originalCount = amount;
        heal(amount * props.health());
    }

    @Override
    public String toString() {
        return props.name() + " (" + totalHealth + "/" + (originalCount * props.health()) + ")";
    }

    public Unit(){
       //setAmount(1);
    }

//    public Unit(int amount){
//        setAmount(amount);
//    }


    @Override
    public void draw(int top, int left) {
        Display.write(props.name().substring(0,3), top, left);
        Display.write(totalHealth + "/" + (originalCount * props.health()), top + 1, left);
    }
}
