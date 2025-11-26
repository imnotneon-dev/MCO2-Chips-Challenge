import javax.swing.ImageIcon;

/**
 * This tile represents an Ice tile on the map. They are only walkable when chip
 * has an 'Ice Skates' item in his inventory otherwise he slides on the tiles
 */
public class IceTile extends Tiles {
    
   /**
     * This constructor is for a new Ice Tile with its appropriate char symbol and sprite image
     */
    public IceTile() {
        super('I');
        sprite = new ImageIcon("IceTile.png");
    }
    
    /**
     * Determines whether chip has ice skates inside his inventory and if the he does the tiles are walkable
     * Otherwise he slides over the tiles
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @return {@code true} if chip can still move on the ice tiles and continue sliding
     *         {@ code false} if chip does not have ice skates inside his inventory
     *         {@ code false} if chip cannot move longer on the tiles            
     */
    @Override
    public boolean applyForce(Chip chip, Maps map) {
        if (chip.getInventory().hasIceSkates()) {
            return false;
        }

        int dx = chip.getLastMoveX();
        int dy = chip.getLastMoveY();

        return chip.tryMove(dx, dy, map);
    }
}
