import javax.swing.ImageIcon;

/**
 * This tile represents a TeleportationDevice tile on the map. They are considered collectable
 * by chip and transferred to his inventory in order to make chip interact with TeleportationTiles
 */
public class TeleportationDevice extends Tiles {
    
    /**
     * This constructor is for a new TeleportationDevice with its appropriate char symbol and sprite image
     */
    public TeleportationDevice() {
        super('t');
        sprite = new ImageIcon("TPDevice.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * TeleportationDevice tiles are walkable and upon stepping onto them
     * chip gains a teleportation device in his inventory for teleportation tiles
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} it is walkable and a collectible for chip, upon stepping chip's inventory will be added with a teleportation device                 
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addTeleportationDevice();
        return true;
    }
}