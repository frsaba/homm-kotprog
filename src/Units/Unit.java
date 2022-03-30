package Units;

import Display.Display;
import Field.Tile;
import Interface.Drawable;
import Players.Force;
import Store.Purchasable;
import Managers.Game;
import Utils.Colors;
import Utils.GameConstants;

import java.awt.*;
import java.text.MessageFormat;
import java.util.Random;

/**
 * Általános egység
 */
public abstract class Unit implements Purchasable, Drawable {
    public UnitProperties props;

    public Force force;
    protected Tile occupiedTile;
    protected int originalCount;
    protected int totalHealth;

    protected boolean hasRetaliatedThisTurn = false;

    public String getName(){
        return props.name();
    }
    public String getDescription(){
        return props.special();
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

    public int getOriginalHealth(){
        return originalCount * props.health();
    }

    Random random = new Random();

    public void moveTo(Tile t){
        if(t == occupiedTile){
            Game.logError("Nem mentünk sehova.");
            return;
        }
        if(t.hasUnit()){
            Game.logError("{0} cella már foglalt!", t);
            return;
        }

        if(occupiedTile != null) occupiedTile.unit = null;
        occupiedTile = t;
        t.unit = this;
    }


    //region Támadásfajták


    /**
     * Általános támadás. A különböző támadásfajták ezt paraméterezik fel.
     * Kisorsolja a sebzést a támadó min és max sebzése közé, felszorozza két oldal hősének támadó illetve védő szorzóival,
     * illetve a paraméterben beadott végső szorzóval, és az így kapott sebzést beadja a célegységnek.
     * @param target a támadás célpontja
     * @param dmgMultiplier kiosztott sebzésre ható végső szorzó
     * @param canRetaliate visszatámadhat-e a célpont egység
     * @return a kiosztott sebzést
     */
    protected int attack(Unit target, double dmgMultiplier, boolean canRetaliate){

        if(this.isDead()){
            Game.logError("{0} nem tud támadni, mert halott!", this);
            return 0;
        }

        if(target.isDead()){
            Game.logError("{0}-t minek támadni, már halott!", target);
            return 0;
        }

        if(this.force == target.force){
            Game.logError("Friendly fire! {0} -> {1}", this, target);
            return 0;
        }

        if(!isInRange(target)){
            Game.logError(target + " nincs " + this + " hatótávolságán belül!");
            return 0;
        }

        int min = props.minDamage();
        int max = props.maxDamage();
        int diff = max - min;

        int bonusDamage = diff == 0 ? 0 : random.nextInt(diff);

        int baseDamage = (min + bonusDamage) * getCount();

        int dmg = (int) Math.round(baseDamage * this.force.hero.getAttackMultiplier() * target.force.hero.getDefenseMultiplier() * dmgMultiplier);
        target.takeDamage(dmg, this, canRetaliate);

        return dmg;
    }

    /**Alapértelmezett támadás. Amennyiben kritikus találatot sorsol, a kritikus támadás metódusát hívja, máskülönben 1-szeres szorzóval támad.
     * @param target a támadás célpontja
     * @return a kiosztott sebzést
     */
    public int attack(Unit target){
        if(Game.getRandomDouble() > GameConstants.BASE_CRIT_CHANCE * force.hero.getLuckMultiplier()){
            Game.log("Kritikus támadás:");
            return criticalAttack(target);
        }

        return this.attack(target, 1, true);
    }


    /** Megtorló támadás. Ellenőrzi, hogy az egység visszatámadhat-e (nem támadott még vissza ebben a körben), majd csökkentett szorzóval támad.
     * @param target a visszatámadás célpontja (az eredeti kezdeményező)
     * @return a kiosztott sebzést
     */
    protected int retaliate(Unit target){
        if(this.hasRetaliatedThisTurn) return 0;
        this.hasRetaliatedThisTurn = true;
        Game.log("{0} visszatámad!", getName());
        return this.attack(target, GameConstants.RETALIATE_DMG_MULTIPLIER, false);

    }


    /** Kritikus támadás. Meghívja az általános támadást kritikus szorzóval.
     * @param target a támadás célpontja
     * @return a kiosztott sebzést
     */
    public int criticalAttack(Unit target){
        return this.attack(target, GameConstants.CRITICAL_DMG_MULTIPLIER, true);
    }
    //endregion

    public boolean isInRange(Unit other){
        //return occupiedTile.neighbors.contains(other.occupiedTile);
        return true;
    }


    /** Sebzés elszenvedése. A kapott sebzést levonja az életerőből, és ha az 0-ra csökken, meghívja a die()-t.
     * Ha egy egységtől kapta a sebzést és a canRetaliate igaz, akkor visszatámad.
     * @param damage kapott sebzés
     * @param source a sebzést kiosztó objektum
     * @param canRetaliate visszatámadhat-e válaszul
     */
    public void takeDamage(int damage, Object source, boolean canRetaliate){
        this.totalHealth -= damage;
        if(totalHealth <= 0) {
            die();
            return;
        }
        Game.log("{0} támad: {1} sebzés --> {2}", source, damage, this);
        if(canRetaliate && source instanceof Unit) retaliate((Unit)source);
    }

    public void heal(int amount){
        totalHealth = Math.min(totalHealth + amount , getOriginalHealth());
    }

    private void die(){
        totalHealth = 0;
        Game.log("{0} meghalt!", getName());
    }

    public boolean isDead(){
        return totalHealth <= 0;
    }

    public void setAmount(int amount){
        originalCount = amount;
        heal(amount * props.health());
    }

    @Override
    public String toString() {
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
