package woodland.game;

import woodland.spell.Spell;
import woodland.animals.Animal;
import woodland.creatures.Creature;

/**
 * Represents a square in the game grid.
 * A square may contain a creature, an animal, or a spell.
 */
public class Square {

    private final int row;
    private final int col;
    private boolean visible;

    private Creature creature;
    private Animal animal;
    private Spell spell;

    /**
     * Constructs a square with the specified row and column.
     *
     * @param row The row position of the square.
     * @param col The column position of the square.
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getter methods

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVisible() {
        return visible;
    }

    public Creature getCreature() {
        return creature;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Spell getSpell() {
        return spell;
    }

    // Checks if the square contains specific entities

    public boolean hasCreature() {
        return creature != null;
    }

    public boolean hasAnimal() {
        return animal != null;
    }

    public boolean hasSpell() {
        return spell != null;
    }

    // Setter methods

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Sets the creature for the square.
     *
     * @param creature The creature to be set.
     */
    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    /**
     * Sets the animal for the square and updates the animal's square reference.
     *
     * @param animal The animal to be set.
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;
        if (animal != null) {
            animal.setSquare(this);
        }
    }

    /**
     * Sets the spell for the square.
     *
     * @param spell The spell to be set.
     */
    public void setSpell(Spell spell) {
        this.spell = spell;
    }
}
