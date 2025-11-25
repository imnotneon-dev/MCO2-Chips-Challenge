import javax.swing.ImageIcon;

public class TeleportationDevice extends Tiles {
    
    public TeleportationDevice() {
        super('t');
        sprite = new ImageIcon("TPDevice.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        inv.addTeleportationDevice();
        return true;
    }
}