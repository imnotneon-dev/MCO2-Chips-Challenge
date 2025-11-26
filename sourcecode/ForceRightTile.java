import javax.swing.ImageIcon;

/**
 * This tile represents a ForceRight tile on the map. They force chip into the next force tile
 * until he steps onto a non-force tile
 */
public class ForceRightTile extends Tiles {

    /**
     * This constructor is for a new ForceRightTile with its appropriate char symbol and sprite image
     */
    public ForceRightTile() {
        super('>');
        sprite = new ImageIcon("ForceRightTile.png");
    }

    /**
     * Determines whether chip can still be forced a direction within a force tile
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @return {@code true} if chip can move within the force tiles
     *         {@ code false} if chip cannot move within the force tiles      
     */
    @Override
    public boolean applyForce(Chip chip, Maps map) {
        return chip.tryMove(1, 0, map);
    }
}
