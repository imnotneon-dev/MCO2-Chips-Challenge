import javax.swing.ImageIcon;

/**
 * This tile represents a RedKey tile on the map. They are considered collectable
 * by chip and transferred to his inventory in order to make chip interact with RedDoor tiles
 */
public class RedKey extends Tiles {
    
    /**
     * This constructor is for a new RedKey with its appropriate char symbol and sprite image
     */
    public RedKey() {
        super('r');
        sprite = new ImageIcon("RedKey.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * RedKey tiles are walkable and upon stepping onto them
     * chip gains a red key in his inventory for red door tiles
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} by default it is walkable and a collectible for chip, upon stepping chip's inventory will be added with a red key                 
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return true;
    }
}