/**
 * Main Class / Program
 * 
 * - For running the Chips Challenge application.
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Main {

    /**
     * The main static void class to run the program and the different functions of it, it runs the myFrame class and then runs the 
     * SoundManager class for the BGMusic to play while inside the game
     * 
     * @param args - accepts the argument of strings to simulate state of the program
     */    
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            MyFrame view = new MyFrame();
            SoundManager.playMusic("BGMusic.wav"); 
        });
    }
}
