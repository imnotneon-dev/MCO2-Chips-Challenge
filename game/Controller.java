/**
 *imports the java utility class Scanner for user input required for the movement of Chip
 */
import java.util.Scanner;

/**
 * Controller Class
 * 
 * - Manages the flow of the game, this includes the player inputs, progression of levels leading to map transitions, and the game state.
 * 
 * It initializes the levels of the game and controls the main loop, it also handles chip's movement and interactions, and the level reset upon death or level progression.
 * 
 * This class interacts with other classes like Chip, Maps, NextLevel, Display, and Levels to simulate gameplay logic
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Controller {

    /**
     * The array containing all the available game levels
     */
    private Maps[] levels;

    /**
     * The current Map object being played by the player
     */
    private Maps currentMap;

    /**
     * Has a new display object to show the state of the game to the player
     */
    private Display display = new Display();

    /**
     * The player's chip instance
     */
    private Chip chip;

    /**
     * Scanner instance for getting inputs from the player to control the game
     */
    private Scanner sc = new Scanner(System.in);

    /**
     * Required for the level progression 
     */
    private NextLevel nextLevel;

    /**
     * Constructor for Controller class initializes levels and nextLevel manager.
     * Sets up the game levels using predefined maps.
     */
    public Controller() {
        levels = new Maps[] {
            new Maps(Levels.generateMap1().getMap(), Levels.generateMap1().getChips()),
            new Maps(Levels.generateMap2().getMap(), Levels.generateMap2().getChips()),
            new Maps(Levels.generateMap3().getMap(), Levels.generateMap3().getChips())
        };

        int[] requiredChips = {
            Levels.generateMap1().getChips(),
            Levels.generateMap2().getChips(),
            Levels.generateMap3().getChips()
        };

        nextLevel = new NextLevel(levels, requiredChips);
    }

    /**
     * Starts this method upon selecting the 1. Start Game in the menu, it loads the first level and initializes the starting position. It displays the current level and begins the game loop method.
     */
    public void startGame() {
        currentMap = nextLevel.getCurrentMap();
        chip = new Chip(currentMap.getStartX(), currentMap.getStartY());
        System.out.println("Level " + (nextLevel.getCurrentLevel() + 1) + " Start!");
        gameLoop();
    }

    /**
     * The game loop that will continuously update the display upon player input, it handles if chip gets blocked or gets killed. Handles level progression and will run until player finishes all the levels or quits the game.
     */
    private void gameLoop() {
        while (true) {
            display.displayMap(currentMap.getMap());
            display.displayInventory(chip.getInventory(), currentMap);
            System.out.print("Move (W/A/S/D, Q to quit): ");
            char move = sc.next().toUpperCase().charAt(0);

            //Player option if they want to quit
            if (move == 'Q') {
                System.out.println("Quitting to menu...");
                break;
            }

            //Handles movement of chip
            String moved = chip.move(move, currentMap); 
            if (moved.equals("blocked")) {
                System.out.println("Cannot move there!");
            }

            //Gets current tile after chip moves / gets blocked
            char currentTile = currentMap.getTile(chip.getX(), chip.getY());

            //For level completion
            if (moved.equals("exit") && nextLevel.canAdvance(chip.getInventory().getChips())) {
                System.out.println("Level Complete!");

                if (nextLevel.advance()) {
                    currentMap = nextLevel.getCurrentMap();
                    chip.setX(currentMap.getStartX());
                    chip.setY(currentMap.getStartY());
                    chip.getInventory().resetInventory();
                    System.out.println("Level " + (nextLevel.getCurrentLevel() + 1) + " Start!");
                } else {
                    System.out.println("You completed all levels!");
                    break; 
                }
            }

            //For resetting current level when chip dies
            if (!chip.isAlive() && moved.equals("died")) {
                System.out.println("You died! Restarting level..."); 
                resetLevel(); 
            }
        }
    }

    /**
     * Resets the current level after chip dies, this method revives chip, clears chip's inventory, reset the map state to the original state and repositions chip back to the starting point
     */
    private void resetLevel() { 
        System.out.println(); 
        currentMap = nextLevel.getCurrentMap();
        
        chip.revive(); 
        chip.getInventory().resetInventory(); 
        currentMap = nextLevel.getCurrentMap();
        chip.setX(currentMap.getStartX()); 
        chip.setY(currentMap.getStartY());

        currentMap.setTile(chip.getX(), chip.getY(), Chip.CHIP); 
    }
}
