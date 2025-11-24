import javax.swing.ImageIcon;

public class BlankTile extends Tiles {
    
    public BlankTile() {
        super(' ');
        sprite = (new ImageIcon("images/BlankTile.png"));
    }
}
