import javax.swing.ImageIcon;

public class RedKey extends Tiles {
    
    public RedKey() {
        super('r');
        sprite = new ImageIcon("RedKey.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addRedKey();
        return true;
    }
}