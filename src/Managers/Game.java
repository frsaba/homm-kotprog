package Managers;

import Display.Display;
import Field.Field;
import Interface.MessageBox;
import Players.Force;
import Units.Unit;
import Utils.ColorHelpers;
import Utils.Colors;
import Utils.Rect;

import java.awt.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

import static Utils.Sleep.sleep;


/**
 * Játékmenet vezérlő osztály
 */
public class Game {

    //region View setup

    public static final int FIELD_TOP = 3;
    public static final int FIELD_LEFT = 2;

    static MessageBox eventLog = new MessageBox(23, 2, 28, 119);
    static TurnManager turnManager = new TurnManager(new Rect(4, 12 * 5 + 3, 8, 119));
    static UserController userController = new UserController("1. játékos", new Rect(10, 12 * 5 + 3, 19, 119));
    static MessageBox statusbar = new MessageBox(1, 1, 1, 119);
    static MessageBox errorLog = new MessageBox(20, 12 * 5 + 3, 23, 119);

    //endregion

    public static Controller player1;
    public static Controller player2;

    public static void redrawField() {
        field.draw(FIELD_TOP, FIELD_LEFT);
    }

    public static void drawHeader() {
        setStatus("{0}. kör -- {1} ({2} mana) vs {3} ({4} mana)",
                turnManager.getTurn(),
                player1, player1.getForce().hero.getMana(),
                player2, player2.getForce().hero.getMana());
    }

    public static List<Unit> getAllUnits() {
        return turnManager.units;
    }

    public static void unitDied(Unit unit) {
        turnManager.removeUnit(unit);
        unit.getOccupiedTile().draw();
    }

//    static ControlsList controls = new ControlsList(31,2, 40, 120, new ArrayList<>());

    public static Random random = new Random();

    public static double getRandomDouble() {
        return random.nextDouble();
    }

    //region Logging

    public static void log(String message) {
        eventLog.addText(message);
    }

    public static void log(String format, Object... args) {
        log(MessageFormat.format(format, args));
    }

    public static void logError(String message) {
        errorLog.setText(ColorHelpers.surroundWithColor(message, Color.red, true));
    }

    public static void logError(String format, Object... args) {
        logError(MessageFormat.format(format, args));
    }

    public static void clearErrors() {
        errorLog.setText("");
        errorLog.draw();
    }

    public static void setStatus(String message) {
        statusbar.setText(message);
    }

    public static void setStatus(String format, Object... args) {
        statusbar.setText(MessageFormat.format(format, args));
    }

    //endregion

    public static Field field = new Field(10, 12);

    /**
     * Seregszemle. Felsorolja a két játékfél hősének tulajdonságait, a megvásárolt varázslatokat és egységeket.
     *
     * @param f1 egyik sereg
     * @param f2 másik sereg
     */
    public static void enumeration(Force f1, Force f2) {
        Display.clear();
        f1.draw(2, 2);
        f2.draw(16, 2);

        Scanner sc = new Scanner(System.in);
        Display.write("Tovább [Enter]: ", 27, 80);

        sc.nextLine();
        Display.clear();
    }

    public static boolean isGameOver() {
        return player1.getForce().hasLost() || player2.getForce().hasLost();
    }


    public static void main(String[] args) {


        Display.clear();
        Display.resetStyling();

        //region Setup Phase

        Setup setup = new Setup();
        setup.pickDifficultyLevel();


        player1 = userController;
        player2 = setup.getSecondPlayer();


        Force force1 = new Force(userController, setup.getStartingGold(), Colors.blueTeamAccent);
        Force force2 = new Force(player2,
                player2 instanceof UserController ? setup.getStartingGold() : 1000,
                Colors.redTeamAccent);

        player1.assembleArmy();
        player2.assembleArmy();

//        field.getTile("A5").select();


        //region Placeholder units

//        Griff griff = new Griff();
//        griff.setAmount(1);
//        force1.addUnit(griff);
//        griff.moveTo(field.getTile("D2"));

        // Griff griff2 = new Griff();
        // griff2.setAmount(5);
        // force2.addUnit(griff2);
        // griff2.moveTo(field.getTile("E3"));

//        Peasant peasant = new Peasant();
//        peasant.setAmount(5);
//        force1.addUnit(peasant);
//        peasant.moveTo(field.getTile("C5"));
//
//        Thorn tuskes = new Thorn();
//        tuskes.setAmount(200);
//        force1.addUnit(tuskes);
//        tuskes.moveTo(field.getTile("H3"));
// //
//        Archer archer = new Archer();
//        archer.setAmount(5);
//        force2.addUnit(archer);
//        archer.moveTo(field.getTile("H1"));

        //endregion

        Game.enumeration(force1, force2);

        redrawField();

        player1.placeUnits(true);
        player2.placeUnits(false);

        redrawField();


        turnManager.setUnits(
                Stream.concat(force1.units.stream(), force2.units.stream()).toList());
        turnManager.draw();


        //endregion


        //region Action phase

        while (!isGameOver()) {
            drawHeader();
            if (turnManager.isNewTurn()) {
                Game.log("-".repeat(12) + " {0}. kör " + "-".repeat(12), turnManager.getTurn());

                player1.newTurn();
                player2.newTurn();
            } else {
                Game.log("-".repeat(24));
            }


            Unit unit = turnManager.getCurrentUnit();
            unit.force.nextMove(unit);

            if (isGameOver()) break;

            turnManager.nextUnit();

            redrawField();

        }

        //endregion

        sleep(2500);
        Display.clear();

        eventLog.setText("");

        Game.log("Játék vége!");

        if (force1.hasLost() && force2.hasLost()) {
            Game.log("Döntetlen lett!");
        } else if (force2.hasLost()) {
            Game.log("{0} nyert!", player1);
        } else {
            Game.log("{0} nyert!", player2);
        }

        System.out.println();


    }
}
