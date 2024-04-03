package woodland.animals;

import woodland.spell.Spell;
import woodland.game.Square;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an animal in the woodland game. An animal has a name, spells, life points,
 * a description, and a position on the game board represented by a Square object.
 */
public class Animal {

    // Instance variables
    protected String name;
    protected Map<Spell, Integer> spells = new HashMap<>();
    protected int lifePoints = 100;
    protected String description;
    protected Square square;

    /**
     * Constructs an Animal with a specified name.
     *
     * @param name the name of the animal
     */
    public Animal(String name) {
        this.name = name;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Spell, Integer> getSpells() {
        return spells;
    }

    public void setSpells(Map<Spell, Integer> spells) {
        this.spells = spells;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * Allows the animal to move one square in any direction.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the new row position
     * @param newCol the new column position
     * @return true if the move is valid, false otherwise
     */
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        return (oldRow == newRow && Math.abs(oldCol - newCol) == 1)
                || (oldCol == newCol && Math.abs(oldRow - newRow) == 1);
    }

    /**
     * Heals the animal, increasing its life points.
     */
    public void heal() {
        lifePoints += 10;
    }

    /**
     * Reduces the animal's life points when attacked.
     *
     * @param attackValue the amount of damage taken
     */
    public void attacked(int attackValue) {
        lifePoints -= attackValue;
        if (lifePoints < 0) {
            lifePoints = 0;
        }
    }

    /**
     * Checks if the animal is still alive.
     *
     * @return true if the animal has positive life points, false otherwise
     */
    public boolean isAlive() {
        return lifePoints > 0;
    }

    /**
     * Adds or increments the count of a specified spell for the animal.
     *
     * @param spell the spell to add
     */
    public void addSpell(Spell spell) {
        spells.merge(spell, 1, Integer::sum);
    }

    /**
     * Updates the spell count after using a spell. Removes the spell if its count reaches zero.
     *
     * @param spell the spell to update
     */
    public void updateSpell(Spell spell) {
        if (spell == Spell.HEAL) {
            heal();
        }
        spells.computeIfPresent(spell, (key, val) -> val - 1 == 0 ? null : val - 1);
    }
}
