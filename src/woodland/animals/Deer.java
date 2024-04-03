package woodland.animals;

import woodland.abilities.LongJumpable;
import woodland.game.Game;
import woodland.game.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Deer in the woodland game. The Deer is a specialized Animal that
 * can move in unique ways, including long jumps, on the game board.
 */
public class Deer extends Animal implements LongJumpable {

    /**
     * Constructs a Deer with a specified name and a default description.
     *
     * @param name the name of the deer
     */
    public Deer(String name) {
        super(name);
        description = "The deer has antlers. The deer is recently divorced and is looking for a new partner.";
    }

    /**
     * Overrides the move method for the Deer. A Deer can move like any other Animal,
     * but also has the ability to move diagonally and jump longer distances.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the new row position
     * @param newCol the new column position
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        return super.move(oldRow, oldCol, newRow, newCol)
                || diagonalMove(oldRow, oldCol, newRow, newCol)
                || jump(oldRow, oldCol, newRow, newCol);
    }

    /**
     * Checks if the move is a valid diagonal move.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the new row position
     * @param newCol the new column position
     * @return true if it's a diagonal move, false otherwise
     */
    private boolean diagonalMove(int oldRow, int oldCol, int newRow, int newCol) {
        return Math.abs(oldRow - newRow) == 1 && Math.abs(oldCol - newCol) == 1;
    }

    /**
     * Implements the jump method for the Deer as per the LongJumpable interface.
     * A Deer can jump horizontally, vertically, or diagonally by either 2 or 3 squares.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the new row position
     * @param newCol the new column position
     * @return true if the jump is valid, false otherwise
     */
    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        boolean horizontalOrVerticalJump =
                (oldRow == newRow && (Math.abs(oldCol - newCol) == 2 || Math.abs(oldCol - newCol) == 3))
                        || (oldCol == newCol && (Math.abs(oldRow - newRow) == 2 || Math.abs(oldRow - newRow) == 3));

        boolean diagonalJump =
                (Math.abs(oldRow - newRow) == 2 && Math.abs(oldCol - newCol) == 2)
                        || (Math.abs(oldRow - newRow) == 3 && Math.abs(oldCol - newCol) == 3);

        return horizontalOrVerticalJump || diagonalJump;
    }

    /**
     * Calculates the jump path for the Deer, to show the creatures on the path.
     * A Deer can jump horizontally, vertically, or diagonally by either 2 or 3 squares.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the new row position
     * @param newCol the new column position
     * @return List of squares in the jump path or null if the jump is invalid
     */
    public List<Square> jumpPath(int oldRow, int oldCol, int newRow, int newCol) {
        List<Square> path = new ArrayList<>();

        if (!jump(oldRow, oldCol, newRow, newCol)) {
            return null;  // Invalid jump
        }

        int directionRow = oldRow == newRow ? 0 : (newRow - oldRow) / Math.abs(newRow - oldRow);
        int directionCol = oldCol == newCol ? 0 : (newCol - oldCol) / Math.abs(newCol - oldCol);

        int tempRow = oldRow;
        int tempCol = oldCol;

        while (tempRow != newRow || tempCol != newCol) {
            tempRow += directionRow;
            tempCol += directionCol;
            path.add(Game.getSquare(tempRow, tempCol));
        }

        return path;
    }
}
