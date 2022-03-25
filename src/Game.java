import Display.Display;
import Field.Field;
import Interface.MessageBox;
import Players.*;
import Units.Types.*;
import java.awt.*;

public class Game {

    static MessageBox messageBox = new MessageBox(10,10, 15, 65);

    public static void main(String[] args) {


        Display.clear();
        Display.setColor(Color.ORANGE, Color.black);

        messageBox.setText("r sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore");

//        Force player1 = new Force(new Hero(), 1500);
//        Force player2 = new Force(new Hero(), 1500);
//
//        Peasant peasant = new Peasant();
//        peasant.setAmount(10);
//        player1.addUnit(peasant);
//
//        Griff griff = new Griff();
//        griff.setAmount(3);
//        player2.addUnit(griff);
//
//        System.out.println(peasant);
//
//        System.out.println("Tamadas!");
//        peasant.attack(griff);
//
//        System.out.println(peasant);
//        System.out.println(griff);
//
//        Field field = new Field();
//
//        griff.moveTo(field.getTile(2,0));
//        peasant.moveTo(field.getTile(0,1));
//
//        for (int row = 0; row < 5; row++) {
//            for (int col = 0; col < 3; col++) {
//                System.out.println(field.getTile(row,col));
//            }
//        }

        Display.setCursorPosition(45,10);

    }
}
