import javax.swing.ImageIcon;

public class Flippers extends Tiles {
    
    public Flippers() {
        super('_');
        sprite = new ImageIcon("Flippers.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addFlippers();
        return true;
    }
}