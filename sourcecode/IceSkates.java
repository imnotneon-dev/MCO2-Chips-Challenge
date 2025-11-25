import javax.swing.ImageIcon;

public class IceSkates extends Tiles {
    
    public IceSkates() {
        super('Q');
        sprite = new ImageIcon("IceSkates.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addIceSkates();
        return true;
    }
}