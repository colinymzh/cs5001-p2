package woodland.creatures;

import woodland.animals.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a generic Creature within the woodland ecosystem.
 */
public class Creature {
    protected String name;
    protected int attackValue;
    protected Map<Animal, Integer> charmAnimal; // Maps animals to charm duration (in turns)
    protected boolean confused;
    protected String shortName;
    protected String description;
    protected List<Animal> shieldAnimal; // List of animals that creature cannot harm

    // Getter and setter methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public Map<Animal, Integer> getCharmAnimal() {
        return charmAnimal;
    }

    public void setCharmAnimal(Map<Animal, Integer> charmAnimal) {
        this.charmAnimal = charmAnimal;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Animal> getShieldAnimal() {
        return shieldAnimal;
    }

    public void setShieldAnimal(List<Animal> shieldAnimal) {
        this.shieldAnimal = shieldAnimal;
    }

    /**
     * Constructs a Creature with the specified name and attack value.
     * Initializes charm and shield lists/collections.
     *
     * @param name        the name of the creature
     * @param attackValue the attack value of the creature
     */
    public Creature(String name, int attackValue) {
        this.name = name;
        this.attackValue = attackValue;
        charmAnimal = new HashMap<>();
        shieldAnimal = new ArrayList<>();
    }

    /**
     * Adds an animal to the shield list, protecting the creature from it.
     *
     * @param animal the animal to shield from
     */
    public void addShieldAnimal(Animal animal) {
        shieldAnimal.add(animal);
    }

    /**
     * Charms an animal for 3 turns.
     *
     * @param animal the animal to charm
     */
    public void addCharmAnimal(Animal animal) {
        charmAnimal.put(animal, 3);
    }

    public void setConfused(boolean confused) {
        this.confused = confused;
    }

    public boolean isCharmed(Animal animal) {
        return charmAnimal.containsKey(animal);
    }

    public boolean isConfused() {
        return confused;
    }

    public boolean isShieldAnimal(Animal animal) {
        return shieldAnimal.contains(animal);
    }

    /**
     * Updates the charm duration for an animal. If the duration reaches zero, the animal is no longer charmed.
     *
     * @param animal the charmed animal to update
     */
    public void updateCharmAnimal(Animal animal) {
        Integer turn = charmAnimal.get(animal) - 1;
        if (turn == 0) {
            charmAnimal.remove(animal);
        } else {
            charmAnimal.put(animal, turn);
        }
    }

    /**
     * Removes an animal from the shield list.
     *
     * @param animal the animal to remove from the shield
     */
    public void updateShieldAnimal(Animal animal) {
        shieldAnimal.remove(animal);
    }
}
