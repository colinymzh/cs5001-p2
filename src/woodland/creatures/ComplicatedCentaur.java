package woodland.creatures;

/**
 * Represents a ComplicatedCentaur, a specialized creature in the woodland game.
 */
public class ComplicatedCentaur extends Creature {

    /**
     * Constructs a ComplicatedCentaur with a specified name and attack value.
     * Additionally, the short name is initialized to "CC", and a unique description
     * encapsulating the centaur's complex feelings is provided.
     *
     * @param name        the name of the ComplicatedCentaur
     * @param attackValue the attack value of the ComplicatedCentaur
     */
    public ComplicatedCentaur(String name, int attackValue) {
        super(name, attackValue);
        shortName = "CC";
        description="The CC is a centaur that has mixed feeling about its love interest, a horse. The centaur is unsure whether they can love them fully.";
    }
}
