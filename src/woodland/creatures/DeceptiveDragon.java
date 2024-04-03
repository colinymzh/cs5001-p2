package woodland.creatures;

/**
 * Represents a DeceptiveDragon within the woodland ecosystem.
 */
public class DeceptiveDragon extends Creature {

    /**
     * Constructs a DeceptiveDragon with the specified name and attack value.
     * Initializes unique characteristics of the DeceptiveDragon.
     *
     * @param name        the name of the DeceptiveDragon
     * @param attackValue the attack value of the DeceptiveDragon
     */
    public DeceptiveDragon(String name, int attackValue) {
        super(name, attackValue);
        shortName = "DD";
        description = "The DD is a dragon that practices social engineering. The dragon is very good at sending phishing emails pretending to be a prince.";
    }
}
