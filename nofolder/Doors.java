/**
 * Doors Class
 * 
 * - Represent the door tiles the player can find in the game. Each door is a specific color and can be red or blue, to unlock/clear a door the player must possess the specific key in their inventory to pass through. 
 * This class handles locking doors upon starting game, unlocking when player has keys and checking if the key is correct.
 * 
 * Doors are represented by symbols with Red door = 'R' and Blue door = 'B'
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Doors {

    /**
     * Indicates if the door is locked 'true' or unlocked 'false'
     */
    private boolean locked;

    /**
     * Indicates the color of the door depending on its character Red door 'R' and Blue door 'B'
     */
    private char color;

    /**
     * Character for Red door tile
     */
    public static final char RED_DOOR = 'R';

    /**
     * Character for Blue door tile
     */
    public static final char BLUE_DOOR = 'B';

    /**
     * Constructor for the Doors class, it accepts the color of the door and sets the locked is true and sets specific color
     * 
     * @param color - the color of the door (red/blue)
     */
    public Doors(char color) {
        this.locked = true;
        this.color = color;
    }

    /**
     * Checks if the next tile that chip will step upon is a door object, can be blue or red door
     * 
     * @param tile - accepts tile and checks if it is a door of either color 
     * @return true - if tile is either a blue or red door, false - if not a door
     */
    public boolean isDoor(char tile) {
        return tile == RED_DOOR || tile == BLUE_DOOR;
    }

    /**
     * Checks if the door that chip is interacting with is locked
     * 
     * @return true = if door is locked, false = if door is unlocked 
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Unlocks the door and interacts with the keys of chip, if the specific conditions are met as the door is locked and the player has the specific key, the door will unlock and the method will return true.
     * 
     * @param inv - checks the current state of chip's inventory
     * @param tile - checks the tile if it is a door object
     * @return true = if the specific conditions are met (door object and possessing key), false = if the specific conditions are not met by both red and blue door checking
     */
    public boolean unlockDoor(Inventory inv, char tile) {
        if (tile == RED_DOOR && inv.hasRedKey()) {
            inv.useRedKey();
            locked = false;
            return true;
        }
        else if (tile == BLUE_DOOR && inv.hasBlueKey()) {
            inv.useBlueKey();
            locked = false;
            return true;            
        }
        return false;
    }

}
