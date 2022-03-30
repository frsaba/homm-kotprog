package Managers;

import Display.Display;
import Field.Field;
import Interface.ControlsList;
import Interface.MessageBox;
import Players.Force;
import Players.Hero;
import Units.Types.Griff;
import Units.Types.Peasant;
import Utils.Colors;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    static MessageBox eventLog = new MessageBox(1,12*5 + 3, 50, 12*5+75, " Események ");
//    static ControlsList controls = new ControlsList(31,2, 40, 120, new ArrayList<>());

    public static Random random = new Random();
    public static double getRandomDouble(){
        return random.nextDouble();
    }

    public static void log(String message){
        eventLog.addText(message);
    }

    public static void log(String format, Object...args){
        log(MessageFormat.format(format, args));
    }

    public static void logError(String format, Object...args){
        log(MessageFormat.format(format, args));
    }

    public static void main(String[] args) {


        Display.clear();
        Display.resetStyling();

//        View view = new View(2,2,4,3);
//        view.draw(0,0);

        Force player = new Force(new Hero(), 1700, Colors.blueTeamAccent);
        Force ai = new Force(new Hero(), 1700, Colors.redTeamAccent);

        Field field = new Field(10,12);

        Griff griff = new Griff();
        griff.setAmount(50);
        player.addUnit(griff);
        griff.moveTo(field.getTile(2,3));

        Griff griff2 = new Griff();
        griff2.setAmount(50);
        ai.addUnit(griff2);
        griff2.moveTo(field.getTile(2,7));

        Peasant peasant = new Peasant();
        peasant.setAmount(700);
        player.addUnit(peasant);
        peasant.moveTo(field.getTile(2,4));

        Peasant peasant2 = new Peasant();
        peasant2.setAmount(100);
        ai.addUnit(peasant2);
        peasant2.moveTo(field.getTile(2,6));

//        griff.attack(griff2);
        peasant.attack(griff2);
        griff2.attack(peasant);
        griff2.attack(peasant);
        griff2.attack(peasant);
        Game.log("Új kör!");
        peasant.beginTurn();
        peasant2.attack(peasant);
        peasant2.attack(peasant);
        Display.clear();

        field.draw(1,1);
        eventLog.draw();
//        log("HALOKA MI VAN It");


//        eventLog.setText("r sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore");

        Display.setCursorPosition(41,10);

    }
}
