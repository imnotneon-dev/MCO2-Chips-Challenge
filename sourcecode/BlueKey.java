import javax.swing.ImageIcon;

/**
 * This tile represents a BlueKey tile on the map. They are considered collectable
 * by chip and transferred to his inventory in order to make chip interact with BlueDoor tiles
 */
public class BlueKey extends Tiles {
    
    /**
     * This constructor is for a new BlueKey with its appropriate char symbol and sprite image
     */
    public BlueKey() {
        super('b');
        sprite = new ImageIcon("BlueKey.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * BlueKey tiles are walkable and upon stepping onto them
     * chip gains a blue key in his inventory for blue door tiles
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} by default it is walkable and a collectible for chip, upon stepping chip's inventory will be added with a blue key                 
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return true;
    }
}