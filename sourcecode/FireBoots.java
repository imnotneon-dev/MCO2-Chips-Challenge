import javax.swing.ImageIcon;

public class FireBoots extends Tiles {
    
    public FireBoots() {
        super('L');
        sprite = new ImageIcon("FireBoots.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addFireBoots();
        return true;
    }
}