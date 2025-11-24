import javax.swing.ImageIcon;

public class FireTile extends Tiles {
    
    public FireTile() {
        super('F');
        sprite = (new ImageIcon("FireTile.png"));
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        if(inv.hasFireBoots())
            return true;
        else {
            chip.die();
            return false;
        }
    }
}
