
package game;

/**
 * Main Class / Program
 * 
 * - For running the Chips Challenge application.
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Main {

    /**
     * The main static void class to run the program and the different functions of it
     * 
     * @param args - accepts the argument of strings to simulate state of the program
     */    
    public static void main(String[] args) {
        // // Creates a new Display instance that is necessary for the menu and map layout and game state display for the user to see
        // Display menu = new Display();
        // //Creates a new Controller instance that is necessary for the gameplay of the player mainly chips movement and interaction with levels
        // Controller controller = new Controller();
        // //Calls the controller method and shows the main menu to simulate the start of the program for the player
        // menu.showMainMenu(controller);

        javax.swing.SwingUtilities.invokeLater(() -> {
            MyFrame view = new MyFrame();
            Sound.playMusic("sound/Chip's Challenge Music.wav");
        });
    }
}
