package Players;

import Managers.Game;
import Spells.Spell;
import Units.Unit;
import Utils.ColorHelpers;
import Utils.Colors;

import java.awt.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class Hero {
    Map<String, Skill> skills;

    int skillPrice;

    int mana;

    public boolean hasActedThisTurn = false;

    Force force;

    public Force getForce() {
        return force;
    }

    public int getAttackDamage() {
        return skills.get("attack").getSkill() * 10;
    }

    public double getAttackMultiplier() {
        return 1 + getSkill("attack") * 0.1;
    }

    public double getDefenseMultiplier() {
        return 1 - getSkill("defense") * 0.05;
    }

    public double getLuckChance() {
        return getSkill("luck") * 0.05;
    }

    public Color getTeamColor() {
        if (force == null) return Colors.grayTeamAccent;
        return force.getTeamColor();
    }

    public int getMagic() {
        return getSkill("magic");
    }

    public int getMana() {
        return mana;
    }

    public int getSkill(String skill) {
        if (!skills.containsKey(skill)) throw new RuntimeException("Nem létezik" + skill + " nevű tulajdonság!");
        return skills.get(skill).getSkill();
    }

    public int getMorale() {
        return getSkill("morale");
    }

    public boolean incrementSkill(String skill) {
        if (skills.get(skill).increment()) {
            skillPrice = (int) Math.ceil(skillPrice * 1.1);
            return true;
        }
        return false;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void fillMana() {
        setMana(skills.get("wisdom").getSkill() * 10);
    }

    public void attack(Unit unit) {
        unit.takeDamage(getAttackDamage(), this, true);
        hasActedThisTurn = true;
    }

    public boolean canCastSpell(Spell spell) {
        return getMana() >= spell.getManaCost();
    }

    public void castSpell(Spell spell) {
        if (!canCastSpell(spell)) {
            Game.logError("Nincs elég mana a {0} használatához!", spell);
            return;
        }
//        if (!spell.isValidTarget(target, this)) {
//            Game.logError("Érvénytelen célpont!");
//            return;
//        }
        setMana(getMana() - spell.getManaCost());
        spell.use(this);
        hasActedThisTurn = true;
    }


    public Hero(Force force) {

        this.force = force;
        var skillKeys = "attack defense magic wisdom morale luck".split(" ");
        var skillDisplayNames = "Támadás Védekezés Varázserő Tudás Morál Szerencse".split(" ");

        this.skills = new LinkedHashMap<>();
        for (int i = 0; i < skillKeys.length; i++) {
            skills.put(skillKeys[i], new Skill(skillDisplayNames[i]));
        }

        fillMana();
    }

    public void beginTurn() {
        hasActedThisTurn = false;
    }


    @Override
    public String toString() {
        return ColorHelpers.surroundWithColor(" Hős ", getTeamColor());
    }

}
