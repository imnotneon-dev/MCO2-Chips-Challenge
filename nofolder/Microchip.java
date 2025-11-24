import javax.swing.ImageIcon;

public class Microchip extends Tiles {
    
    public Microchip() {
        super('#');
        sprite = new ImageIcon("Microchip.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addChips();
        return true;
    }
}