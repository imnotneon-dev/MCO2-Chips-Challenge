package tiles;

public class IceTile extends Tiles {
    
    public IceTile() {
        super('I');
        // will add sprite when GUI is implemented
    }

    public void onStep(Chip chip, Maps map) {
        chip.slide(map);
    }
}