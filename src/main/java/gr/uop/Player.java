package gr.uop;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a player in a dominoes game.
 * <p>
 * A player has a name, a score and a {@code Set} of tiles.
 * </p>
 * @author Giannopoulos Georgios 
 * @author Giannopoulos Ioannis 
 * @version 0.0.1
 */
public class Player {
    String name;
    int score;
    Set<Tile> tiles;

    /**
     * Constructs a player with the specified name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.tiles = new HashSet<>();
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the score of the player.
     *
     * @return the score of the player
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Returns the tiles held by the player.
     *
     * @return the tiles held by the player
     */
    public Set<Tile> getTiles() {
        return tiles;
    }

    /**
     * Adds a tile to the player's collection.
     *
     * @param tile the tile to be added
     */
    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    /**
     * Removes a tile from the player's collection.
     *
     * @param tile the tile to be removed
     */
    public void removeTile(Tile tile) {
        tiles.remove(tile);
    }

    /**
     * Updates the player's score by the specified points.
     *
     * @param score the score 
     */
    public void updateScore(int score) {
        this.score = score;
        System.out.println("updateScore: " + this.score);
    }
    
}
