import javax.swing.ImageIcon;

/**
 * This tile represents a BlueDoor tile on the map. They are considered door tiles
 * and they are can only be walked upon when chip has a blue key his inventory
 */
public class BlueDoor extends Tiles {
    
    /**
     * This constructor is for a new BlueDoor with its appropriate char symbol and sprite image
     */
    public BlueDoor() {
        super('B');
        sprite = new ImageIcon("BlueDoor.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * BlueDoor tiles are walkable only when chip has a blue key in his inventory
     * otherwise it is blocked and chip cannot pass through
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} when chip currently has a blue key in his inventory
     *         {@code false} when chip does not have a blue key currently in his inventory            
     */
  @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return inv.hasBlueKey();
    }
}