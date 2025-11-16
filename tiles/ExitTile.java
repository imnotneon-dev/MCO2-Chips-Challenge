package tiles;

public class ExitTile extends Tiles {
    
    public ExitTile() {
        super('E');
        // will add sprite when GUI is implemented
    }

    @Override
    public boolean isWalkable(Inventory inv, int requiredChips) {
        return inv.getChips() >= requiredChips;
    }

}
