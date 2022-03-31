package Players;

import Field.Tile;
import Managers.Game;
import Spells.Spell;
import Units.Unit;

public class Hero {
    int attack;
    int defense;
    int magic;
    int wisdom;
    int morale;
    int luck;

    int spentSkillPoints;

    int mana = 0;

    public double getAttackMultiplier(){
        return 1 + (attack / 10.0);
    }
    public double getDefenseMultiplier(){
        return 1 - (defense / 5.0);
    }
    public double getLuckMultiplier(){
        return 1 + (luck / 5.0);
    }

    public int getMagic() {
        return magic;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        if(mana < 0) mana = 0;
        this.mana = mana;
    }

    void attack(Unit unit){
        unit.takeDamage(attack, this, true);
    }

    public boolean canCastSpell(Spell spell){
        return getMana() >= spell.getManaCost();
    }

    void castSpell(Spell spell, Tile target){
        if(!canCastSpell(spell)){
            Game.logError("Nincs elég mana a {0} használatához!", spell);
            return;
        }
        if(!spell.isValidTarget(target, this)){
            Game.logError("Érvénytelen célpont!");
            return;
        }
        setMana(getMana() - spell.getManaCost());
        spell.use(target, this);
    }
}
