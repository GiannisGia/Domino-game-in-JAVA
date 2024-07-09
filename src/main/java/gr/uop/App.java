import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Giannopoulos Georgios
 * @author Giannopoulos Ioannis
 */
public class App {
    
    public static void main(String[] args) throws Exception {
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        GameEngine gameEngine = new GameEngine(players);
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        while (!gameEngine.isGameOver()) {
            for (Player player : players) {
                if (gameEngine.isGameOver()) {
                    break;
                }

                System.out.println(player.getName() + "'s turn");
                System.out.println("Your tiles: " + player.getTiles());
                System.out.println("Line of play: " + gameEngine.getLineOfPlay());
                System.out.println("Open ends: " + gameEngine.getOpenEnds());

                boolean played = false;

                while (!played) {
                    System.out.println("Enter tile to play (format: value1:value2) or 'draw' to draw a tile:");
                    String input = scanner.nextLine();
                    
                    if (input.equalsIgnoreCase("draw")) {
                        gameEngine.drawTile(player);
                        System.out.println("Player: " + player.getName() + " drew a tile");
                        System.out.println("Your tiles: " + player.getTiles());
                    }
                    else if (input.equalsIgnoreCase("pass")) {
                        System.out.println("Player: " + player.getName() + " passed the round");
                        played = true;
                    }
                    else {
                        String[] values = input.split(":");
                        if (values.length == 2) {
                            try {
                                int value1 = Integer.parseInt(values[0]);
                                int value2 = Integer.parseInt(values[1]);
                                Tile tile = new Tile(value1, value2);

                                if (gameEngine.playTile(player, tile)) {
                                    // player played his tile
                                    played = true;
                                }
                                else {
                                    System.out.println("Invalid tile or no matching open end. Try again");
                                }
                            }
                            catch (NumberFormatException e) {
                                System.out.println("Invalid input format. Format is 'value1:value2'.");
                            }
                        }
                        else {
                            System.out.println("Invalid input format");
                        }
                    }
                }
                if (gameEngine.isGameOver()) {
                    break;
                }
            }
        }
        // Player winnerPlayer = gameEngine.getWinner();
        Player winnerPlayer = gameEngine.determineWinner();

        System.out.println("Game over! The winner is " + winnerPlayer.getName() + " with score " + winnerPlayer.getScore());

        for (Player player : players) {
            System.out.println("Game over!" + player.getName() + " with score " + player.getScore());
        }
    }
}
