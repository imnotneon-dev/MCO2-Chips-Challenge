import javax.swing.ImageIcon;

public class WaterTile extends Tiles {
    
    public WaterTile() {
        super('W');
        sprite = new ImageIcon("images/WaterTile.png");
    }

    @Override
    public boolean isWalkable(Inventory inv, int requiredChips) {
        return inv.hasFlippers();
    }

    @Override
    public void onStep(Chip chip, Maps map) {
        chip.die();
    }

}