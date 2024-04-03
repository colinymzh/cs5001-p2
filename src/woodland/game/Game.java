package woodland.game;

import woodland.animals.*;
import woodland.creatures.*;
import woodland.spell.Spell;

import javax.json.*;
import java.io.StringReader;
import java.util.*;

/**
 * Represents the main game logic for the Woodland game.
 * The Game class manages the game board, animals, creatures, and spells. It provides methods
 * to move animals, cast spells, and update the game state. The game board is represented as a 2D array
 * of Square objects, and the game's current status is tracked using the StatusMessage enumeration.
 * The game involves various animals navigating through a woodland environment, interacting with creatures,
 * and using spells.
 * This class also provides utility methods to get specific squares on the board, check the game's over state,
 * and update the game state based on player actions.
 */
public class Game {

    // Constants for the number of rows and columns on the game board.
    protected static final int ROW = 20;
    protected static final int COL = 20;

    // 2D array representing the game board.
    protected static Square[][] board;
    // Keeps track of the current turn.
    protected static int turn;

    // Array of animal names used in the game.
    protected static String[] animals = new String[]{"Rabbit", "Fox", "Deer", "Owl", "Badger"};
    // Map to track creatures that are confused.
    protected Map<Creature, Integer> confuseCreature = new HashMap<>();

    // Status message indicating the current state of the game.
    public static StatusMessage status;

    /**
     * Constructor for the Game class.
     * Initializes the game board and other game-related settings.
     *
     * @param seed Seed for random number generation.
     */
    public Game(long seed) {
        GameReset.reset(seed);
    }

    /**
     * Returns the game board.
     *
     * @return 2D array representing the game board.
     */
    public Square[][] getBoard() {
        return board;
    }

    /**
     * Returns the square at the specified row and column.
     *
     * @param row Row index.
     * @param col Column index.
     * @return Square at the specified row and column.
     */
    public static Square getSquare(int row, int col) {
        if (row < 0 || row >= 20 || col < 0 || col >= 20) {
            return new Square(-1, -1);
        }
        return board[row][col];
    }

    /**
     * Moves an animal from one square to another on the game board.
     *
     * @param animal Animal to be moved.
     * @param oldRow Initial row position of the animal.
     * @param oldCol Initial column position of the animal.
     * @param newRow Target row position for the animal.
     * @param newCol Target column position for the animal.
     */
    public void moveAnimal(Animal animal, int oldRow, int oldCol, int newRow, int newCol) {

        // Check if the game is already won or lost.
        if (status != null && (status == StatusMessage.GAME_LOST || status == StatusMessage.GAME_WIN)) {
            return;
        }
        // Validate the move.
        if (!animal.getName().equals(animals[turn % 5]) || !animal.move(oldRow, oldCol, newRow, newCol) || getSquare(newRow, newCol).hasAnimal()) {
            status = StatusMessage.MOVE_INVALID;
            return;
        }

        // Calculate the direction of the move.
        int directionRow = oldRow == newRow ? 0 : (newRow - oldRow) / Math.abs(newRow - oldRow);
        int directionCol = oldCol == newCol ? 0 : (newCol - oldCol) / Math.abs(newCol - oldCol);

        // Check for specific animal types.
        boolean isDeerAndBadger = animal.getName().equals("Deer") || animal.getName().equals("Badger");
        boolean isRabbitAndFox = animal.getName().equals("Rabbit") || animal.getName().equals("Fox");
        boolean isOwl = animal.getName().equals("Owl");

        int tempRow = oldRow;
        int tempCol = oldCol;

        // Handle long jump logic for Deer, show the creatures that deer passes.
        if (animal instanceof Deer) {
            Deer deer = (Deer) animal;
            List<Square> path = deer.jumpPath(oldRow, oldCol, newRow, newCol);
            if (path != null) {
                for (Square square : path) {
                    if (square.hasCreature()) {
                        square.setVisible(true);
                    }
                }
            }
        }

        // Move the animal across the board.
        while (tempRow != newRow || tempCol != newCol) {
            tempRow += directionRow;
            tempCol += directionCol;

            if (getSquare(tempRow, tempCol).hasAnimal() && (isRabbitAndFox || (isOwl && getSquare(tempRow, tempCol).hasCreature()))) {
                status = StatusMessage.MOVE_INVALID;
                return;
            }

            if (getSquare(tempRow, tempCol).hasCreature()) {
                if (isDeerAndBadger) {
                    // Check if it's the final destination or just passing through
                    if (tempRow == newRow && tempCol == newCol) {
                        // Exact location, so it gets attacked
                        getSquare(oldRow, oldCol).setAnimal(null);
                        getSquare(tempRow, tempCol).setAnimal(animal);
                        getSquare(tempRow, tempCol).setVisible(true);
                        attackAnimal(getSquare(tempRow, tempCol).getCreature(), animal);
                        turn++;
                        return;
                    } else {
                        // Just passing through, so no harm done
                        continue;
                    }
                } else {
                    // For animals other than Deer and Badger
                    getSquare(oldRow, oldCol).setAnimal(null);
                    getSquare(tempRow, tempCol).setAnimal(animal);
                    getSquare(tempRow, tempCol).setVisible(true);
                    attackAnimal(getSquare(tempRow, tempCol).getCreature(), animal);
                    turn++;
                    return;
                }
            }
        }

        turn++;
        // Remove the animal from its old position.
        getSquare(oldRow, oldCol).setAnimal(null);
        // Place the animal in its new position.
        getSquare(newRow, newCol).setAnimal(animal);

        // Check if the animal's current square has a spell.
        if (animal.getSquare().getSpell() != null) {
            // Save the spell for the animal.
            saveSpell(animal, animal.getSquare().getSpell());
            // Remove the spell from the square after saving.
            animal.getSquare().setSpell(null);
        }

        // Update the status to indicate a successful move.
        status = StatusMessage.MOVE_SUCCESSFUL;

        // Update the game state after the move.
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                // Check if the current square has a creature.
                if (getSquare(i, j).hasCreature()) {
                    Creature creature = getSquare(i, j).getCreature();
                    // Check if the creature is charmed by the moved animal.
                    if (creature.isCharmed(animal)) {
                        creature.updateCharmAnimal(animal);
                    // Check if the creature is shielded by the moved animal.
                    } else if (creature.isShieldAnimal(animal)) {
                        creature.updateShieldAnimal(animal);
                    // Check if the creature is in a confused state.
                    } else if (creature.isConfused()) {
                        // If the creature has a confusion duration, decrement it.
                        if (confuseCreature.get(creature) != null && confuseCreature.get(creature) != 0) {
                            confuseCreature.put(creature, confuseCreature.get(creature) - 1);
                        // If the creature's confusion duration has ended, set its state to not confused.
                        } else {
                            creature.setConfused(false);
                        }
                    }
                }
            }
        }
    }


    /**
     * Represents the action of an animal attacking a creature.
     *
     * @param creature The target creature.
     * @param animal   The attacking animal.
     */
    public void attackAnimal(Creature creature, Animal animal) {
        if (creature.isShieldAnimal(animal)) {
            creature.updateShieldAnimal(animal);
        } else if (creature.isConfused() && confuseCreature.get(creature) == 1) {
            creature.setConfused(false);
            confuseCreature.put(creature, 0);
        } else if (creature.isCharmed(animal)) {
            creature.updateCharmAnimal(animal);
        } else {
            animal.attacked(creature.getAttackValue());
            status = StatusMessage.MOVE_INTERRUPTED;
        }
    }

    /**
     * Saves a spell for the given animal.
     *
     * @param animal The animal to save the spell for.
     * @param spell  The spell to be saved.
     */
    public void saveSpell(Animal animal, Spell spell) {
        animal.addSpell(spell);
    }

    /**
     * Casts a spell from an animal's current position.
     *
     * @param animal The animal casting the spell.
     * @param spell  The spell being cast.
     */
    public void castSpell(Animal animal, Spell spell) {
        Square square = animal.getSquare();
        int row = square.getRow();
        int col = square.getCol();

        // Handle the DETECT spell.
        if (spell == Spell.DETECT) {
            // Make adjacent squares visible.
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    getSquare(row + i, col + j).setVisible(true);
                }
            }
        }
        // Handle the SHIELD spell.
        else if (spell == Spell.SHIELD) {
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++) {
                    if (getSquare(i, j).hasCreature()) {
                        getSquare(i, j).getCreature().addShieldAnimal(animal);
                    }
                }
            }
        }
        // Handle the CONFUSE spell.
        else if (spell == Spell.CONFUSE) {
            // Apply confusion to creatures in adjacent squares.
            for (int i = -1; i <= 1; i += 2) {
                for (int j = -1; j <= 1; j += 2) {
                    if (getSquare(row + i, col + j).hasCreature()) {
                        confuseCreature.put(getSquare(row + i, col + j).getCreature(), 5);
                        getSquare(row + i, col + j).getCreature().setConfused(true);
                    }
                }
            }
        }
        // Handle the CHARM spell.
        else if (spell == Spell.CHARM) {
            // Charm creatures in adjacent squares.
            for (int i = -1; i <= 1; i += 2) {
                for (int j = -1; j <= 1; j += 2) {
                    if (getSquare(row + i, col + j).hasCreature()) {
                        getSquare(row + i, col + j).getCreature().addCharmAnimal(animal);
                    }
                }
            }
        }

        // Update the animal's spell status.
        animal.updateSpell(spell);
        status = StatusMessage.SPELL_SUCCESSFUL;
    }

    /**
     * Checks if the game is over based on the animals' positions and states.
     *
     * @return true if the game is over, otherwise false.
     */
    public static boolean gameOver() {
        int animalsArrived = 0;

        // Iterate through each square on the board.
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                Square square = getSquare(i, j);

                // Check if the square has an animal.
                if (square.hasAnimal()) {
                    if (!square.getAnimal().isAlive()) {
                        status = StatusMessage.GAME_LOST;
                        return true;
                    } else if (i == 0) {
                        // Count the animals that have reached the top row.
                        animalsArrived++;
                    }
                }
            }
        }

        // Check if all animals have reached the top row.
        if (animalsArrived == animals.length) {
            status = StatusMessage.GAME_WIN;
        }

        return false;
    }

    /**
     * Updates the game state based on the provided action and animal details.
     *
     * @param body JSON string containing action and animal details.
     * @return Updated game state as a JsonObject.
     */
    public JsonObject updateState(String body) {
        JsonObject input = Json.createReader(new StringReader(body)).readObject();
        String action = input.getString("action");
        String animalName = input.getString("animal");

        // Find the current square of the specified animal.
        Square currentSquare = AnimalFinder.findAnimal(animalName);

        // Handle the specified action.
        if ("move".equals(action)) {
            JsonObject destination = input.getJsonObject("toSquare");
            int newRow = destination.getInt("row");
            int newCol = destination.getInt("col");
            moveAnimal(currentSquare.getAnimal(), currentSquare.getRow(), currentSquare.getCol(), newRow, newCol);
        } else if ("spell".equals(action)) {
            String spellName = input.getString("spell");
            castSpell(currentSquare.getAnimal(), Spell.valueOf(spellName.toUpperCase()));
        }

        // Return the updated game state.
        return JsonBuilder.toJson();
    }
}