package Interface;


/**
 * Parancs osztály. Egy elnevezett eljárás, menüpontokban való használatra
 */
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
