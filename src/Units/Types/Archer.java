package Units.Types;

import Units.Unit;

public class Archer extends Unit {

    public Archer(){
//        super(amount);
        props = new UnitProperties("Íjász","Ne etesd az állatokat!",6, 2, 4, 4 ,4, 9);
//        heal(amount * props.health());
    }

    @Override
    public boolean isInRange(Unit other) {
        //Csak üres cellák vagy barátságos egységek vannak körülötte
        return occupiedTile.neighbors.stream().allMatch(tile -> tile.unit == null || tile.unit.force == this.force);
    }
}
