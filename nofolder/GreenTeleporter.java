import javax.swing.ImageIcon;

public class GreenTeleporter extends Tiles {
    
    public GreenTeleporter() {
        super('G');
        sprite = new ImageIcon("GreenTeleporter.png");
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return true;
    }
    
    @Override
    public boolean applyForce(Chip chip, Maps map) {
        if (!chip.getInventory().hasTeleportationDevice()) {
            return false; 
        }
 
        int[] destination = findOtherTeleporter(map, 'G', chip.getX(), chip.getY());
        
        if (destination != null) {
            map.setTile(chip.getX(), chip.getY(), chip.getCurrentTileBelow());
            chip.setX(destination[0]);
            chip.setY(destination[1]);
            chip.setCurrentTileBelow('G'); 
            map.setTile(chip.getX(), chip.getY(), '@');
        }
        
        return false; 
    }
    
    private int[] findOtherTeleporter(Maps map, char teleporterType, int currentX, int currentY) {
        char[][] grid = map.getMap();
        
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == teleporterType && !(x == currentX && y == currentY)) {
                    return new int[]{x, y};
                }
            }
        }
        
        return null;
    }
}