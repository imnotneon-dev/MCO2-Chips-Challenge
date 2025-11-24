import javax.swing.ImageIcon;

public class IceTile extends Tiles {
    
    public IceTile() {
        super('I');
        sprite = new ImageIcon("IceTile.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
    if(inv.hasIceSkates())
            return true;
        else {
            //chip.slide();
            return false;
        }
    }
}
