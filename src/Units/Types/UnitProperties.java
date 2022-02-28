package Units.Types;

public record UnitProperties(
    String name,
    String description,
    int price,
    int minDamage,
    int maxDamage,
    int health,
    int speed,
    int initiative
){}

