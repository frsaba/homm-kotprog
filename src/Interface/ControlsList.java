package Interface;

import Managers.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControlsList extends View implements Drawable{

    List<Command> commands;

    @Override
    public void draw(int top, int left) {
        super.draw(top, left);
    }

    public ControlsList(int top, int left, int bottom, int right, List<Command> commands) {
        super(top, left, bottom, right);
        this.commands = commands;
    }

    public void getNextCommand(String prompt){
        Scanner scanner = new Scanner(System.in);
        Game.log(prompt);
        while (true){
            int choice = scanner.nextInt();
            if(choice >= 0 && choice < commands.size() ){
                //commands.get(choice).execute(t);
                return;
            }
            Game.logError("Érvénytelen bemenet!");
        }
    }
}
