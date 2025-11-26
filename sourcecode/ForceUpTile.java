import javax.swing.ImageIcon;

/**
 * This tile represents a ForceUp tile on the map. They force chip into the next force tile
 * until he steps onto a non-force tile
 */
public class ForceUpTile extends Tiles {

    /**
     * This constructor is for a new ForceUpTile with its appropriate char symbol and sprite image
     */
    public ForceUpTile() {
        super('^');
        sprite = new ImageIcon("ForceUpTile.png");
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
        return chip.tryMove(0, -1, map);
    }
}
