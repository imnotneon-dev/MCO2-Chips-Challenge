/**
 * Tiles Class
 * 
 * - Represents all the tiles used in the map layouts, it is responsible for giving property of the special tiles including hazard tiles (water and fire), force tiles, and exit tiles. It is the one used by the Controller class (isWalkable) and Maps class to simulate the game and its objectives. 
 * This class also handles the action of the force tiles when chip has stepped upon them, pushing him into the direction where the force tile has ended or an obstacle/hazard is the next tile.
 * 
 * This class works with the Chip class, Inventory class, Maps class, Doors class as it handles the movement and interaction and collision of chip to special and blank tiles
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */

import javax.swing.ImageIcon;

public abstract class Tiles {

    private char symbol;
    private Item itemOnTile;  
    protected ImageIcon sprite;

    public Tiles(char symbol) {
        this.symbol = symbol;
    }

    public void setTiles(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setItem(Item item) {
        this.itemOnTile = item;
    }

    public Item getItem() {
        return itemOnTile;
    }

    public ImageIcon getSprite() {
        return sprite;
    }

    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return true;
    }

    //public void onStep(Chip chip, Maps map){};
        // if (itemOnTile != null) {
        //     chip.getInventory().addItem(itemOnTile); // add to inventory
        //     itemOnTile = null; // remove from map
        // }
    

    // public static final char BLANK = ' ';
    // public static final char WATER = 'W';
    // public static final char FIRE = 'F';
    // public static final char WALL = 'X';
    // public static final char EXIT = 'E';
    // public static final char FORCE_UP = '^';
    // public static final char FORCE_DOWN = 'v';
    // public static final char FORCE_LEFT = '<';
    // public static final char FORCE_RIGHT = '>';
    
    // /**
    //  * Constructor for the Tiles class with specified tile type
    //  * 
    //  * @param type - accepts character symbol that represents a tile type
    //  */
    // public Tiles(char type) {
    //     this.type = type;
    // }

    // /**
    //  * Gets the specific type of the tile
    //  * 
    //  * @return type - returns character type of tile
    //  */
    // public char getType() {
    //     return type;
    // }

    // /**
    //  * Sets the tile to a specific type
    //  * 
    //  * @param newType - the newType replacing the type inside
    //  */
    // public void setType(char newType) {
    //     this.type = newType;
    // }

    // /**
    //  * Has conditions if hazard tile has been stepped on, requires flippers/fire boots, enables collision on walls, logic for exit tile (chips) and rest of types is walkable
    //  * 
    //  * @param tile - specific type of tile that method accepts
    //  * @param inv - current inventory of player
    //  * @param requiredChips - required chips of current map
    //  * @return true if satisfied inventory conditions / not wall, false if conditions hasn't been met
    //  */
    // public static boolean isWalkable(char tile, Inventory inv, int requiredChips) {
    //     switch(tile) {
    //         case BLANK:
    //         case Inventory.CHIP:
    //         case FORCE_UP:
    //         case FORCE_DOWN:
    //         case FORCE_LEFT:
    //         case FORCE_RIGHT:
    //         case Inventory.RED_KEY:
    //         case Inventory.BLUE_KEY:
    //         case Inventory.FLIPPERS:
    //         case Inventory.FIRE_BOOTS:
    //         case Doors.RED_DOOR:
    //         case Doors.BLUE_DOOR:
    //             return true;
    //         case WATER:
    //             return inv.hasFlippers();
    //         case FIRE:
    //             return inv.hasFireBoots();
    //         case WALL:
    //             return false;
    //         case EXIT:
    //             return inv.getChips() >= requiredChips;
    //         default:
    //             return false;
    //     }
    // }

    // /**
    //  * Determines if tile is a force tile
    //  * 
    //  * @param tile - accepts tile
    //  * @return true - if tile parameter is a force tile, false - if not a force tile
    //  */
    // public static boolean isForceTile(char tile) {
    //     return tile == FORCE_UP || tile == FORCE_DOWN || tile == FORCE_LEFT || tile == FORCE_RIGHT;
    // }

    // /**
    //  * Determines if the tile is a collectible
    //  * 
    //  * @param tile - accepts tile
    //  * @return true if tile is chip/key/fireboots/flippers, false if tile is not a collectible
    //  */
    // public static boolean isCollectible(char tile) {
    //     return tile == Inventory.CHIP || tile == Inventory.RED_KEY || tile == Inventory.BLUE_KEY || tile == Inventory.FLIPPERS || tile == Inventory.FIRE_BOOTS;
    // }

    // /**
    //  * Gets the direction of the force tile
    //  * 
    //  * @param tile - accepts tile
    //  * @return FORCE_UP if tile is a FORCE_UP, FORCE_DOWN if tile is a FORCE_DOWN, FORCE_LEFT if tile is a FORCE_LEFT, FORCE_RIGHT if tile is a FORCE_RIGHT, '' if tile is not a force tile
    //  */
    // public static char getForceDirection(char tile) {
    //     if (tile == FORCE_UP) 
    //         return FORCE_UP;
    //     if (tile == FORCE_DOWN)
    //         return FORCE_DOWN;
    //     if (tile == FORCE_LEFT)
    //         return FORCE_LEFT;
    //     if (tile == FORCE_RIGHT)
    //         return FORCE_RIGHT;
    //     return ' ';
    // }

    // /**
    //  * Method for force tiles, applies force depending on the direction of the force tile and places chip where force tiles end or if next tile is a wall or hazard. This method also allows the collection of items when the force tile puts chip into their tile.
    //  * @param chip - accepts chip and current position of chip
    //  * @param map - accepts map layout and used to determine where to place chip
    //  */
    // public static void applyForce(Chip chip, Maps map) {
    //     while (true) {
    //         char tileUnder = map.getTile(chip.getX(), chip.getY());
    //         if (!isForceTile(tileUnder)) {
    //             tileUnder = chip.getCurrentTileBelow();
    //             if (!isForceTile(tileUnder)) 
    //                 break;
    //         }

    //         int oldX = chip.getX();
    //         int oldY = chip.getY();
    //         int newX = oldX;
    //         int newY = oldY;

    //         switch (tileUnder) {
    //             case FORCE_UP:    
    //                 newY--; 
    //                 break;
    //             case FORCE_DOWN:  
    //                 newY++; 
    //                 break;
    //             case FORCE_LEFT:  
    //                 newX--; 
    //                 break;
    //             case FORCE_RIGHT: 
    //                 newX++; 
    //                 break;
    //             default: 
    //                 break;
    //         }

    //         if (!map.inBounds(newX, newY)) 
    //             break;

    //         char nextTile = map.getTile(newX, newY);

    //         if (!isWalkable(nextTile, chip.getInventory(), map.getRequiredChips()))
    //             break;

    //         map.setTile(oldX, oldY, tileUnder);

    //         chip.setX(newX);
    //         chip.setY(newY);

    //         chip.setCurrentTileBelow(nextTile);

    //         if (isCollectible(nextTile)) { 
    //             switch (nextTile) {
    //                 case Inventory.CHIP:
    //                     chip.getInventory().addChips();
    //                     break;
    //                 case Inventory.RED_KEY:
    //                     chip.getInventory().addRedKey();
    //                     break;
    //                 case Inventory.BLUE_KEY:
    //                     chip.getInventory().addBlueKey();
    //                     break;
    //                 case Inventory.FLIPPERS:
    //                     chip.getInventory().addFlippers();
    //                     break;
    //                 case Inventory.FIRE_BOOTS:
    //                     chip.getInventory().addFireBoots();
    //                     break;
    //             }
    //             chip.setCurrentTileBelow(BLANK);
    //             map.setTile(newX, newY, BLANK);
    //         }

    //         if (nextTile == WATER && !chip.getInventory().hasFlippers()) {
    //             chip.die();
    //             map.setTile(newX, newY, WATER);
    //             return;
    //         }
    //         if (nextTile == FIRE && !chip.getInventory().hasFireBoots()) {
    //             chip.die();
    //             map.setTile(newX, newY, FIRE);
    //             return;
    //         }

    //         map.setTile(newX, newY, Chip.CHIP);
    //         if (!isForceTile(nextTile)) 
    //             break;
    //     }
    // }
}
