/**
 *imports the java utility class Scanner for user input required for the navigation of menu
 */
import java.util.Scanner;

/**
 * Display Class
 *
 * - Manages all the text displayed on the player's console / terminal that shows the game/menu state. 
 * It handles the display of the menu, current map with chip and tiles, and the player's current inventory. 
 * This class works with other classes like Controller, Maps, and Inventory in order to show the player the current state of the game.
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Display {

    /**
     * Scanner instance for menu options
     */
    private Scanner sc = new Scanner(System.in);

    /**
     * Method shows the player the main menu of the game, including start game, instructions manual, 
     * and exit program options, it will print invalid input if the player inputs an invalid number/character. 
     * It will call the method of the controller class startGame if the input is 1.
     * @param controller - instance of controller class to call startGame method
     */
    public void showMainMenu(Controller controller) {
        while (true) {
            System.out.println("=== CHIP'S CHALLENGE ===");
            System.out.println("1. Start Game");
            System.out.println("2. Instructions");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    controller.startGame(); 
                    break;
                case 2:
                    showInstructions();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    /**
     * Responsible for showing the user the instructions of the game, on how to play and the objectives, 
     * this is triggered when the user inputs 2 on the main menu.
     */
    private void showInstructions() {
        System.out.println("\nHOW TO PLAY:");
        System.out.println("W/A/S/D - move");
        System.out.println("Collect all chips to unlock the exit!");
        System.out.println("Collect colored keys to unlock colored doors!"); 
        System.out.println("Avoid fire and water unless you have items!");
        System.out.println();
    }

    /**
     * Displaying the current map and its state 
     * Each tile is printed to display the map board
     * 
     * @param map - array of characters from Maps to represent the current map layout
     */
    public void displayMap(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                    System.out.print(map[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Shows the current state of the player's inventory, if the player has colored keys, fire boot or flippers, and shows the player the chips they have acquired for them to exit the level.
     * 
     * @param inventory - gets the current inventory of the player to display its current state
     * @param map - contains the specific map level and the required chips for the player to exit
     */
    public void displayInventory(Inventory inventory, Maps map) {
        System.out.println("Inventory:");
        System.out.print("Chips: " + (map.getRequiredChips()-inventory.getChips()));
        if (inventory.hasFireBoots())
            System.out.print(" Fire Boots: YES");
        else
            System.out.print(" Fire Boots: NO");
        if (inventory.hasFlippers())
            System.out.print(" Flippers: YES");
        else
            System.out.print(" Flippers: NO");
        System.out.print(" Red Key: " + inventory.getRedKeys());
        System.out.print(" Blue Key: " + inventory.getBlueKeys());
        System.out.println();
        System.out.println();
    }
}
