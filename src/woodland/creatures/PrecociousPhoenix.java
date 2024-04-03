package woodland.creatures;

/**
 * Represents a PrecociousPhoenix, a specialized creature in the woodland game.
 */

public class PrecociousPhoenix extends Creature {

    /**
     * Constructs a PrecociousPhoenix with the specified name and attack value.
     * Initializes unique characteristics of the PrecociousPhoenix.
     *
     * @param name        the name of the PrecociousPhoenix
     * @param attackValue the attack value of the PrecociousPhoenix
     */
    public PrecociousPhoenix(String name, int attackValue) {
        super(name, attackValue);
        shortName = "PP";
        description = "The PP is a phoenix that is very precocious. The phoenix understands the meaning of life and the universe.";
    }
}
