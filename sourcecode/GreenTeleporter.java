import javax.swing.ImageIcon;

/**
 * This tile represents a GreenTeleporter tile on the map. They are considered teleportation tiles
 * and they are can be teleported upon when chip has a teleportation device in his inventory
 */
public class GreenTeleporter extends Tiles {
    
    /**
     * This constructor is for a new GreenTeleporter with its appropriate char symbol and sprite image
     */
    public GreenTeleporter() {
        super('G');
        sprite = new ImageIcon("GreenTeleporter.png");
    }

    /**
     * Determines whether chip can walk over the tile
     * GreenTeleporter tiles are walkable and when chip has a teleportation device in his inventory the
     * effect changes
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code true} by default it is walkable by chip
     *                
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return true;
    }

    /**
     * Determines whether chip has a teleportation device inside his inventory and if the destination is not null
     * This method is in charge of teleporting chip to another teleporter tile in the map if he has the teleportation device
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @return {@code true} if chip has a teleportation device and the destination of the other teleporter is not null
     *         {@ code false} if chip does not have a teleportation device inside his inventory
     *         {@ code false} if the other teleporter's destination is null              
     */
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
    
    /**
     * This method finds the other colored teleporter on the map once applyForce has been called by the game
     * if it was satisfied and returns the grid location of the teleporter
     * 
     * @param map               the current map layout that chip is interacting with
     * @param teleporterType    the specific teleporter type that chip has stepped upon 
     * @param currentX          the current x coordinate
     * @param currentY          the current y coordinate
     * @return an {@code int[]} contains the matching coordinates of the other teleportation tile
     *         or {@code null}  if no matching teleporter is found           
     */
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