import javax.swing.ImageIcon;

/**
 * This tile represents a RedDoor tile on the map. They are considered door tiles
 * and they are can only be walked upon when chip has a red key his inventory
 */
public class RedDoor extends Tiles {
    
    /**
     * This constructor is for a new RedDoor with its appropriate char symbol and sprite image
     */
    public RedDoor() {
        super('R');
        sprite = new ImageIcon("RedDoor.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * RedDoor tiles are walkable only when chip has a red key in his inventory
     * otherwise it is blocked and chip cannot pass through
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} when chip currently has a red key in his inventory
     *         {@code false} when chip does not have a red key currently in his inventory            
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return inv.hasRedKey();
    }
}