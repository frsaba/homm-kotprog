package Units;


/**
 * Egy egység alapvető tulajdonságait egységbezáró rekord osztály.
 */
public record UnitProperties(
    String name,
    String special,
    int price,
    int minDamage,
    int maxDamage,
    int health,
    int speed,
    int initiative
){}

