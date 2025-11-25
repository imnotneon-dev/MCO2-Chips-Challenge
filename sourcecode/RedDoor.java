import javax.swing.ImageIcon;

public class RedDoor extends Tiles {
    
    public RedDoor() {
        super('R');
        sprite = new ImageIcon("RedDoor.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return inv.hasRedKey();
    }
}