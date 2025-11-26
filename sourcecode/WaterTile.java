import javax.swing.ImageIcon;

/**
 * This tile represents a water tile on the map. They are not walkable and chip will die upon stepping unless
 * he has a 'Flippers' item in his inventory.
 */
public class WaterTile extends Tiles {
    
    /**
     * This constructor is for a new WaterTile with its appropriate char symbol and sprite image
     */
    public WaterTile() {
        super('W');
        sprite = new ImageIcon("WaterTile.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * Water tiles are only walkable when chip has a 'Flippers' item in his inventory
     * If chip does not have a 'Flippers', it will cause chip to die and it is unwalkable
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} if chip has a flipper
     *         {@code false} if chip does not have a flipper
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        if(inv.hasFlippers())
            return true;
        else {
            chip.die();
            return false;
        }
    }
}