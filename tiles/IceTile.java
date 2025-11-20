package tiles;

import javax.swing.ImageIcon;

public class IceTile extends Tiles {
    
    public IceTile() {
        super('I');
        sprite = new ImageIcon("images/ForceTileRight.png");
    }

    public void onStep(Chip chip, Maps map) {
        chip.slide(map);
    }
}