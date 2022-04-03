package Managers;

import Display.Display;
import Field.Field;
import Interface.MessageBox;
import Players.Force;
import Spells.Fireball;
import Units.Types.*;
import Utils.Colors;
import Utils.Rect;

import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


/**
 * Játékmenet vezérlő osztály
 */
public class Game {

    static MessageBox eventLog = new MessageBox(22, 2, 29, 119, " Események ");
    static TurnManager turnManager = new TurnManager(new Rect(2, 12 * 5 + 3, 20, 119));

//    static ControlsList controls = new ControlsList(31,2, 40, 120, new ArrayList<>());

    public static Random random = new Random();

    public static double getRandomDouble() {
        return random.nextDouble();
    }

    public static void log(String message) {
        eventLog.addText(message);
    }

    public static void log(String format, Object... args) {
        log(MessageFormat.format(format, args));
    }

    public static void logError(String format, Object... args) {
        log(MessageFormat.format(format, args));
    }

    public static Field field = new Field(10, 12);

    public static void redrawField() {
        field.draw(1, 1);
    }

    public static void main(String[] args) {


        Display.clear();
        Display.resetStyling();

        //region Setup Phase

        SetupPhase setupPhase = new SetupPhase();
//        setupPhase.pickDifficultyLevel();

        Game.log("Kezdőarany: {0}", setupPhase.getStartingGold());

        Force player = new Force(setupPhase.getStartingGold(), Colors.blueTeamAccent);
        Force ai = new Force(1700, Colors.redTeamAccent);

        field.getTile("A5").select();


        Griff griff = new Griff();
        griff.setAmount(50);
        player.addUnit(griff);
        griff.moveTo(field.getTile(2, 3));

        Griff griff2 = new Griff();
        griff2.setAmount(50);
        ai.addUnit(griff2);
        griff2.moveTo(field.getTile(2, 7));

        Peasant peasant = new Peasant();
        peasant.setAmount(700);
        player.addUnit(peasant);
        peasant.moveTo(field.getTile(2, 4));

        Thorn tuskes = new Thorn();
        tuskes.setAmount(200);
        player.addUnit(tuskes);
        tuskes.moveTo(field.getTile("H3"));

        Archer archer = new Archer();
        archer.setAmount(100);
        ai.addUnit(archer);
        archer.moveTo(field.getTile(2, 6));

        redrawField();

        turnManager.setUnits(
                Stream.concat(player.units.stream(), ai.units.stream()).toList());
        turnManager.draw();

        Fireball fireball = new Fireball();
        player.hero.castSpell(fireball, field.getTile("I2"));

        player.hero.attack(archer);

//        griff.attack(griff2);
        peasant.attack(griff2);
        griff2.attack(peasant);
        griff2.attack(peasant);
        griff2.attack(peasant);
        Game.log("Új kör!");
        peasant.beginTurn();
        archer.attack(tuskes);
        archer.attack(peasant);
//        Display.clear();

//        field.draw(1,1);
        eventLog.draw();
//        log("HALOKA MI VAN It");


//        eventLog.setText("r sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore");

        Display.setCursorPosition(51, 10);

    }
}
