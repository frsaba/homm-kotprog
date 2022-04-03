package Players;

import Field.Tile;
import Managers.Game;
import Spells.Spell;
import Units.Unit;
import Utils.ColorHelpers;
import Utils.Colors;

import java.awt.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Hero {
    Map<String, Skill> skills;

    int skillPrice;

    int mana;

    Force force;

    public double getAttackMultiplier() {
        return 1 + getSkill("attack") / 10.0;
    }

    public double getDefenseMultiplier() {
        return 1 - getSkill("defense") / 5.0;
    }

    public double getLuckMultiplier() {
        return 1 + getSkill("luck") / 5.0;
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
        if(skills.get(skill).increment()){
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
        unit.takeDamage(skills.get("attack").getSkill(), this, true);
    }

    public boolean canCastSpell(Spell spell) {
        return getMana() >= spell.getManaCost();
    }

    public void castSpell(Spell spell, Tile target) {
        if (!canCastSpell(spell)) {
            Game.logError("Nincs elég mana a {0} használatához!", spell);
            return;
        }
        if (!spell.isValidTarget(target, this)) {
            Game.logError("Érvénytelen célpont!");
            return;
        }
        setMana(getMana() - spell.getManaCost());
        spell.use(target, this);
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


    @Override
    public String toString() {
        return ColorHelpers.surroundWithColor(" Hős ", getTeamColor());
    }
}
