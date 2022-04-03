package Interface;

import Managers.Game;

import java.util.function.Consumer;

public class  Command<T> {
    char keyCode;
    Consumer<T> command;

    public void execute(T t){
        command.accept(t);
    }

    public Command(Consumer<T> command) {
        this.command = command;
    }
}
