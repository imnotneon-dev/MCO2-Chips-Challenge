import javax.swing.ImageIcon;

/** 
 * Chip Class
 * 
 * - Represents the player in the game
 * This class manages the player's position, inventory, interaction with other classes (collectibles, doors, hazard tiles, exit tile), 
 * and takes control of the movement of the player
 * 
 * This class tracks where the player is alive and it tracks the type of tile below it
 * 
 * This class interacts with other classes like Maps, Tiles, Doors, and Inventory to provide the simulation of the gameplay
 * @author Jenrick Lim, Ryan Malapitan (S16)
*/
public class Chip extends Tiles {

    /**
     *  The '@' character represents Chip / Player in the game 
     */
    public static final char CHIP = '@';

    /**
     * Player's current x-coordinate on the map 
     */
    private int x;

    /**
     * Player's current y-coordinate on the map 
     */
    private int y;

    /**
     * Player's status if they're alive or not (true = alive, false = dead) 
     */
    private boolean alive;

    /** 
     * Player's current inventory that simulates Chip carrying chips, keys, fire boots, and flippers 
     */
    private final Inventory INVENTORY;

    /** 
     * Stores the tile below Chip to ensure fire, water, force tiles will not disappear after Chip steps on them 
     */
    private char currentTileBelow = ' '; // Blank tile

    /**
     * Stores the last X-move of Chip
     */
    private int lastMoveX = 0;

    /**
     * Stores the last Y-move of Chip
     */
    private int lastMoveY = 0;
    
    /** 
     * Constructor of Chip Class, creates a new Chip that accepts the starting position. 
     * 
     * @param startX - the starting x-coordinate
     * @param startY - the starting y-coordinate
     */
    public Chip(int startX, int startY) {
        super(CHIP); // Call Tiles constructor with '@' symbol
        this.x = startX;
        this.y = startY;
        this.alive = true;
        this.INVENTORY = new Inventory();
        this.currentTileBelow = ' '; // Start with blank tile below
        this.sprite = new ImageIcon("Chip.png"); // Set the sprite
    }

    /** 
     * Implement the abstract isWalkable method from Tiles
     * Since Chip is the player, this determines if the player can walk on other tiles
     * For the Chip tile itself, it should not be walkable by other entities
     */
    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        // Chip tile itself is not walkable (other entities can't walk through the player)
        return false;
    }

    /** 
     * Moves chip in the direction that depends on the player input. Handles collision with walls and doors, the walkable feature that kills chip if he steps on fire/water without equipment, the movement of chip with force tiles. Also manages the interaction of Chip with doors, hazard tiles, force tiles, exit tile)
     * 
     * @param direction - the movement input by the player ('W', 'A', 'S', 'D')
     * @param map - the current game map depending on level
     * @return a string that indicates the result upon calling move ("moved", "blocked", "died", "exit", "invalid")
     */
    public String move(char direction, Maps map) { 
        int newX = x;
        int newY = y;

        switch (direction) {
            case 'W': 
                newY--; 
                lastMoveX = 0;
                lastMoveY = -1;
                break;
            case 'A': 
                newX--; 
                lastMoveX = -1;
                lastMoveY = 0;
                break;
            case 'S': 
                newY++; 
                lastMoveX = 0;
                lastMoveY = 1;
                break;
            case 'D': 
                newX++; 
                lastMoveX = 1;
                lastMoveY = 0;
                break;
            default: 
                return "invalid";
        }

        // Check if move is within bounds
        if (!map.inBounds(newX, newY)) {
            return "blocked";
        }

        // Get the target tile object for interaction
        Tiles targetTile = map.getTileObject(newX, newY);
        
        // Use the tile's isWalkable method to check if movement is allowed
        if (!targetTile.isWalkable(this, map, INVENTORY, map.getRequiredChips())) {
            return "blocked";
        }

        char targetTileType = targetTile.getSymbol();

        // Handle exit tile
        if (targetTileType == TileRegistry.EXIT) {
            if (INVENTORY.getChips() >= map.getRequiredChips()) {
                // Move to exit position first
                map.setTile(x, y, currentTileBelow);
                x = newX;
                y = newY;
                currentTileBelow = targetTileType;
                map.setTile(x, y, CHIP);
                return "exit";
            } else {
                return "blocked"; // Not enough chips
            }
        }

        // Handle collectible items
        if (isCollectible(targetTileType)) {
            collect(targetTileType);
            // Remove the collectible from the map after collection
            map.setTile(newX, newY, ' ');
            targetTileType = ' '; // Now it's blank after collection
        }

        // Move the player
        map.setTile(x, y, currentTileBelow); // Restore previous tile
        x = newX;
        y = newY;
        currentTileBelow = targetTileType; // Remember what's under us now
        map.setTile(x, y, CHIP); // Place player at new position

        // Handle hazard tiles (fire, water) - the tile's isWalkable method should handle this
        // If we reached here, the tile is walkable, so just check if it's hazardous
        handleHazardTile(targetTileType);
        if (!alive) {
            return "died";
        }

        Controller.applyForce(this, map);

        return "moved";
    }

    /**
     * Check if a tile type is collectible
     */
    private boolean isCollectible(char tileType) {
        return tileType == '#' || // Chip item
               tileType == 'r' || // Red key
               tileType == 'b' || // Blue key
               tileType == 'L' || // Fire boots
               tileType == '_' || // Flippers
               tileType == 'Q' || // Ice Skates
               tileType == 't';   // Teleporation Device
    }

    /**
     * Handle hazard tiles that might kill the player
     */
    private void handleHazardTile(char tileType) {
        switch (tileType) {
            case 'W': // Water
                if (!INVENTORY.hasFlippers()) {
                    die();
                }
                break;
            case 'F': // Fire
                if (!INVENTORY.hasFireBoots()) {
                    die();
                }
                break;
        }
    }

    /**
     * This method collects an item that chip '@' has crossed upon and add the said item to chip's current inventory
     * @param item - the item that the character collected (chip, colored key, boot/flipper)
     */
    public void collect(char item) {
        switch(item) {
            case '#': // Chip item
                INVENTORY.addChips();
                break;
            case 'r': // Red key
                INVENTORY.addRedKey();
                break;
            case 'b': // Blue key
                INVENTORY.addBlueKey();
                break;
            case 'L': // Fire boots
                INVENTORY.addFireBoots();
                break;
            case 'F': // Flippers
                INVENTORY.addFlippers();
                break;
            case 'Q': // Ice Skates
                INVENTORY.addIceSkates();
                break;
            case 't': // TP Device
                INVENTORY.addTeleportationDevice();
                break;
        }
    }

    public boolean tryMove(int dx, int dy, Maps map) {
        int newX = x + dx;
        int newY = y + dy;

        if (!map.inBounds(newX, newY)) return false;

        Tiles next = map.getTileObject(newX, newY);

        if (!next.isWalkable(this, map, INVENTORY, map.getRequiredChips()))
            return false;

        char nextSymbol = next.getSymbol();

        if (isCollectible(nextSymbol)) {
            collect(nextSymbol);
            nextSymbol = ' ';
        }

        map.setTile(x, y, currentTileBelow); 
        x = newX;
        y = newY;
        
        if (currentTileBelow == 'I') {
            lastMoveX = dx;
            lastMoveY = dy;
        }
        
        currentTileBelow = nextSymbol;
        map.setTile(x, y, CHIP); 

        handleHazardTile(nextSymbol);
        if (!alive) return false;

        return true;
    }

    /**
     * Set chip's attribute "alive" to false, killing him in the game
     */
    public void die() {
        alive = false;
        sprite = new ImageIcon("ChipDead.png");
    }

    /**
    * Set chip's attribute "alive" to true, reviving him in the game
    */
    public void revive() {
        alive = true;
        sprite = new ImageIcon("Chip.png");
    }

    /**
     * Returns chip's current x-coordinate
     * 
     * @return - x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns chip's current y-coordinate
     * 
     * @return - y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets chip's current x-coordinate
     * 
     * @param x - new x-coordinate of chip
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets chip's current y-coordinate
     * 
     * @param y - new y-coordinate of chip
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns whether Chip is alive/dead
     * 
     * @return true if alive = 'true', false if alive = 'false'
     */
    public boolean isAlive() {
        return alive;
    }
   
   /**
    * Returns chip's current inventory 
    * 
    * @return the current inventory of chip
    */
    public Inventory getInventory() {
        return INVENTORY;
    }

   /**
    * Returns chip's current tile below him 
    * 
    * @return the character of the tile below
    */
    public char getCurrentTileBelow() {
        return currentTileBelow;
    }

   /**
    * Sets chip's current tile below him 
    * 
    * @param tile - the tile character to be set as the new one below chip
    */
    public void setCurrentTileBelow(char tile) {
        this.currentTileBelow = tile;
    }

    /**
     * Get the sprite for rendering
     */
    public ImageIcon getSprite() {
        return sprite;
    }

    /**
     * Get the last X-coordinate of Chip 
     */
    public int getLastMoveX() {
    return lastMoveX;
}
    /**
     * Get the last Y-coordinate of Chip 
     */
    public int getLastMoveY() {
        return lastMoveY;
    }
}