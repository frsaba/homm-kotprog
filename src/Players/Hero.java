package Players;

import Units.Unit;

public class Hero {
    int attack;
    int defense;
    int magic;
    int intelligence;
    int morale;
    int luck;

    int spentSkillPoints;

    public double getAttackMultiplier(){
        return 1 + (attack / 10.0);
    }
    public double getDefenseMultiplier(){
        return 1 - (defense / 5.0);
    }
    public double getLuckMultiplier(){
        return 1 + (luck / 5.0);
    }

    void attack(Unit unit){
        unit.takeDamage(attack, this, true);
    }

    void castSpell(){

    }
}
