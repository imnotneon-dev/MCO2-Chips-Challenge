import javax.swing.ImageIcon;

public class BlueDoor extends Tiles {
    
    public BlueDoor() {
        super('B');
        sprite = new ImageIcon("BlueDoor.png");
    }

  @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return inv.hasBlueKey();
    }
}