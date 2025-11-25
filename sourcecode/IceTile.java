import javax.swing.ImageIcon;

public class IceTile extends Tiles {
    
    public IceTile() {
        super('I');
        sprite = new ImageIcon("IceTile.png");
    }
    
    @Override
    public boolean applyForce(Chip chip, Maps map) {
        // If player has ice skates, don't slide
        if (chip.getInventory().hasIceSkates()) {
            return false;
        }

        // Determine slide direction based on the last move direction
        // We need to track this in Chip class
        int dx = chip.getLastMoveX();
        int dy = chip.getLastMoveY();

        // Try to continue sliding in the same direction
        return chip.tryMove(dx, dy, map);
    }
}
