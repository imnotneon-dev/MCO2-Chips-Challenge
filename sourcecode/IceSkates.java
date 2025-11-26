import javax.swing.ImageIcon;

/**
 * This tile represents an Ice Skates tile on the map. They are considered collectable
 * by chip and transferred to his inventory in order to make chip interact with the ice tiles
 */
public class IceSkates extends Tiles {
    
    /**
     * This constructor is for a new IceSkates with its appropriate char symbol and sprite image
     */
    public IceSkates() {
        super('Q');
        sprite = new ImageIcon("IceSkates.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * Ice Skates tiles are walkable and upon stepping onto them
     * chip gains ice skates in his inventory for ice tiles
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} by default it is walkable and a collectible for chip, upon stepping chip's inventory will be added with ice skates           
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addIceSkates();
        return true;
    }
}