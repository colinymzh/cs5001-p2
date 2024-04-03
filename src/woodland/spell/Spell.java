package woodland.spell;


/**
 * Enumerates the various spells available in the game, each associated with a short value and a detailed description.
 */
public enum Spell {
    DETECT("Detect", "The detect spell allows the animal to detect the mythical creatures on the adjacent squares."),
    HEAL("Heal", ": The heal spell allows the animal to heal 10 life points."),
    SHIELD("Shield", ": The shield spell allows the animal to block a mythical creature attack for that turn."),
    CONFUSE("Confuse", "The confuse spell allows the animal to confuse a mythical creature on a square adjacent\n" +
            "to the animal but not the square the animal is occupying. The mythical creature will not attack any\n" +
            "animal for the next turn."),
    CHARM("Charm", "The charm spell allows the animal to charm a mythical creature on a square adjacent to\n" +
            "the animal but not the square the animal is occupying. The mythical creature will not attack the\n" +
            "charming animal for the next three turns.");

    /** The short identifier or name of the spell. */
    private final String value;

    /** A detailed description of the spell. */
    private final String description;

    /**
     * Constructs a spell with a given value and description.
     *
     * @param value       The short identifier or name of the spell.
     * @param description A detailed description of the spell.
     */
    Spell(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Returns the short identifier or name of the spell.
     *
     * @return The value associated with the spell.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns a detailed description of the spell.
     *
     * @return The description of the spell.
     */
    public String getDescription() {
        return description;
    }
}
