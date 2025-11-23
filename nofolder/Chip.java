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
public class Chip {

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
    private char currentTileBelow = Tiles.BLANK;
    
    /** 
     * Constructor of Chip Class, creates a new Chip that accepts the starting position. 
     * 
     * @param startX - the starting x-coordinate
     * @param startY - the starting y-coordinate
     */
    public Chip(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.alive = true;
        this.INVENTORY = new Inventory(); 
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
                break;
            case 'A': 
                newX--; 
                break;
            case 'S': 
                newY++; 
                break;
            case 'D': 
                newX++; 
                break;
            default: 
                return "invalid";
        }

        if (!map.inBounds(newX, newY))
            return "blocked";

        char tile = map.getTile(newX, newY);

        Doors door = new Doors(tile); 
        if (door.isDoor(tile)) {
            if (door.unlockDoor(INVENTORY, tile)) {
                map.setTile(newX, newY, Tiles.BLANK);
                tile = Tiles.BLANK;
            } else {
                return "blocked";
            }
        }

        if (tile == Tiles.EXIT) {
            if (INVENTORY.getChips() >= map.getRequiredChips()) return "exit";
            else return "blocked";
        }

        if (Tiles.isWalkable(tile, INVENTORY, map.getRequiredChips())) {
            map.setTile(x, y, getCurrentTileBelow());

            x = newX;
            y = newY;

            setCurrentTileBelow(tile);

            if (Tiles.isCollectible(tile)) {
                switch (tile) {
                    case Inventory.CHIP: 
                        INVENTORY.addChips(); 
                        break;
                    case Inventory.RED_KEY: 
                        INVENTORY.addRedKey(); 
                        break;
                    case Inventory.BLUE_KEY: 
                        INVENTORY.addBlueKey();
                        break;
                    case Inventory.FIRE_BOOTS:
                        INVENTORY.addFireBoots(); 
                        break;
                    case Inventory.FLIPPERS:
                        INVENTORY.addFlippers();
                        break;
                }
                setCurrentTileBelow(Tiles.BLANK);
                map.setTile(x, y, Tiles.BLANK);
            }

            map.setTile(x, y, Chip.CHIP);

            if (Tiles.isForceTile(tile)) {
                Tiles.applyForce(this, map);
            }

            if (isAlive())
                return "moved";
            else
                return "died";
        }

        if (tile == Tiles.WATER) {
            if (INVENTORY.hasFlippers()) {
                map.setTile(x, y, getCurrentTileBelow());
                x = newX; 
                y = newY;
                setCurrentTileBelow(tile);
                map.setTile(x, y, Chip.CHIP);
                return "moved";
            } else {
                map.setTile(x, y, getCurrentTileBelow());
                x = newX; 
                y = newY;
                setCurrentTileBelow(tile);
                map.setTile(x, y, Chip.CHIP);
                die();
                map.setTile(x, y, tile); 
                return "died";
            }
        }

        if (tile == Tiles.FIRE) {
            if (INVENTORY.hasFireBoots()) {
                map.setTile(x, y, getCurrentTileBelow());
                x = newX; 
                y = newY;
                setCurrentTileBelow(tile);
                map.setTile(x, y, Chip.CHIP);
                return "moved";
            } else {
                map.setTile(x, y, getCurrentTileBelow());
                x = newX; 
                y = newY;
                setCurrentTileBelow(tile);
                map.setTile(x, y, Chip.CHIP);
                die();
                map.setTile(x, y, tile);
                return "died";
            }
        }

        return "blocked";
    }

    /**
     * This method collects an item that chip '@' has crossed upon and add the said item to chip's current inventory
     * @param item - the item that the character collected (chip, colored key, boot/flipper)
     */
    public void collect(char item) {
        switch(item) {
            case Inventory.CHIP:
                INVENTORY.addChips();;
                break;
            case Inventory.RED_KEY:
                INVENTORY.addRedKey();;
                break;
            case Inventory.BLUE_KEY:
                INVENTORY.addBlueKey();
                break;
            case Inventory.FIRE_BOOTS:
                INVENTORY.addFireBoots();
                break;
            case Inventory.FLIPPERS:
                INVENTORY.addFlippers();
                break;
        }
    }

    /**
     * Set chip's attribute "alive" to false, killing him in the game
     */
    public void die() {
        alive = false;
    }

    /**
    * Set chip's attribute "alive" to true, reviving him in the game
    */
    public void revive() {
        alive = true;
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

}
