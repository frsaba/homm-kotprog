package Units.Types;

import Managers.Game;
import Units.Unit;
import Units.UnitProperties;
import Utils.ColorHelpers;
import Utils.GameConstants;

import java.awt.*;


/**
 * Íjász. Egy speciális egység, amely távolsági támadásra képes.
 */
public class Archer extends Unit {

    public Archer(){
        props = new UnitProperties("Íjász","Távolsági támadás",6, 2, 4, 4 ,4, 9);
    }

    @Override
    public boolean isInRange(Unit other) {
        //Csak üres cellák vagy barátságos egységek vannak körülötte
        return occupiedTile.getNeighbors().stream().allMatch(tile -> !tile.hasUnit() || tile.unit.force == this.force);
    }

    @Override
    public int attack(Unit target) {
        if(Game.getRandomDouble() > GameConstants.BASE_CRIT_CHANCE * force.hero.getLuckMultiplier()){
            Game.log("Kritikus támadás:");
            return criticalAttack(target);
        }

        return this.attack(target, 1, false); //Távolsági támadás, nem lehet visszatámadni
    }

    public Color getTeamColor() {
        return ColorHelpers.applyFilter(super.getTeamColor(), Color.getHSBColor(0.55f,0.5f,0.45f));
    }
}
