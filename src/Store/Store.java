package Store;

import Display.Display;
import Interface.Menu;
import Managers.Game;
import Players.Force;
import Players.Hero;
import Spells.*;
import Units.Types.*;
import Units.Unit;
import Utils.Rect;

import java.util.ArrayList;
import java.util.List;

//Bolt osztály. Táblázatosan kirajzolja a megvehető egységeket, varázslatokat és tulajdonságpontokat
public class Store {

    final List<Unit> purchasableUnits;
    final List<Spell> purchasableSpells;

    Force force;
    final Rect rect;

    public Store(Rect rect, Force force) {
        purchasableUnits = List.of(new Peasant(), new Archer(), new Griff(), new Axolotl(), new Thorn());
        purchasableSpells = List.of(new Fireball(), new LightningStrike(), new Resurrection(), new Whirlwind(), new Mutiny());

        this.force = force;

        purchasableUnits.forEach(u -> u.force = force);

        this.rect = rect;
    }

    String alignedColoredUnitName(Unit unit, int width) {
        return unit.getColoredName() + " ".repeat(width - unit.getName().length());
    }

    public Unit getUnitPurchase() {
        Display.clear();
        drawHeader("Egységvásárlás");

        String header = String.format(" ".repeat(12) + "%-12s" + " %8s".repeat(6),
                "Név", "Ár", "Sebzés", "Életerő", "Sebesség", "Kezdeményezés", " Speciális képesség");

        Menu<Unit> menu = new Menu<>(rect,
                //A már megvetteket és a túl drágákat nem mutatjuk többé
                purchasableUnits.stream().filter(u -> !force.units.contains(u) && force.gold >= u.props.price()).toList(),

                u -> String.format("%s" + "%-8s".repeat(4) + "%-12s %s",

                alignedColoredUnitName(u, 17), u.getPrice(), String.format("%d-%d", u.props.minDamage(), u.props.maxDamage()),
                        u.props.health(), u.props.speed(), u.props.initiative(), u.props.special()),
                force.units.size() == 0 ?"" : "Tovább"); //Nem engedjük tovább amíg legalább egy egységet nem vett

        if(force.units.size() > 0){
            Display.drawList(
                    force.units.stream().map(u -> u.getColoredName() + " * " + u.getCount()).toList(),
                    rect.bottom + 2, rect.left + 10,"Megvásárolt egységek:");
        }

        return menu.getUserChoice(header, "Válassz egységet:");
    }

    public Spell getSpellPurchase(){
        Display.clear();
        drawHeader("Varázslatok");

        String header = String.format(" ".repeat(12) + "%-12s %10s %12s %12s",
                "Név", "Ár", "Manna", " Leírás");

        Menu<Spell> menu = new Menu<>(rect, purchasableSpells.stream().filter(s -> !force.spells.contains(s)).toList(),
                (s) -> String.format("%-12s %10d %10d \t%s", s.getName(), s.getPrice(), s.getManaCost(), s.getDescription()),
              "Tovább");

        return menu.getUserChoice(header, "Válassz varázslatot:");
    }

    public void openSkillPointShop(Hero hero){
        Display.clear();

        Menu<Hero.Skill> skillMenu = new Menu<>(rect,
                new ArrayList<>(hero.getAllSkills().values()),
                s -> String.format("%s (%d)", s.getDisplayName(), s.getSkill()),
                "Tovább");

        while(true){
            drawHeader(String.format("%s tulajdonságai", hero));

            Hero.Skill sk = skillMenu.getUserChoice(String.format("\tNövelni kívánt tulajdonság (%d arany): ", hero.getSkillPrice()));
            if(sk == null) break;

            int cost = hero.getSkillPrice();

            if(cost > force.gold - 2){
                Game.logError("Nincs pénzed több tulajdonságpont megvételére!");
                continue;
            }

            boolean success = hero.incrementSkill(sk);
            if(success){
                force.gold -= cost;
                Game.clearErrors();
            }
            if(!success) Game.logError("Ez a tulajdonság már maximális!");
        }

        hero.fillMana();

    }

    public void drawHeader(String title) {
        int row = rect.top - 3;

        Display.write(row, rect.left + 8, title);
        Display.write(row, rect.right - 5, "%5s arany", force.gold);
        Display.write(row + 1, rect.left, "-".repeat(119));
    }
}
