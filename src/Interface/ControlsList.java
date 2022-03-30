package Interface;

import Managers.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class ControlsList extends View implements KeyListener {

    List<Command> commands;

    @Override
    public void draw(int top, int left) {
        super.draw(top, left);
    }

    public ControlsList(int top, int left, int bottom, int right, List<Command> commands) {
        super(top, left, bottom, right);
        this.commands = commands;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        Game.log("keyTyped: {0} {1}",e.getKeyCode() ,e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Game.log("keyPressed: {0} {1}",e.getKeyCode() ,e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Game.log("keyReleased: {0} {1}",e.getKeyCode() ,e);
    }
}
