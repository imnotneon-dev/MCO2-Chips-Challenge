/**
 * Inventory Class
 * 
 * - The available inventory of Chip, this class will change its attributes upon collection of items, reset of level, and interacting with doors
 * This class features the use of characters to identify which item will be acquired in the inventory
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Inventory {

    /**
     * Red keys to unlock red doors upon starting a game is set to 0 until chip finds one
     */
    private int redKeys = 0;

    /**
     * Blue keys to unlock blue doors upon starting a game is set to 0 until chip finds one
     */
    private int blueKeys = 0;

    /**
     * Determines if chip currently has acquired the flipper object to move through water tiles or not
     */
    private boolean hasFlippers;

    /**
     * Determines if chip currently has acquired the fire boots object to move through fire tiles or not
     */
    private boolean hasFireBoots;

    /**
     * Currently set to 0 upon starting a game until chip finds one and enough to unlock the exit of the level
     */
    private int chips = 0;

    /**
     * Character symbol representing the chip objects that chip needs to complete a level
     */
    public static final char CHIP = '#';

    /**
     * Character symbol representing the red key objects
     */
    public static final char RED_KEY = 'r';

    /**
     * Character symbol representing the blue key objects
     */
    public static final char BLUE_KEY = 'b';

    /**
     * Character symbol representing the flipper objects
     */
    public static final char FLIPPERS = '_';

    /**
    * Character symbol representing the fire boots objects
    */
    public static final char FIRE_BOOTS = 'L';

    /**
     * Constructor for the Inventory class that automatically sets the inventory to fire boots and flippers to false upon the start of the game
     */
    public Inventory() {
        hasFireBoots = false;
        hasFlippers = false;
    }

    /**
     * Add a red key onto the inventory if chip acquires one
     */
    public void addRedKey() {
        redKeys++;
    }

    /**
    * Add a blue key onto the inventory if chip acquires one
    */
    public void addBlueKey() {
        blueKeys++;
    }

    /**
    * Add a flipper equipment onto the inventory if chip acquires one
    */
    public void addFlippers() {
        hasFlippers = true;
    }

    /**
    * Add a fire boots equipment onto the inventory if chip acquires one
    */
    public void addFireBoots() {
        hasFireBoots = true;
    }

    /**
    * Add a chip onto the inventory if chip acquires one
    */
    public void addChips() {
        chips++;
    }

    /**
    * Determines if chip has a single red key
    * 
    * @return true - if red keys are currently 1 or more, false - if red keys are 0
    */
    public boolean hasRedKey() {
        return redKeys > 0;
    }

    /**
    * Determines if chip has a single blue key
    * 
    * @return true - if blue keys are currently 1 or more, false - if blue keys are 0
    */
    public boolean hasBlueKey() {
        return blueKeys > 0;
    }

    /**
    * Determines if chip has a flipper
    * 
    * @return true - if chip has a flipper object in the inventory, false - if not
    */
    public boolean hasFlippers() {
        return hasFlippers;
    }

    /**
    * Determines if chip has a fire boots
    * 
    * @return true - if chip has a fire boots object in the inventory, false - if not 
    */
    public boolean hasFireBoots() {
        return hasFireBoots;
    }

    /**
    * Determines how many chips the player has
    * 
    * @return chips - the current number of chips inside player's inventory
    */
    public int getChips() {
        return chips;
    }

    /**
    * Removes red key in inventory upon use on red door
    */
    public void useRedKey() {
        redKeys--;
    }

    /**
    * Removes blue key in inventory upon use on blue door
    */
    public void useBlueKey() {
        blueKeys--;
    }

    /**
    * Determines how many red keys the player has
    * 
    * @return redKeys - the current number of red keys inside player's inventory
    */
    public int getRedKeys() {
        return redKeys;
    }

    /**
    * Determines how many blue keys the player has
    * 
    * @return blueKeys - the current number of blue keys inside player's inventory
    */
    public int getBlueKeys() {
        return blueKeys;
    }

    /**
    * When the game calls resetLevel() it resets all the count of the items and the game will restart and simulates game repeat
    */
    public void resetInventory() {
        redKeys = 0;
        blueKeys = 0;
        hasFlippers = false;
        hasFireBoots = false;
        chips = 0;
    }

}
