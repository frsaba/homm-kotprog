package Managers;

import Display.Display;
import Field.Tile;
import Interface.Command;
import Interface.OptionList;
import Interface.View;
import Players.Force;
import Units.Unit;
import Utils.ColorHelpers;
import Utils.Rect;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import static java.text.MessageFormat.format;


/**
 * Játékos által irányított résztvevő vezérlése. Minden körben utasítást kér a felhasználótól.
 */
public class UserController extends View implements Controller {

    Rect rect = new Rect(top, left, bottom, right);

    Force force;

    public Force getForce() {
        if (force == null) throw new RuntimeException("UserControl nem kapott Forcet!");
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    String playerName;

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Megkéri a felhasználót, hogy adjon egy mező koordinátát
     *
     * @param requirements követelményfüggvény, aminek meg kell felelnie egy cellának.
     *                     Ez egy függvény ami egy mezőre ad egy hibaüzenetet Stringet, hogy milyen követelménynek nem felel meg a cella;
     *                     ha üres Stringet ad, akkor a cella megfelelő.
     * @return a játékos által kiválasztott mezőt
     */
    public Tile pickTile(Function<Tile, String> requirements) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Display.clearToEndOfLine(top + 1, left, bottom - 1);
                Display.write("Cél mező [A0 - L9]: ", top + 3, left + 3);
                String next = scanner.next();

                Tile t = Game.field.getTile(next);
                if (t != null) { //getTile nullt ad, ha pályán kívülre hivatkozunk
                    String req = requirements.apply(t);
                    if (Objects.equals(req, "")) return t;
                    Game.logError(req);
                }

            } catch (Exception e) {
                Game.logError("Érvénytelen bemenet!");
            }

        }
    }

    public Tile pickTile() {
        return pickTile(t -> "");
    }

    public Unit pickUnit(boolean friendly, String prompt) {
        OptionList<Unit> unitOptions = new OptionList<>(rect,
                Game.getAllUnits().stream().filter(u -> u.force.equals(getForce()) == friendly).toList());

        return unitOptions.getUserChoice(prompt);
    }

    public Unit pickUnit(Function<Unit, Boolean> filterFunction, String prompt) {
        OptionList<Unit> unitOptions = new OptionList<>(rect,
                Game.getAllUnits().stream().filter(filterFunction::apply).toList());

        return unitOptions.getUserChoice(prompt);
    }

    public Unit pickUnit(boolean friendly) {
        return pickUnit(friendly, "\tVálassz célpontot: ");
    }


    /**
     * Adott egység következő cselekedetének megválasztása
     *
     * @param unit A soron következő egység
     */
    public void nextMove(Unit unit) {

        Display.clearToEndOfLine(top, left);


        final OptionList<Command> optionList = new OptionList<>(rect);

        List<Command> unitCommands = new LinkedList<>();

        //Akkor lehet csak támadást választani, ha van támadható ellenséges egység
        if (Game.getAllUnits().stream().anyMatch(unit::canAttack)) {

            String expectedDamage = format(" (várható sebzés: {0}-{1})", unit.getMinDamage(), unit.getMaxDamage());

            unitCommands.add(new Command(
                    "Támadás" + expectedDamage, () -> unit.attack(
                    pickUnit(unit::canAttack, "Válassz célpontot" + expectedDamage + ":")))
            );
        }

        unitCommands.addAll(List.of(
                new Command(format("Mozgás (max. {0} mező)", unit.props.speed()), () -> {
                    unit.moveTo(pickTile(t -> {
                        if (t.hasUnit() && t.unit == unit) return "Már ott van az egység!";
                        if (t.hasUnit()) return "Üres cellát kell választanod!";
                        return "";
                    }));
                    Game.clearErrors();
                }),
                new Command("Várakozás\n", () -> Game.log("{0} befejezte a körét.", unit.getColoredName()))
        ));


        List<Command> heroCommands = List.of(
                new Command(format("Hős támadás ({0} sebzés)", force.hero.getAttackDamage()), () -> force.hero.attack(pickUnit(false))),
                new Command("Varázslat", () -> {
                    Game.log("{0}", "var");
                })
        );


        //1. választás: egység és hős akciók
        optionList.setOptions(unitCommands);
        if (!force.hero.hasActedThisTurn)
            optionList.addAll(heroCommands);

        Command command = optionList.getUserChoice(String.format(" %s következik:", unit));
        command.execute();

        // ha egység opciót választottunk, vége a körnek
        if (unitCommands.contains(command)) return;

        // ha hős opciót választottunk, még választhatunk egy egység opciót
        optionList.setOptions(unitCommands);

        optionList.getUserChoice(String.format("%s köre folytatódik:", unit)).execute();


    }

    public void newTurn() {
        force.hero.beginTurn();
        for (Unit unit : force.units) {
            unit.beginTurn();
        }
    }

    @Override
    public String toString() {
        return ColorHelpers.surroundWithColor(" " + getPlayerName() + " ", force.getTeamColor());
    }

    public UserController(String playerName, Rect rect) {
        super(rect);
        this.playerName = playerName;
    }
}
