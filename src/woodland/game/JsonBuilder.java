package woodland.game;

import woodland.animals.Animal;
import woodland.creatures.Creature;
import woodland.spell.Spell;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Map;

import static woodland.game.Game.*;

/**
 * Utility class for converting game objects to their JSON representation.
 */
public class JsonBuilder {

    /**
     * Converts the current game state to a JSON representation.
     *
     * @return The JSON representation of the current game state.
     */
    public static JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder array = Json.createArrayBuilder();

        // Convert the game board to JSON
        for (int i = 0; i < ROW; i++) {
            JsonArrayBuilder child = Json.createArrayBuilder();
            for (int j = 0; j < COL; j++) {
                Square square = Game.getSquare(i, j);

                // Handle squares with both an animal and a creature
                if (square.hasAnimal() && square.hasCreature()){
                    JsonArrayBuilder combined = Json.createArrayBuilder();

                    Animal animal = square.getAnimal();
                    combined.add(buildAnimalJson(animal).build().getJsonObject(0));

                    Creature creature = square.getCreature();
                    combined.add(buildCreatureJson(creature).build().getJsonObject(0));

                    child.add(combined);
                } else if (square.hasAnimal()) {
                // If squares have just animals
                    Animal animal = square.getAnimal();
                    child.add(buildAnimalJson(animal));
                } else if (square.hasCreature() && square.isVisible()) {
                // If squares have creatures and the state of the creature is visible
                    Creature creature = square.getCreature();
                    child.add(buildCreatureJson(creature));
                } else {
                // If nothing
                    child.add(Json.createArrayBuilder());
                }
            }
            array.add(child);
        }

        // Add game-specific data to the JSON
        builder.add("board", array)
                .add("gameOver", Game.gameOver())
                .add("currentAnimalTurn", Game.animals[Game.turn % 5])
                .add("nextAnimalTurn", Game.animals[(Game.turn + 1) % 5])
                .add("status", Game.status == null ? "" : Game.status.getDescription())
                .add("currentAnimalTurnType", "")
                .add("extendedStatus", "");

        return builder.build();
    }

    /**
     * Builds the JSON representation of a given animal.
     *
     * @param animal The animal object.
     * @return A JSON array representation of the animal.
     */
    private static JsonArrayBuilder buildAnimalJson(Animal animal) {
        JsonObjectBuilder o = Json.createObjectBuilder()
                .add("name", animal.getName())
                .add("type", "Animal")
                .add("description", animal.getDescription())
                .add("life", animal.getLifePoints());

        // Convert animal's spells to JSON
        JsonArrayBuilder spells = Json.createArrayBuilder();
        for (Map.Entry<Spell, Integer> entry : animal.getSpells().entrySet()) {
            if (entry.getValue() > 0) {
                spells.add(buildSpellJson(entry));
            }
        }

        o.add("spells", spells);
        return Json.createArrayBuilder().add(o);
    }

    /**
     * Builds the JSON representation of a given spell.
     *
     * @param entry The spell map entry.
     * @return A JSON object representation of the spell.
     */
    private static JsonObjectBuilder buildSpellJson(Map.Entry<Spell, Integer> entry) {
        Spell key = entry.getKey();
        return Json.createObjectBuilder()
                .add("name", key.getValue())
                .add("description", key.getDescription())
                .add("amount", entry.getValue());
    }

    /**
     * Builds the JSON representation of a given creature.
     *
     * @param creature The creature object.
     * @return A JSON array representation of the creature.
     */
    private static JsonArrayBuilder buildCreatureJson(Creature creature) {
        JsonObjectBuilder o = Json.createObjectBuilder()
                .add("name", creature.getName())
                .add("type", "Creature")
                .add("description", creature.getDescription())
                .add("shortName", creature.getShortName())
                .add("attack", creature.getAttackValue())
                .add("confused", creature.isConfused());

        // Convert creature's charmed animals to JSON
        JsonArrayBuilder charmeds = Json.createArrayBuilder();
        for (Map.Entry<Animal, Integer> entry : creature.getCharmAnimal().entrySet()) {
            JsonObjectBuilder charmed = Json.createObjectBuilder()
                    .add("animal", entry.getKey().getName())
                    .add("turnsLeft", entry.getValue());
            charmeds.add(charmed);
        }

        o.add("charmed", charmeds);
        return Json.createArrayBuilder().add(o);
    }
}
