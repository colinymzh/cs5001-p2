package woodland.animals;

import woodland.abilities.ShortJumpable;

/**
 * Represents a Rabbit in the woodland game.
 */
public class Rabbit extends Animal implements ShortJumpable {

    /**
     * Constructs a Rabbit with a specified name and a default description.
     *
     * @param name the name of the rabbit
     */
    public Rabbit(String name) {
        super(name);
        description = "The rabbit has fluffy ears and tail. The rabbit really likes to eat grass.";
    }

    /**
     * Determines if the Rabbit can move to a specified position.
     * The Rabbit can move diagonally, make short jumps, or use the standard animal move.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the desired row position
     * @param newCol the desired column position
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        return diagonalMove(oldRow, oldCol, newRow, newCol)
                || jump(oldRow, oldCol, newRow, newCol)
                || super.move(oldRow, oldCol, newRow, newCol);
    }

    /**
     * Checks if the desired move is a diagonal move.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the desired row position
     * @param newCol the desired column position
     * @return true if the move is diagonal, false otherwise
     */
    private boolean diagonalMove(int oldRow, int oldCol, int newRow, int newCol) {
        return Math.abs(oldRow - newRow) == 1 && Math.abs(oldCol - newCol) == 1;
    }

    /**
     * Determines if the Rabbit can make a short jump to a specified position.
     * The Rabbit can jump two squares horizontally, vertically, or diagonally.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the desired row position
     * @param newCol the desired column position
     * @return true if the jump is valid, false otherwise
     */
    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        boolean horizontalOrVerticalJump = (oldRow == newRow && Math.abs(oldCol - newCol) == 2)
                || (oldCol == newCol && Math.abs(oldRow - newRow) == 2);

        boolean diagonalJump = Math.abs(oldRow - newRow) == 2 && Math.abs(oldCol - newCol) == 2;

        return horizontalOrVerticalJump || diagonalJump;
    }
}
