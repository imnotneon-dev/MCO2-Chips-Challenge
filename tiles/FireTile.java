package tiles;

import javax.swing.ImageIcon;

public class FireTile extends Tiles {
    
    public FireTile() {
        super('F');
        sprite = (new ImageIcon("images/FireTile.png"));
    }

    @Override
    public boolean isWalkable(Inventory inv, int requiredChips) {
        return inv.hasFireBoots();
    }

    @Override
    public void onStep(Chip chip, Maps map) {
        chip.die();
    }

}