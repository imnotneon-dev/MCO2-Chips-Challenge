import javax.swing.ImageIcon;

/**
 * This tile represents a blank tile on the map. They are the default walkable tile and chip can move
 * within these tiles
 */
public class BlankTile extends Tiles {
    
    /**
     * This constructor is for a new BlankTile with its appropriate char symbol and sprite image
     */
    public BlankTile() {
        super(' ');
        sprite = (new ImageIcon("BlankTile.png"));
    }
}