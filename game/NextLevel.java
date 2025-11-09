/**
 * NextLevel Class
 * 
 * - Handles the progression and transition of map/level based on the player. 
 * This class works with the Maps class and Levels class
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class NextLevel {

    /**
     * Current level of the program. 1 for (level 1), etc.
     */
    private int currentLevel;
    /**
     * The levels needed and provides the map layouts for each level
     */
    private Maps[] levels;
    /**
     * The required chips for the user to pass onto the next level
     */
    private int[] requiredChips;

    /**
     * Constructor for the NextLevel class that accepts a map layout and required chips of that specific map
     * @param levels - a Maps object that gets the layout for the level
     * @param requiredChips - required chips needed for the user to pass
     */
    public NextLevel(Maps[] levels, int[] requiredChips) {
        this.levels = levels;
        this.requiredChips = requiredChips;
        this.currentLevel = 0;
    }

    /**
     * Gets the current map level and it gives the clone of it instantly to restore the original state
     * @return - it returns the original state of the current map
     */
    public Maps getCurrentMap() { 
        return levels[currentLevel].cloneMap();
    }

    /**
     * Gets the current level of the player
     * 
     * @return currentLevel - current level of the player (1,2,3)
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Accepts the collected chips of the player and decides if the player gets to pass or not.
     * @param collectedChips - current collection of chips by the player
     * @return true if collected chips are equal to the required chips of the current level, false if not
     */
    public boolean canAdvance(int collectedChips) {
        if (currentLevel >= requiredChips.length) 
            return false;
        return collectedChips >= requiredChips[currentLevel];
    }

    /**
     * Mainly for the transitioning of levels of the game. 
     * It increments the current level therefore it advances the player to the next level
     * @return true - if the current level is still within length (1/2), false - if the current level is already max (3)
     */
    public boolean advance() {
        if (currentLevel + 1 < levels.length) {
            currentLevel++;
            return true;
        }
        return false; 
    }
}
