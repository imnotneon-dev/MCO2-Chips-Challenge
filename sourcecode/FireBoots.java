import javax.swing.ImageIcon;

/**
 * This tile represents an FireBoots tile on the map. They are considered collectable
 * by chip and transferred to his inventory in order to make chip interact with the water tiles
 */
public class FireBoots extends Tiles {
    
    /**
     * This constructor is for a new FireBoots with its appropriate char symbol and sprite image
     */
    public FireBoots() {
        super('L');
        sprite = new ImageIcon("FireBoots.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * FireBoots tiles are walkable and upon stepping onto them
     * chip gains FireBoots in his inventory for fire tiles
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} by default it is walkable and a collectible for chip, upon stepping chip's inventory will be added with fire boots         
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addFireBoots();
        return true;
    }
}