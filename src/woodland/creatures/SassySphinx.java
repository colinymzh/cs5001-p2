package woodland.creatures;

/**
 * Represents a SassySphinx, a specialized creature in the woodland game.
 */

public class SassySphinx extends Creature {

    /**
     * Constructs a SassySphinx with the specified name and attack value.
     * Initializes unique characteristics of the SassySphinx.
     *
     * @param name        the name of the SassySphinx
     * @param attackValue the attack value of the SassySphinx
     */
    public SassySphinx(String name, int attackValue) {
        super(name, attackValue);
        shortName = "SS";
        description = "The SS is a sphinx that is very sassy. The sphinx is very good at giving sarcastic answers to questions.";
    }
}
