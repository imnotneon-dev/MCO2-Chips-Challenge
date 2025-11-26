import javax.swing.ImageIcon;

/**
 * This tile represents a wall tile on the map. They are not walkable and chip will get blocked upon trying
 * to step in this tile
 */
public class WallTile extends Tiles {
    
    /**
     * This constructor is for a new WallTile with its appropriate char symbol and sprite image
     */
    public WallTile() {
        super('X');
        sprite = new ImageIcon("WallTile.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * Wall tiles are not walkable when chip tries to walk over them, he will get blocked
     * and his x and y coordinates will not change
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code false} it is default that chip cannot walk through this specific tile   
     */
    @Override 
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return false;
    }
}