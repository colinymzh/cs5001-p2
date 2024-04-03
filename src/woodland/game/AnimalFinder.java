package woodland.game;

import static woodland.game.Game.*;

/**
 * Utility class for finding animals within the game's board.
 */
public class AnimalFinder {

    /**
     * Searches for a specific animal by its name on the game's board.
     *
     * @param animal The name of the animal to find.
     * @return The square containing the animal if found, otherwise null.
     */
    public static Square findAnimal(String animal) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                Square currentSquare = getSquare(i, j);
                // Check if the current square has an animal with the given name.
                if (currentSquare.hasAnimal() && currentSquare.getAnimal().getName().equals(animal)) {
                    return currentSquare;
                }
            }
        }
        return null; // Return null if no such animal is found.
    }
}
