package Managers;

import Field.Tile;
import Players.Force;
import Units.Unit;

import java.util.function.Function;

/**
 * Egy játékfél irányítás interfésze. Ezeket a metódusokat kell megvalósítani a user input, illetve AI alapú vezérlőknek
 */
public interface Controller {
    void nextMove(Unit unit);

    Unit pickUnit(boolean friendly);

    Unit pickUnit(boolean friendly, String prompt);

    Unit pickUnit(Function<Unit, Boolean> filterFunction, String prompt);

    Tile pickTile(String s);


    Force getForce();

    void setForce(Force force);

    String getPlayerName();

    void newTurn();

    void placeUnits(boolean leftSide);

    void assembleArmy();
}
