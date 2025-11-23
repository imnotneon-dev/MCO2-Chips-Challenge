import javax.swing.ImageIcon;

public class WallTile extends Tiles {
    
    public WallTile() {
        super('X');
        sprite = new ImageIcon("images/WallTile.png");
    }

    @Override
    public boolean isWalkable(Inventory inv, int requiredChips) {
        return false;
    }
}