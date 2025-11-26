import javax.swing.ImageIcon;

/**
 * This tile represents a Exit tile on the map. They are considered the exit tiles
 * and they are can only be walked upon when chip has the required number of chips in his inventory
 */
public class ExitTile extends Tiles {
    
    /**
     * This constructor is for a new ExitTile with its appropriate char symbol and sprite image
     */
    public ExitTile() {
        super('E');
        sprite = (new ImageIcon("ExitTile.png"));
    }

    /**
     * Determines whether chip can walk over the tile
     * Exit tiles are walkable only when chip has the required chips in his inventory for the level
     * otherwise it is blocked and chip cannot pass through
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} when chip currently has the required chips in his inventory
     *         {@code false} when chip does not have the required chips currently in his inventory            
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return inv.getChips() >= requiredChips;
    }
}
