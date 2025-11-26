import javax.swing.ImageIcon;

/**
 * This tile represents a Player tile on the map. They are considered the tiles
 * to simulate moving chip (the player)
 */
public class PlayerTile extends Tiles {

    /**
     * This constructor is for a new PlayerTile with its appropriate char symbol and sprite image
     */
    public PlayerTile() {
        super('@');
        this.sprite = new ImageIcon("Chip.png");
    } 
    
    /**
     * Determines whether chip can walk over the tile
     * The player tile by default cannot be walked upon by other entities
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code false} by default other entities cannot walk through the player          
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return false;
    }
}
