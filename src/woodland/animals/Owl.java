package woodland.animals;

/**
 * Represents an Owl in the woodland game. The Owl is a specialized Animal that
 * can move in its unique way, including diagonal moves, on the game board.
 */
public class Owl extends Animal {

    /**
     * Constructs an Owl with a specified name and a default description.
     *
     * @param name the name of the owl
     */
    public Owl(String name) {
        super(name);
        description = "The owl has wings. The owl has prescription contact lenses but cannot put them on.";
    }

    /**
     * Overrides the move method for the Owl. An Owl can move horizontally, vertically,
     * and diagonally on the game board.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the new row position
     * @param newCol the new column position
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        // Check for horizontal moves
        boolean horizontalMove = oldRow == newRow;

        // Check for vertical moves
        boolean verticalMove = oldCol == newCol;

        // Check for diagonal moves
        boolean diagonalMove = Math.abs(oldRow - newRow) == Math.abs(oldCol - newCol);

        return horizontalMove || verticalMove || diagonalMove;
    }
}
