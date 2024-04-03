package woodland.animals;

import woodland.abilities.LongJumpable;

/**
 * Represents a Fox in the woodland game.
 */
public class Fox extends Animal implements LongJumpable {

    /**
     * Constructs a Fox with a specified name and a default description.
     *
     * @param name the name of the fox
     */
    public Fox(String name) {
        super(name);
        description = "The fox has a bushy tail. The fox really enjoys looking at butterflies in the sunlight.";
    }

    /**
     * Overrides the move method for the Fox.
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
                || jump(oldRow, oldCol, newRow, newCol);
    }

    /**
     * Implements the jump method for the Fox as per the LongJumpable interface.
     * A Fox can jump horizontally or vertically by 2 or 3 squares.
     *
     * @param oldRow the original row position
     * @param oldCol the original column position
     * @param newRow the new row position
     * @param newCol the new column position
     * @return true if the jump is valid, false otherwise
     */
    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        return (oldRow == newRow && (Math.abs(oldCol - newCol) == 2 || Math.abs(oldCol - newCol) == 3))
                || (oldCol == newCol && (Math.abs(oldRow - newRow) == 2 || Math.abs(oldRow - newRow) == 3));
    }
}