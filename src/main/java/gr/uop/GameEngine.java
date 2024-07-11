package gr.uop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** 
 * @author Giannopoulos Georgios 
 * @author Giannopoulos Ioannis 
 * @version 0.0.1
 */
public class GameEngine {
    private List<Player> players;
    private LinkedList<Tile> lineOfPlay; // so we have access on both ends
    private Set<Tile> stock; // no duplicates Tiles
    private int currentPlayerIndex; // Track the current player index
    
    /**
     * Constructs a new GameEngine object with the specified list of players.
     *
     * @param players the list of players participating in the game
     */
    public GameEngine(List<Player> players) {
        this.players = players;
        this.lineOfPlay = new LinkedList<>();
        this.stock = generateStock();
        this.currentPlayerIndex = 0; // Initialize the current player index
        distributeTiles();
    }

    /**
     * Generates a set of tiles representing the stock for the game.
     * Each tile is a unique combination of two numbers from 0 to 6.
     * The tiles are shuffled to provide a new order for each run.
     *
     * @return a new LinkedHashSet containing the shuffled tiles.
     */
    private Set<Tile> generateStock() {
        Set<Tile> stock = new HashSet<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                stock.add(new Tile(i, j));
            }
        }
        // Shuffle the stock so each run gets a new order of the tiles
        List<Tile> stockList = new ArrayList<>(stock);
        Collections.shuffle(stockList);
        // Return the stockList as a new LinkedHashSet
        return new LinkedHashSet<>(stockList);
    }

    /**
     * Returns the stock of tiles in the game.
     *
     * @return the stock of tiles
     */
    public Set<Tile> getStock() {
        return stock;
    }  

    /**
     * Distribute the tiles to the players.
     * Each player gets 7 tiles.
     * 
     * <p> Tiles are getting selected from the stock using {@code iterator().next()}.
     * Stock is shuffled when initialized, so randomness is achieved.
     * We add the tile to the player and remove it from the stock. </p>
     */
    private void distributeTiles() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                Tile tile = stock.iterator().next();
                player.addTile(tile);
                stock.remove(tile);
            }
        }
    }

    /**
     * Returns a list of integers representing the open ends of the line of play.
     * An open end is defined as the values of the first and last tiles in the line of play.
     * If the line of play is empty, an empty list is returned.
     *
     * @return a list of integers representing the open ends of the line of play
     */
    public List<Integer> getOpenEnds() {
        List<Integer> openEnds = new ArrayList<>();

        // Check that it's not the first move of the game
        if(!lineOfPlay.isEmpty()) {
            Tile firstTile =  lineOfPlay.getFirst();
            Tile lastTile = lineOfPlay.getLast();

            openEnds.add(firstTile.getleftValue());
            openEnds.add(lastTile.getRightValue());
        }
        return openEnds;
    }

    /**
     * Returns the line of play in the game.
     *
     * @return the line of play as a LinkedList of Tile objects
     */
    public LinkedList<Tile> getLineOfPlay() {
        return lineOfPlay;
    }

    /**
     * Plays a tile on the line of play.
     * 
     * @param player the player making the move
     * @param tile the tile to be played
     * @return true if the tile was successfully played, false otherwise
     */
    public boolean playTile(Player player, Tile tile) {

        if (lineOfPlay.isEmpty()) {
            // First move of the game 
            // Line of play is empty
            lineOfPlay.add(tile);
            player.removeTile(tile);
            advanceToNextPlayer();
            return true;
        } else {
            // Line of play contains tiles 
            // Check each combination
            Tile firstTile = lineOfPlay.getFirst();
            Tile lastTile = lineOfPlay.getLast();

            if (tile.getleftValue()== firstTile.getleftValue()) {
                // We need to add the tile rotated 180
                lineOfPlay.addFirst(new Tile(tile.getRightValue(), tile.getleftValue()));
                player.removeTile(tile);
                advanceToNextPlayer();
                return true;
            } else if (tile.getRightValue() == firstTile.getleftValue()) {
                // Tile is okay, no need for any other transformation
                lineOfPlay.addFirst(tile);
                player.removeTile(tile);
                advanceToNextPlayer();
                return true;
            } else if (tile.getleftValue() == lastTile.getRightValue()) {
                // Tile is okay, no need for any other transformation
                lineOfPlay.addLast(tile);
                player.removeTile(tile);
                advanceToNextPlayer();
                return true;
            } else if (tile.getRightValue() == lastTile.getRightValue()) {
                // We need to add the tile rotated 180
                lineOfPlay.addLast(new Tile(tile.getRightValue(), tile.getleftValue()));
                player.removeTile(tile);
                advanceToNextPlayer();
                return true;
            } 
        }
        // Not a valid move
        return false;
    }

    /**
     * Advances to the next player in the list, cycling back to the first player if necessary.
     */
    private void advanceToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Returns the current player whose turn it is.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Draws a tile from the stock and adds it to the specified player's hand.
     * If the stock is empty, a message is printed indicating that the stock is empty.
     *
     * @param player the player to add the drawn tile to
     */
    public void drawTile(Player player) {
        if (!stock.isEmpty()) {
            System.out.println("Drawing from the stock");
            Tile tile = stock.iterator().next();
            player.addTile(tile);
            stock.remove(tile);
            advanceToNextPlayer();
        } else {
            System.out.println("Stock is empty");
        }
    }

    /**
     * Check if the game is over by iterating over players' tiles.
     * If the player's tiles are empty, the game stops.
     *
     * @return {@code true} if a player has no tiles left,
     * otherwise {@code false}
     */
    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getTiles().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determines the winner of the game based on the scores of the players.
     * The player with the lowest score wins.
     *
     * @return the winner of the game
     */
    public Player determineWinner() {
        Player winner = players.get(0);
        int minScore = calculateScore(winner);
        winner.updateScore(minScore);
        for (Player player : players) {
            int score = calculateScore(player);
            player.updateScore(score);
            if (score < minScore) {
                minScore = score;
                winner = player;
            }
        }
        return winner;
    }

    /**
     * Calculates the score of a player based on the sum of their tiles' values.
     *
     * @param player the player whose score is to be calculated
     * @return the calculated score
     */
    private int calculateScore(Player player) {
        int score = 0;
        for (Tile tile : player.getTiles()) {
            score += tile.getTileSum(tile);
        }
        return score;
    }

}
