import javax.swing.ImageIcon;

public class WallTile extends Tiles {
    
    public WallTile() {
        super('X');
        sprite = new ImageIcon("WallTile.png");
    }

    @Override 
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return false;
    }
}