import javax.swing.ImageIcon;

public class ExitTile extends Tiles {
    
    public ExitTile() {
        super('E');
        sprite = (new ImageIcon("ExitTile.png"));
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return inv.getChips() >= requiredChips;
    }
}
