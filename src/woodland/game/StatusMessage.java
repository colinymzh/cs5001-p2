package woodland.game;


/**
 * Represents the various status messages that can occur during a game session.
 */
public enum StatusMessage {
    MOVE_SUCCESSFUL("Move successful", "The last move was successful."),
    MOVE_INTERRUPTED("Move interrupted", "The last move was interrupted by a creature."),
    MOVE_INVALID("Move invalid", "The last move was invalid."),
    SPELL_SUCCESSFUL("Spell successful", "The last spell was successful."),
    SPELL_INVALID("Spell invalid", "The last spell was invalid."),
    GAME_WIN("Game win", "You have won the game."),
    GAME_LOST("Game lost", "You have lost the game.");

    /** The short version of the status message. */
    private final String value;

    /** A detailed description of the status message. */
    private final String description;

    /**
     * Constructs a status message with a given value and description.
     *
     * @param value The short version of the status message.
     * @param description A detailed description of the status message.
     */
    StatusMessage(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Returns the value of the status message.
     *
     * @return A short version of the status message.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the detailed description of the status message.
     *
     * @return A detailed description of the status message.
     */
    public String getDescription() {
        return description;
    }
}
