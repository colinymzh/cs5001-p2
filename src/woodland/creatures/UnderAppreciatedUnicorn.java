package woodland.creatures;

/**
 * Represents a UnderAppreciatedUnicorn, a specialized creature in the woodland game.
 */

public class UnderAppreciatedUnicorn extends Creature {

    /**
     * Constructs a UnderAppreciatedUnicorn with the specified name and attack value.
     * Initializes unique characteristics of the UnderAppreciatedUnicorn.
     *
     * @param name        the name of the UnderAppreciatedUnicorn
     * @param attackValue the attack value of the UnderAppreciatedUnicorn
     */
    public UnderAppreciatedUnicorn(String name, int attackValue) {
        super(name, attackValue);
        shortName = "UU";
        description = "The UU is a unicorn that is under-appreciated by the other mythical creatures because it is often mistaken for a horse with a horn.";
    }
}
