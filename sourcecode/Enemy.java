import javax.swing.ImageIcon;

/**
 * This Enemy class is for a new type of tile within chip's challenge, it is an obstacle of chip and he must avoid them
 * or else chip will die in the game
 *  
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Enemy extends Tiles {
    private int x;
    private int y;
    private int dx; // Direction: -1 or 1 for horizontal, 0 for vertical
    private int dy; // Direction: -1 or 1 for vertical, 0 for horizontal
    private char tileBelow; // What tile the enemy is standing on

    /**
     * This constructor is for a new Enemy tile with its appropriate char symbol and sprite image, it's default is a blank tile
     */
    public Enemy() {
        super('e');
        sprite = new ImageIcon("Enemy.png");
        this.tileBelow = ' ';
    }

    /**
     * This constructor is for a new Enemy tile with the appropriate starting positions and directions that they are going to
     * 
     * @param startX starting position of the x coordinate
     * @param startY starting position of the y coordinate
     * @param directionX direction going of the x coordinate
     * @param directionY direction going of the y coordinate
     */
    public Enemy(int startX, int startY, int directionX, int directionY) {
        super('e');
        sprite = new ImageIcon("Enemy.png");
        this.x = startX;
        this.y = startY;
        this.dx = directionX;
        this.dy = directionY;
        this.tileBelow = ' ';
    }

    /**
     * Determines whether chip can walk over the tile
     * Enemy tiles are not walkable and once chip walks to them, chip will instantly be killed
     * 
     * @param chip      the current chip of the player attempting to move to this tile
     * @param map       the current map layout that chip is interacting with
     * @param inv       the current inventory status of chip
     * @param requiredChips     the current chips needed to pass the level
     * @return {@code false} if chip walks onto an enemy tile    
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        chip.die();
        return false;
    }

    /**
     * Handles the movement of the enemy tiles
     * These tiles will determine if the enemy is in bounds, or facing a wall tile, it will return
     * once it faces an unwalkable tile
     * 
     * @param map       the current map layout that chip is interacting with
     * @param chip      the current chip of the player attempting to move to this tile 
     */
    public void move(Maps map, Chip chip) {
        int newX = x + dx;
        int newY = y + dy;

        if (!map.inBounds(newX, newY) || map.getTileObject(newX, newY).getSymbol() == 'X') {
            dx = -dx;
            dy = -dy;
            return;
        }

        if (newX == chip.getX() && newY == chip.getY()) {
            chip.die();
            return;
        }

        map.setTile(x, y, tileBelow);
        tileBelow = map.getTile(newX, newY);
        
        x = newX;
        y = newY;
        map.setTile(x, y, 'e');
    }

    // All the getters necessary for the enemy class
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setTileBelow(char tile) { this.tileBelow = tile; }
}