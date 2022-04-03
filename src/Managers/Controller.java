package Managers;

import Players.Force;
import Units.Unit;

/**
 * Egy játékfél irányítás interfésze. Ezeket a metódusokat kell megvalósítani a user input, illetve AI alapú vezérlőknek
 */
public interface Controller {
    void nextMove(Unit unit);

    Unit pickUnit(boolean friendly);

    Force getForce();

    void setForce(Force force);

    String getPlayerName();

    void newTurn();
}
