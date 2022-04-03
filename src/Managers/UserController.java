package Managers;

import Display.Display;
import Field.Tile;
import Interface.Border;
import Interface.Command;
import Interface.OptionList;
import Interface.View;
import Players.Force;
import Units.Unit;
import Utils.ColorHelpers;
import Utils.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;


/**
 * Játékos által irányított résztvevő vezérlése. Minden körben utasítást kér a felhasználótól.
 */
public class UserControl extends View {

    Rect rect = new Rect(top + 2, left, bottom, right);

    Force force;

    public Force getForce() {
        if (force == null) throw new RuntimeException("UserControl nem kapott Forcet!");
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    //    public Unit pickUnit(boolean friendly){
//
//    }
//
//    public Unit pickEnemyUnit(){
//        return pickUnit(false);
//    }
//
//    public Unit pickFriendlyUnit(){
//        return pickUnit(true);
//    }
//
    public Tile pickTile(Function<Tile, String> requirements) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Display.clearToEndOfLine(top + 1, left, bottom - 2);
                Display.write("Cél [A0 - L9]: ", top + 3, left + 3);
                String next = scanner.next();
                Tile t = Game.field.getTile(next);
                if (t != null){
                    String req = requirements.apply(t);
                    if(Objects.equals(req, "")) return t;
                    Game.logError(req);
                }

            }catch (Exception e){
                Game.logError("Érvénytelen bemenet!");
            }

        }
    }

    public Tile pickTile() {
        return pickTile(t -> "");
    }

    public Unit pickUnit(boolean friendly) {
        OptionList<Unit> unitOptions = new OptionList<>(rect,
                Game.getAllUnits().stream().filter(u -> u.force.equals(getForce()) == friendly).toList());

        return unitOptions.getUserChoice();
    }


    /**
     * Adott egység következő cselekedetének megválasztása
     *
     * @param unit A soron következő egység
     */
    public void nextMove(Unit unit) {

        unit.beginTurn();
        unit.force.hero.beginTurn();

        Display.clearToEndOfLine(top, left);
        Display.write(String.format(" %s következik:", unit), top, left);


        final OptionList<Command> optionList = new OptionList<Command>(rect);


        List<Command> unitCommands = List.of(
                new Command("Mozgás", () -> {
                    unit.moveTo(pickTile(t -> {
                        if(t.hasUnit() && t.unit == unit) return "Már ott van az egység!";
                        if(t.hasUnit()) return "Üres cellát kell választanod!";
                        return "";
                    }));
                    Game.clearErrors();
                }),
                new Command("Támadás", () -> {
                    unit.attack(pickUnit(false));
                }),
                new Command("Kimarad\n", () -> {
                    Game.log("{0}", "smi");
                })
        );

        List<Command> heroCommands = List.of(
                new Command("Hős támadás", () -> {
                    force.hero.attack(pickUnit(false));
                }),
                new Command("Varázslat", () -> {
                    Game.log("{0}", "var");
                })
        );

        optionList.setOptions(unitCommands);
        optionList.addAll(heroCommands);

        Command command = optionList.getUserChoice();
        command.execute();

        if (unitCommands.contains(command)) return;

        optionList.setOptions(unitCommands);

        optionList.getUserChoice().execute();


    }

    public UserControl(Rect rect) {
        super(rect);
    }
}
