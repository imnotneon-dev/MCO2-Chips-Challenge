import javax.swing.ImageIcon;

/**
 * This tile represents a Fire tile on the map. They are not walkable and chip will die upon stepping unless
 * he has a 'Fire boots' item in his inventory.
 */
public class FireTile extends Tiles {
    
    /**
     * This constructor is for a new FireTile with its appropriate char symbol and sprite image
     */
    public FireTile() {
        super('F');
        sprite = (new ImageIcon("FireTile.png"));
    }

    /**
     * Determines whether chip can walk over the tile
     * Fire tiles are only walkable when chip has a 'Fire boots' item in his inventory
     * If chip does not have a 'Fire boots', it will cause chip to die and it is unwalkable
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} if chip has a fire boots
     *         {@code false} if chip does not have a fire boots
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        if(inv.hasFireBoots())
            return true;
        else {
            chip.die();
            return false;
        }
    }
}