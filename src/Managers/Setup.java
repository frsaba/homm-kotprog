package Managers;

import Display.Display;
import Interface.Menu;
import Units.*;
import Units.Types.*;
import Utils.Rect;

import java.util.*;

/**
 * Játék beállítások megválasztása: résztvevők, nehézségi szint
 */
public class Setup {
    private int startingGold;

    public int getStartingGold() {
        return startingGold;
    }

    Unit[] purchasableUnits = {new Griff(), new Peasant(), new Archer()};

    public void pickDifficultyLevel() {

        Display.clear();

        Map<String, Integer> difficultyLevels = new LinkedHashMap<>();
        difficultyLevels.put("Könnyű ", 1300);
        difficultyLevels.put("Közepes", 1000);
        difficultyLevels.put("Nehéz  ", 700);

        var difficultyOptions = new Menu<>(new Rect(3, 2, 12, 60),
                difficultyLevels.entrySet(),
                (level) -> String.format("%s (%4d arany)", level.getKey(), level.getValue()));

        startingGold = difficultyOptions.getUserChoice("Válassz nehézségi szintet:").getValue();


    }

    public Controller getSecondPlayer(){
        Display.clear();

        Controller cPlayer2= new UserController("2. játékos", Game.userController.rect);
        Controller cAI = new UserController("Géép", Game.userController.rect);

        Display.write("Játékmód:", 3, 2);

        Map<String, Controller> gameModes = Map.of(
                "Ember vs Ember", cPlayer2,
                "Ember vs Gép",  cAI);


                var options = new Menu<>(new Rect(3, 2, 12, 60), gameModes.entrySet(), Map.Entry::getKey);

                return options.getUserChoice("Válassz játékmódot:").getValue();

    }

}
