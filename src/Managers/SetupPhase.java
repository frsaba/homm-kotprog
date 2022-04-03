package Managers;

import Display.Display;
import Interface.OptionList;
import Units.*;
import Units.Types.*;
import Utils.Rect;

import java.util.*;

/**
 * Előkészületi fázis
 */
public class SetupPhase {
    private int startingGold;

    public int getStartingGold() {
        return startingGold;
    }

    Unit[] purchasableUnits = {new Griff(), new Peasant(), new Archer()};

    public void pickDifficultyLevel() {

        Display.clear();
        Display.write("Válassz nehézségi szintet:", 3, 2);

        Map<String, Integer> difficultyLevels = new LinkedHashMap<>();
        difficultyLevels.put("Könnyű ", 1300);
        difficultyLevels.put("Közepes", 1000);
        difficultyLevels.put("Nehéz  ", 700);

        var difficultyOptions = new OptionList<>(new Rect(5, 2, 12, 60),
                difficultyLevels.entrySet(),
                (level) -> String.format("%s (%4d arany)", level.getKey(), level.getValue()));

        startingGold = difficultyOptions.getUserChoice().getValue();


    }

}
