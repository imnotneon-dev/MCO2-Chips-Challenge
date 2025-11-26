import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class features the main panel of the game, it contains the map panel for the level and game simulation, level selection to choose from levels,
 * an instructions tab to figure out the layout of the game, a mute button for all of the tabs for the BGMusic, and an exit to main menu 
 * and an exit game feature
 * 
 * Buttons and Sprites appear here for the player to see with set fonts and the proper frame size
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class MyFrame extends JFrame {
    private JPanel mainMenuPanel;
    private MapPanel gamePanel;
    private Controller gameController;
    private BufferedImage backgroundImage;
    private JButton muteButton;

    /**
     * This is the main constructor for the MyFrame class, it features the Title of "Chip's Challenge" the background image
     * and the main menu of the game
     */
    public MyFrame() {
        this.setTitle("Chips Challenge");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        loadBackgroundImage();
        createMainMenu();
        this.setContentPane(mainMenuPanel);
        this.setVisible(true);
    }
       
    /**
     * This is the helper function to load the background image of the game
     */
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("background.png"));
        } catch (Exception e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    /**
     * This is the function in order to create the main menu panel of the game, it features the play level 1 button
     * select level button, instructions button, and the exit button
     * 
     * This method also handles the font styling and sizing of each button and their action listeners when you click the buttons
     */
    private void createMainMenu() {
        mainMenuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainMenuPanel.setLayout(null);

        JButton playButton = new JButton("Play Level 1");
        JButton selectLevelButton = new JButton("Select Level");
        JButton instructionsButton = new JButton("Instructions");
        JButton exitButton = new JButton("Exit");
        JButton muteButton = SoundManager.createMuteButton();
        mainMenuPanel.add(muteButton);

        playButton.setBounds(190, 350, 300, 60);
        selectLevelButton.setBounds(190, 420, 300, 60);
        instructionsButton.setBounds(190, 490, 300, 60);
        exitButton.setBounds(190, 560, 300, 60);

        Font buttonFont = new Font("Courier New", Font.BOLD, 18);
        playButton.addActionListener(e -> startGame(0));

        selectLevelButton.addActionListener(e -> showLevelSelector());

        instructionsButton.addActionListener(e -> showInstructions());

        exitButton.addActionListener(e -> System.exit(0));

        playButton.setFont(buttonFont);
        selectLevelButton.setFont(buttonFont);
        instructionsButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        mainMenuPanel.add(playButton);
        mainMenuPanel.add(selectLevelButton);
        mainMenuPanel.add(instructionsButton);
        mainMenuPanel.add(exitButton);
    }

    /**
     * This is the function to start the game of Chip's Challenge, it calls the controller and mapPanel class for it to work,
     * it accepts the starting level as a parameter to choose what level to start with
     * 
     * @param startingLevel the number of level that the game will start and run
     */
    public void startGame(int startingLevel) {
        gameController = new Controller();
        gameController.setMainMenu(this);
        gameController.setStartingLevel(startingLevel);
        
        gamePanel = new MapPanel(gameController);
        this.setContentPane(gamePanel);
        this.pack();  
        this.revalidate();
        gamePanel.requestFocusInWindow();
    }

    /**
     * This method helps the player to show the levels 1, 2, and 3 for them to choose on
     * This method helps the player to start a level of their choice
     */
        private void showLevelSelector() {
        JPanel levelSelectPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        levelSelectPanel.setLayout(null);

        JLabel titleLabel = new JLabel("SELECT LEVEL", JLabel.CENTER);
        titleLabel.setBounds(35, 300, 600, 40);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 32));
        titleLabel.setForeground(java.awt.Color.WHITE);
        levelSelectPanel.add(titleLabel);

        JButton muteButton = SoundManager.createMuteButton();
        levelSelectPanel.add(muteButton);

        JButton level1Button = new JButton("Level 1 - Beginner");
        JButton level2Button = new JButton("Level 2 - Intermediate");
        JButton level3Button = new JButton("Level 3 - Advanced");
        JButton backButton = new JButton("Back to Main Menu");

        level1Button.setBounds(190, 350, 300, 60);
        level2Button.setBounds(190, 420, 300, 60);
        level3Button.setBounds(190, 490, 300, 60);
        backButton.setBounds(240, 560, 200, 50);

        Font levelFont = new Font("Courier New", Font.BOLD, 18);
        level1Button.setFont(levelFont);
        level2Button.setFont(levelFont);
        level3Button.setFont(levelFont);
        backButton.setFont(levelFont);

        java.awt.Cursor handCursor = new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR);
        level1Button.setCursor(handCursor);
        level2Button.setCursor(handCursor);
        level3Button.setCursor(handCursor);
        backButton.setCursor(handCursor);

        level1Button.addActionListener(e -> startGame(0)); 
        level2Button.addActionListener(e -> startGame(1));
        level3Button.addActionListener(e -> startGame(2)); 
        backButton.addActionListener(e -> returnToMainMenu());

        levelSelectPanel.add(level1Button);
        levelSelectPanel.add(level2Button);
        levelSelectPanel.add(level3Button);
        levelSelectPanel.add(backButton);

        this.setContentPane(levelSelectPanel);
        this.revalidate();
        this.repaint();
    }

    /**
     * This method handles the panel for the instructions panel, it has the text for the instructions for the player to see,
     * it features a scrollable pane with text, a mute button and a back to main menu button
     * 
     * @return the instructions panel that was made by fonts and button styles
     */
    private JPanel createInstructionsPanel() {
    JPanel instructionsPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    };
    instructionsPanel.setLayout(null);

        JLabel titleLabel = new JLabel("CHIP'S CHALLENGE - INSTRUCTIONS", JLabel.CENTER);
        titleLabel.setBounds(50, 40, 600, 40);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 28));
        titleLabel.setForeground(java.awt.Color.WHITE);
        instructionsPanel.add(titleLabel);
        JButton muteButton = SoundManager.createMuteButton();
        instructionsPanel.add(muteButton);

        JTextArea instructionsText = new JTextArea();
        instructionsText.setText(
        "GAME OBJECTIVE:\n" +
        "• Collect all microchips on each level\n" +
        "• Reach the exit portal to advance to the next level\n" +
        "• Avoid hazards and solve puzzles to help Chip progress!\n\n" +
        
        "CONTROLS:\n" +
        "• W or ↑ Arrow - Move Up\n" +
        "• A or ← Arrow - Move Left\n" +
        "• S or ↓ Arrow - Move Down\n" +
        "• D or → Arrow - Move Right\n" +
        "• ESC - Return to Main Menu\n\n" +
        
        "ITEMS AND TILES:\n" +
        "• # - Computer Chip (collect required amount to win)\n" +
        "• r - Red Key (opens Red Doors)\n" +
        "• R - Red Door (requires Red Key)\n" +
        "• b - Blue Key (opens Blue Doors)\n" +
        "• B - Blue Door (requires Blue Key)\n" +
        "• @ - Chip (the player character)\n" +
        "• E - Exit Portal (finish level once chips has been collected)\n" +
        "• X - Walls (blocks movement of chip)\n" +
        "• W - Water (deadly without flippers)\n" +
        "• _ - Flippers (protect from water tiles)\n" +
        "• F - Fire (deadly without fire boots)\n" +
        "• L - Fire Boots (protect from fire tiles)\n" +
        "• I - Ice (slippery surface)\n" +
        "• Q - Ice Skates (makes you move in ice tiles)\n" +
        "• P - Purple Teleporter (makes you teleport if you have the teleportation device)\n" +
        "• G - Green Teleporter (makes you teleport if you have the teleportation device)\n" +
        "• t - Teleportation Device (makes you teleport through teleporters)\n" +
        "• e - Enemy (kills chip if he walks toward them)\n\n" +
        
        
        "GAME RULES:\n" +
        "• You MUST collect ALL required chips before exiting the portal!\n" +
        "• Doors remain locked without the correct colored key!\n" +
        "• Hazard tiles will kill you without the proper equipment!\n" +
        "• If you die, the level restarts!\n" +
        "• Complete all levels to win the game!!"
    );
    
        instructionsText.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsText.setForeground(java.awt.Color.WHITE);
        instructionsText.setBackground(new java.awt.Color(0, 0, 0, 180));
        instructionsText.setOpaque(true);
        instructionsText.setEditable(false);
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(instructionsText);
        scrollPane.setBounds(50, 260, 600, 290);
        scrollPane.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        instructionsPanel.add(scrollPane);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBounds(245, 560, 200, 50);
        backButton.setFont(new Font("Courier New", Font.BOLD, 18));
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> returnToMainMenu());
    
    instructionsPanel.add(backButton);
    return instructionsPanel;
    }

    /**
     * This method handles the showing of instructions to the instruction panel
     */
    public void showInstructions() {
        JPanel instructionsPanel = createInstructionsPanel();
        this.setContentPane(instructionsPanel);
        this.revalidate();
        this.repaint();
    }

    /**
     * This method handles for the player to go back to the main menu, it will set the panel, repaint it, and set mapPanel and controller to null when
     * succesfully exiting the main menu
     */
    public void returnToMainMenu() {
        createMainMenu(); 
        this.setContentPane(mainMenuPanel);
        this.revalidate();
        this.repaint();
        
        gamePanel = null;
        gameController = null;
        
        System.out.println("Returned to main menu");
    }
}