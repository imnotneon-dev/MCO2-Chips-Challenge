import javax.swing.ImageIcon;

/**
 * Represents all the tiles used in the map layouts, it is responsible for giving property of the special tiles including hazard tiles (water, ice, fire, enemy), force tiles, teleporter tiles and exit tiles.
 * This class also handles the action of the force tiles when chip has stepped upon them, pushing him into the direction 
 * where the force tile has ended or an  obstacle/hazard is the next tile.
 * 
 * This class works with the all the classes that uses the tiles for the game
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public abstract class Tiles {

    /**
     * This stores the type of tile based on the symbol
     */
    private char symbol;

    /**
     * This stores the image sprite of the tile
     */
    protected ImageIcon sprite;

    /**
     * This constructor sets the symbol of the tile based on the param
     * 
     * @param symbol the symbol character that determines the type of tile
     */
    public Tiles(char symbol) {
        this.symbol = symbol;
    }

    /**
     * This method sets the symbol of the tile based on the param
     * 
     * @param symbol the symbol character that determines the type of tile
     */
    public void setTiles(char symbol) {
        this.symbol = symbol;
    }

    /**
     * This method gets the symbol of the tile 
     * 
     * @return {@code char} the symbol character that determines the type of tile
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * This method gets the ImageIcon sprite of the tile 
     * 
     * @return {@code ImageIcon} sprite of the tile character that determines the type of tile
     */
    public ImageIcon getSprite() {
        return sprite;
    }

    /**
     * Determines whether chip can walk over the tile
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} sets the tiles default walkable state as true
     */
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return true;
    }

    /**
     * Handles the movement of chip when it comes to specific tiles 
     * including ice, force, teleporter tiles
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @return {@code false} sets the tiles default applyForce state as false            
     */
    public boolean applyForce(Chip chip, Maps map) {
        return false;
    }
}
