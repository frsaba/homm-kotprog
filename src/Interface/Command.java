package Interface;

import Managers.Game;

import java.util.function.Consumer;
import java.util.function.Function;

public class  Command {
    String commandName;
    Runnable command;

    public void execute(){
        command.run();
    }

    public Command(String commandName, Runnable command) {
        this.commandName = commandName;
        this.command = command;
    }

    @Override
    public String toString() {
        return commandName;
    }
}
