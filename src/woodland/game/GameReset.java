package woodland.game;

import woodland.animals.*;
import woodland.creatures.*;
import woodland.spell.Spell;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


/**
 * The class provides functionality to reset the game state.
 */
public class GameReset {

    private static final int NUMBER_OF_ANIMALS = 5;
    private static final int NUMBER_OF_CREATURES = 5;
    private static final int NUMBER_OF_SPELLS = 10;

    /**
     * Resets the game with the given seed.
     *
     * @param seed The seed to be used for random placements.
     */
    public static void reset(long seed) {
        Game.status = null;
        Game.turn = 0;
        Random random = new Random(seed);
        initializeBoard();

        // Populate animals and creatures
        placeAnimalsOnBoard(random);
        placeCreaturesOnBoard(random);

        // Populate spells on the board
        placeSpellsOnBoard(random);
    }

    /**
     * Initializes the game board.
     */
    private static void initializeBoard() {
        Game.board = new Square[Game.ROW][Game.COL];
        for (int i = 0; i < Game.ROW; i++) {
            for (int j = 0; j < Game.COL; j++) {
                Game.board[i][j] = new Square(i, j);
            }
        }
    }

    /**
     * Places animals on the board at random locations.
     *
     * @param random The random object to generate random numbers.
     */
    private static void placeAnimalsOnBoard(Random random) {
        Set<Integer> animalIndexes = generateRandomIndexes(random, NUMBER_OF_ANIMALS, 20);
        Iterator<Integer> iterator = animalIndexes.iterator();
        Game.board[19][iterator.next()].setAnimal(new Rabbit("Rabbit"));
        Game.board[19][iterator.next()].setAnimal(new Fox("Fox"));
        Game.board[19][iterator.next()].setAnimal(new Deer("Deer"));
        Game.board[19][iterator.next()].setAnimal(new Owl("Owl"));
        Game.board[19][iterator.next()].setAnimal(new Badger("Badger"));
    }

    /**
     * Places creatures on the board at random locations.
     *
     * @param random The random object to generate random numbers.
     */
    private static void placeCreaturesOnBoard(Random random) {
        Set<String> creaturePositions = generateRandomPositions(random, NUMBER_OF_CREATURES);
        Iterator<String> iterator = creaturePositions.iterator();
        placeCreatureAtPosition(iterator.next(), new UnderAppreciatedUnicorn("Under-Appreciated Unicorn", 14));
        placeCreatureAtPosition(iterator.next(), new ComplicatedCentaur("Complicated Centaur", 36));
        placeCreatureAtPosition(iterator.next(), new DeceptiveDragon("Deceptive Dragon", 29));
        placeCreatureAtPosition(iterator.next(), new PrecociousPhoenix("Precocious Phoenix", 42));
        placeCreatureAtPosition(iterator.next(), new SassySphinx("Sassy Sphinx", 21));
    }

    /**
     * Places spells on the board at random locations.
     *
     * @param random The random object to generate random numbers.
     */
    private static void placeSpellsOnBoard(Random random) {
        Set<String> spellPositions = generateRandomPositions(random, NUMBER_OF_SPELLS);
        for (String position : spellPositions) {
            int[] coords = parsePositionToCoordinates(position);
            Game.board[coords[0]][coords[1]].setSpell(Spell.values()[random.nextInt(5)]);
        }
    }

    /**
     * Generates a set of random indexes.
     *
     * @param random       The random object to generate random numbers.
     * @param numberOfIndexes The number of unique indexes needed.
     * @param upperBound   The upper bound for random number generation.
     * @return A set of random indexes.
     */
    private static Set<Integer> generateRandomIndexes(Random random, int numberOfIndexes, int upperBound) {
        Set<Integer> indexes = new HashSet<>();
        while (indexes.size() < numberOfIndexes) {
            indexes.add(random.nextInt(upperBound));
        }
        return indexes;
    }

    /**
     * Generates a set of random positions.
     *
     * @param random       The random object to generate random numbers.
     * @param numberOfPositions The number of unique positions needed.
     * @return A set of random positions.
     */
    private static Set<String> generateRandomPositions(Random random, int numberOfPositions) {
        Set<String> positions = new HashSet<>();
        while (positions.size() < numberOfPositions) {
            positions.add((random.nextInt(18) + 1) + "," + random.nextInt(20));
        }
        return positions;
    }

    /**
     * Places a creature on the board at the given position.
     *
     * @param position The position where the creature should be placed.
     * @param creature The creature to be placed.
     */
    private static void placeCreatureAtPosition(String position, Creature creature) {
        int[] coords = parsePositionToCoordinates(position);
        Game.board[coords[0]][coords[1]].setCreature(creature);
    }

    /**
     * Parses a string position into x and y coordinates.
     *
     * @param position The position in the format "x,y".
     * @return An array containing the x and y coordinates.
     */
    private static int[] parsePositionToCoordinates(String position) {
        String[] parts = position.split(",");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }
}
