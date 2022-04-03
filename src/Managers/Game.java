package Managers;

import Display.Display;
import Field.Field;
import Interface.MessageBox;
import Players.Force;
import Units.Types.*;
import Units.Unit;
import Utils.ColorHelpers;
import Utils.Colors;
import Utils.Rect;

import java.awt.*;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static Utils.Colors.defaultBackground;


/**
 * Játékmenet vezérlő osztály
 */
public class Game {

    //region View setup

    public static final int FIELD_TOP = 3;
    public static final int FIELD_LEFT = 2;

    static MessageBox eventLog = new MessageBox(23, 2, 28, 119);
    static TurnManager turnManager = new TurnManager(new Rect(4, 12 * 5 + 3, 8, 119));
    static UserController userController = new UserController("1. játékos", new Rect(10, 12 * 5 + 3, 20, 119));
    static MessageBox statusbar = new MessageBox(1, 1, 1, 119);
    static MessageBox errorLog = new MessageBox(20, 12 * 5 + 3, 23, 119);

    //endregion

    public static void redrawField() {
        field.draw(FIELD_TOP, FIELD_LEFT);
    }

    public static List<Unit> getAllUnits() {
        return turnManager.units;
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
        errorLog.setText(ColorHelpers.surroundWithColor(message, Color.red, defaultBackground));
    }

    public static void logError(String format, Object... args) {
        logError(MessageFormat.format(format, args));
    }

    public static void clearErrors() {
        errorLog.setText("");
        errorLog.draw();
    }

    public static void setStatus(String message){
        statusbar.setText(message);
    }

    public static void setStatus(String format, Object... args){
        statusbar.setText(MessageFormat.format(format, args));
    }

    //endregion

    public static Field field = new Field(10, 12);

    public static void main(String[] args) {


        Display.clear();
        Display.resetStyling();

        //region Setup Phase

        SetupPhase setupPhase = new SetupPhase();
//        setupPhase.pickDifficultyLevel();

        Game.log("Kezdőarany: {0}", setupPhase.getStartingGold());


        Controller player1 = userController;
        Controller player2 = new UserController("2. játékos", userController.rect);


        Force force1 = new Force(userController, setupPhase.getStartingGold(), Colors.blueTeamAccent);
        Force force2 = new Force(player2, 1700, Colors.redTeamAccent);

//        field.getTile("A5").select();


        Griff griff = new Griff();
        griff.setAmount(50);
        force1.addUnit(griff);
        griff.moveTo(field.getTile("D2"));

        Griff griff2 = new Griff();
        griff2.setAmount(50);
        force2.addUnit(griff2);
        griff2.moveTo(field.getTile("E3"));

        Peasant peasant = new Peasant();
        peasant.setAmount(250);
        force1.addUnit(peasant);
        peasant.moveTo(field.getTile("C5"));

        Thorn tuskes = new Thorn();
        tuskes.setAmount(200);
        force1.addUnit(tuskes);
        tuskes.moveTo(field.getTile("H3"));

        Archer archer = new Archer();
        archer.setAmount(100);
        force2.addUnit(archer);
        archer.moveTo(field.getTile("H1"));

        redrawField();

        turnManager.setUnits(
                Stream.concat(force1.units.stream(), force2.units.stream()).toList());
        turnManager.draw();

        //endregion

        //region Action phase

        while (true) {
            setStatus("{0}. kör -- {1} ({2} mana) vs {3} ({4} mana)",
                    turnManager.getTurn(),
                    player1, player1.getForce().hero.getMana(),
                    player2, player2.getForce().hero.getMana());



            if(turnManager.isNewTurn()){
                player1.newTurn();
                player2.newTurn();

                Game.log("-".repeat(12) + " {0}. kör " + "-".repeat(12), turnManager.getTurn() );
            }else{
                Game.log("-".repeat(24));
            }

            Unit unit = turnManager.getCurrentUnit();
            unit.force.nextMove(unit);


            turnManager.nextUnit();

            redrawField();

        }

        //endregion


//        Fireball fireball = new Fireball();
//        player.hero.castSpell(fireball, field.getTile("I2"));
//
//        player.hero.attack(archer);
//
////        griff.attack(griff2);
//
//
////        eventLog.setText("r sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore");
//
//        Display.setCursorPosition(51, 10);

    }
}
