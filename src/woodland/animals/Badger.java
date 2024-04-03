package woodland.animals;

/**
 * Represents a Badger in the woodland game.
 */
public class Badger extends Animal {

    /**
     * Constructs a Badger with a specified name and a default description.
     *
     * @param name the name of the badger
     */
    public Badger(String name) {
        super(name);
        description = "The badger has a black and white face. The badger is a often mistaken for a very small panda. The badger wears a t-shirt that says \"I am not a panda\" to combat this.";
    }

    /**
     * Overrides the move method for the Badger. The Badger can move one or dig two squares
     * in any direction.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the new row position
     * @param newCol the new column position
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        boolean singleMove = (Math.abs(oldRow - newRow) == 1 && Math.abs(oldCol - newCol) <= 1)
                || (Math.abs(oldCol - newCol) == 1 && Math.abs(oldRow - newRow) <= 1);

        boolean doubleMove = Math.abs(oldRow - newRow) == 2 || Math.abs(oldCol - newCol) == 2;

        return singleMove || doubleMove;
    }
}

