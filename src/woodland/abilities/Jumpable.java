package woodland.abilities;

/**
 * The Jumpable interface provides the ability for animals to jump on the board.
 */
public interface Jumpable {

    /**
     * Tries to jump an animal from its current position to a new position.
     *
     * @param oldRow The starting row position of the animal.
     * @param oldCol The starting column position of the animal.
     * @param newRow The desired row position to jump to.
     * @param newCol The desired column position to jump to.
     * @return true if the jump is successful, false otherwise.
     */
    boolean jump(int oldRow,int oldCol,int newRow,int newCol);
}
