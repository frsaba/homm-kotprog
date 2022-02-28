package Units;

import Field.Tile;
import Players.Force;
import Store.Purchasable;
import Units.Types.UnitProperties;

import java.util.Random;

public abstract class Unit implements Purchasable {
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

    public void MoveTo(Tile t){
        occupiedTile.unit = null;
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

    @Override
    public String toString() {
        return props.name() + " (" + totalHealth + "/" + (originalCount * props.health()) + ")";
    }

    public Unit(int amount){
        originalCount = amount;
        //totalHealth = amount * props.health();
    }
}
