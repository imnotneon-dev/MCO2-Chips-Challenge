import javax.swing.ImageIcon;

public class PlayerTile extends Tiles {
    public PlayerTile() {
        super('@');
        this.sprite = new ImageIcon("Chip.png"); // Make sure this image exists
    } //overwritten code sample
    
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return false; // Other entities can't walk through the player
    }
}