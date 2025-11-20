package tiles;

import javax.swing.ImageIcon;

public class ExitTile extends Tiles {
    
    public ExitTile() {
        super('E');
        sprite = (new ImageIcon("images/ExitTile.png"));
    }

    @Override
    public boolean isWalkable(Inventory inv, int requiredChips) {
        return inv.getChips() >= requiredChips;
    }

}
