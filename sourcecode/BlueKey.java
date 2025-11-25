import javax.swing.ImageIcon;

public class BlueKey extends Tiles {
    
    public BlueKey() {
        super('b');
        sprite = new ImageIcon("BlueKey.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return true;
    }
}