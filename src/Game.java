import Display.Display;
import Field.Field;
import Interface.FieldView;
import Interface.MessageBox;
import Interface.View;
import Units.Types.Griff;

import java.awt.*;

public class Game {

//    static MessageBox eventLog = new MessageBox(2,2, 3, 3);

    public static void main(String[] args) {


        Display.clear();
        Display.resetStyling();

//        View view = new View(2,2,4,3);
//        view.draw(0,0);


        Field field = new Field(10,12);

        Griff griff = new Griff();
        griff.setAmount(10);
        griff.moveTo(field.getTile(2,3));


        field.draw(2,2);
//        eventLog.draw();

//        eventLog.setText("r sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore");

        Display.setCursorPosition(40,10);

    }
}
