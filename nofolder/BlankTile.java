package tiles;

public class BlankTile extends Tiles {
    
    public BlankTile() {
        super(' ');
        sprite = (new ImageIcon("images/BlankTile.png"));
    }

}