package Spells;

import Field.Tile;
import Players.Hero;
import Store.Purchasable;

/**
 * Absztrakt varázslás osztály. Egy varázslatnak meg kell valósítania a isValidTarget metódust, amely megadja,
 * hogy egy adott cella érvényes célpontja-e a varázslatnak, illetve a
 * use() metódust, amely meghatározza, mi történik, amikor a várazslatot használjuk
 */
public abstract class Spell implements Purchasable {
    final int price;
    final int manaCost;
    final String name;
    final String description;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public int getManaCost() {
        return manaCost;
    }

    public Spell(int price, int manaCost, String name, String description) {
        this.price = price;
        this.manaCost = manaCost;
        this.name = name;
        this.description = description;
    }

    public abstract boolean isValidTarget(Tile target, Hero caster);

    public abstract void use(Hero caster);

    @Override
    public String toString() {
        return String.valueOf(this.name);
    }
}
