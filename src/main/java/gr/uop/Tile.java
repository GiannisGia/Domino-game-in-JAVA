package gr.uop;

/**
 * The {@code Tile} class respresents a tile in a domino set
 * 
 * @author Giannopoulos Georgios
 * @author Giannopoulos Ioannis
 * @version 0.0.1
 */
public class Tile {
    private int leftValue;
    private int rightValue;

    /**
     * Constructs a tile with the specified values.
     *
     * @param leftValue the value of the first side of the tile
     * @param rightValue the value of the second side of the tile
     */
    public Tile(int leftValue, int rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    /**
     * Returns the value of the left side of the tile.
     *
     * @return the value of the left side
     */
    public int getleftValue() {
        return leftValue;
    }

    /**
     * Returns the value of the right side of the tile.
     *
     * @return the value of the right side
     */
    public int getRightValue() {
        return rightValue;
    }

    /**
     * Returns a hash code value for the object. This method is used by the Java
     * hashing algorithms when storing objects in hash-based data structures such
     * as HashMap, HashSet, etc.
     *
     * @return the hash code value for the object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + leftValue;
        result = prime * result + rightValue;
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (leftValue != other.leftValue)
            return false;
        if (rightValue != other.rightValue)
            return false;
        return true;
    }

    /**
     * Get the sum of the values of the tile
     * @param tile
     * @return the sum of the left and right value of the tile
     */
    public int getTileSum(Tile tile) {
        return tile.getleftValue() + tile.getRightValue();
    }

    /**
     * Returns a string representation of the tile.
     * The string representation is in the format [leftValue:rightValue].
     *
     * @return a string representation of the tile
     */
    @Override
    public String toString() {
        return ("[" + getleftValue() + ":" + getRightValue() + "]");
    }
}
