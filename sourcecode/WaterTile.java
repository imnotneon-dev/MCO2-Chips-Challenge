import javax.swing.ImageIcon;

public class WaterTile extends Tiles {
    
    public WaterTile() {
        super('W');
        sprite = new ImageIcon("WaterTile.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        if(inv.hasFlippers())
            return true;
        else {
            chip.die();
            return false;
        }
    }
}