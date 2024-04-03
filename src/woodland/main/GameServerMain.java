package woodland.main;

import woodland.animals.Animal;
import woodland.creatures.Creature;
import woodland.game.Game;
import woodland.game.GameReset;
import woodland.game.JsonBuilder;
import woodland.game.Square;

import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The main server class for the game.
 * Initializes a game instance and listens for client connections to handle game-related requests.
 */
public class GameServerMain {

    /**
     * The port number on which the server will run.
     */
    private static int port;

    /**
     * The main game instance.
     */
    private static Game game;

    /**
     * The seed value used for game initialization.
     */
    private static long seed;

    /**
     * The main method to start the game server.
     *
     * @param args Command line arguments. Expects two arguments: port number and seed value.
     * @throws IOException If there is an issue with server socket operation.
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Usage: GameServerMain <port> <seed>");
            return;
        }

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number: " + args[0]);
            return;
        }

        try {
            seed = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid seed value: " + args[1]);
            return;
        }

        game = new Game(seed);

        ServerSocket serverSocket = new ServerSocket(port);
        printInitialPositions(); // Display initial game positions to console

        // Server loop to continuously listen for and handle client connections
        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                // Parse incoming HTTP request
                String line;
                String method = "";
                String path = "";

                // Parsing headers to determine HTTP method, path and content length
                int contentLength = 0;
                while (!(line = in.readLine()).isEmpty()) {
                    if (line.startsWith("GET")) {
                        method = "GET";
                        path = line.split(" ")[1];
                    } else if (line.startsWith("POST")) {
                        method = "POST";
                        path = line.split(" ")[1];
                    } else if (line.startsWith("OPTIONS")) {
                        method = "OPTIONS";
                        path = line.split(" ")[1];
                    } else if (line.startsWith("Content-Length: ")) {
                        contentLength = Integer.parseInt(line.split(": ")[1]);
                    }
                }

                // If POST request, read the body
                StringBuilder body = new StringBuilder();
                if ("POST".equals(method)) {
                    char[] bodyChars = new char[contentLength];
                    in.read(bodyChars, 0, contentLength);
                    body.append(bodyChars);
                }

                // Handle the request and send response
                String responseBody = handleRequest(method, path, body.toString());
                sendResponse(out, responseBody);
            }
        }
    }

    /**
     * Handles the client request based on the HTTP method, path and optional body.
     *
     * @param method HTTP method (e.g., GET, POST)
     * @param path   Requested path (e.g., /game, /reset)
     * @param body   Optional body for POST requests
     * @return Response body as a string
     */
    private static String handleRequest(String method, String path, String body) {

        // Log the incoming request body
        System.out.println(">>" + body);

        // Determine response based on the request
        if ("GET".equals(method) && "/".equals(path)) {
            return "{\"status\": \"ok\"}";
        } else if ("GET".equals(method) && "/game".equals(path)) {
            JsonObject gameState = JsonBuilder.toJson();
            return gameState.toString();
        }  else if ("POST".equals(method) && "/game".equals(path)) {
            return game.updateState(body).toString();
        } else if ("POST".equals(method) && "/reset".equals(path)) {
            GameReset.reset(seed);
            JsonObject gameState = JsonBuilder.toJson();
            return gameState.toString();
        }
        // Default error response
        return "{\"status\": \"error\"}";
    }

    /**
     * Sends a response back to the client.
     *
     * @param out          PrintWriter to write the response to the client
     * @param responseBody The body of the response
     */
    private static void sendResponse(PrintWriter out, String responseBody) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: application/json");
        out.println("Access-Control-Allow-Origin: *");
        out.println("Access-Control-Allow-Methods: *");
        out.println("Access-Control-Allow-Headers: *");
        out.println("Access-Control-Max-Age: 86400");
        out.println("Content-Length: " + responseBody.length());
        out.println();
        out.println(responseBody);
    }

    /**
     * Prints the initial positions of animals, creatures, and spells on the game board to the console.
     */
    private static void printInitialPositions() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Square square = Game.getSquare(i, j);
                if (square.hasAnimal()) {
                    Animal animal = square.getAnimal();
                    System.out.println("Animal " + animal.getName() + " is at row: " + i + ", column: " + j);
                }
                if (square.hasCreature()) {
                    Creature creature = square.getCreature();
                    System.out.println("Creature " + creature.getName() + " is at row: " + i + ", column: " + j);
                }

                if (square.hasSpell()) {
                    System.out.println("Spell is at row: " + i + ", column: " + j);
                }
            }
        }
    }
}
