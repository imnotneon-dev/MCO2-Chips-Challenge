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

public class MyFrame extends JFrame {
    private JPanel mainMenuPanel;
    private MapPanel gamePanel;
    private Controller gameController;
    private BufferedImage backgroundImage;
    private JButton muteButton;

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
       
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("background.png"));
        } catch (Exception e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    private void createMainMenu() {
        // Your existing main menu creation code
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

        // Buttons
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

        // Style buttons (your existing font and cursor code)
        Font buttonFont = new Font("Courier New", Font.BOLD, 18);
        // Play button - start game
        playButton.addActionListener(e -> startGame(0));

        selectLevelButton.addActionListener(e -> showLevelSelector());

        // Instructions button
        instructionsButton.addActionListener(e -> showInstructions());

        // Exit button
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

    public void startGame(int startingLevel) {
        gameController = new Controller();
        gameController.setMainMenu(this);
        gameController.setStartingLevel(startingLevel); // Set the starting level
        
        gamePanel = new MapPanel(gameController);
        this.setContentPane(gamePanel);
        this.pack();  
        this.revalidate();
        gamePanel.requestFocusInWindow();
    }

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

        // Title
        JLabel titleLabel = new JLabel("SELECT LEVEL", JLabel.CENTER);
        titleLabel.setBounds(35, 300, 600, 40);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 32));
        titleLabel.setForeground(java.awt.Color.WHITE);
        levelSelectPanel.add(titleLabel);

        // Add mute button
        JButton muteButton = SoundManager.createMuteButton();
        levelSelectPanel.add(muteButton);

        // Level buttons
        JButton level1Button = new JButton("Level 1 - Beginner");
        JButton level2Button = new JButton("Level 2 - Intermediate");
        JButton level3Button = new JButton("Level 3 - Advanced");
        JButton backButton = new JButton("Back to Main Menu");

        // Position level buttons
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

        // Level button actions
        level1Button.addActionListener(e -> startGame(0)); // Level 1
        level2Button.addActionListener(e -> startGame(1)); // Level 2
        level3Button.addActionListener(e -> startGame(2)); // Level 3
        backButton.addActionListener(e -> returnToMainMenu());

        levelSelectPanel.add(level1Button);
        levelSelectPanel.add(level2Button);
        levelSelectPanel.add(level3Button);
        levelSelectPanel.add(backButton);

        this.setContentPane(levelSelectPanel);
        this.revalidate();
        this.repaint();
    }

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

    // Instructions Title
    JLabel titleLabel = new JLabel("CHIP'S CHALLENGE - INSTRUCTIONS", JLabel.CENTER);
    titleLabel.setBounds(50, 40, 600, 40);
    titleLabel.setFont(new Font("Courier New", Font.BOLD, 28));
    titleLabel.setForeground(java.awt.Color.WHITE);
    instructionsPanel.add(titleLabel);
    JButton muteButton = SoundManager.createMuteButton();
    instructionsPanel.add(muteButton);

    // Scrollable Instructions Text Area
    JTextArea instructionsText = new JTextArea();
    instructionsText.setText(
        "GAME OBJECTIVE:\n" +
        "• Collect all computer chips on each level\n" +
        "• Reach the exit portal to advance to the next level\n" +
        "• Avoid hazards and solve puzzles to progress\n\n" +
        
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
        "• @ - You (the player character)\n" +
        "• E - Exit Portal (finish level here)\n" +
        "• X - Wall (blocks movement)\n" +
        "• W - Water (deadly without flippers)\n" +
        "• F - Fire (deadly without fire boots)\n" +
        "• S - Fire Boots (protect from fire tiles)\n" +
        "• I - Ice (slippery surface)\n\n" +
        
        "GAME RULES:\n" +
        "• You must collect ALL required chips before exiting\n" +
        "• Doors remain locked without the correct key\n" +
        "• Hazard tiles will kill you without proper equipment\n" +
        "• If you die, the level restarts\n" +
        "• Complete all levels to win the game\n\n" +
        
        "STRATEGY TIPS:\n" +
        "• Plan your route before moving\n" +
        "• Collect keys before approaching doors\n" +
        "• Some items are required to pass hazards\n" +
        "• Watch out for tricky level layouts\n" +
        "• Take your time and think carefully!"
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

    // Back button
    JButton backButton = new JButton("Back to Main Menu");
    backButton.setBounds(245, 560, 200, 50);
    backButton.setFont(new Font("Courier New", Font.BOLD, 18));
    backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    backButton.addActionListener(e -> returnToMainMenu());
    
    instructionsPanel.add(backButton);
    return instructionsPanel;
    }

    public void showInstructions() {
        // Your instructions panel code
        JPanel instructionsPanel = createInstructionsPanel();
        this.setContentPane(instructionsPanel);
        this.revalidate();
        this.repaint();
    }

    // ADD THIS METHOD - Called from Controller to return to main menu
    public void returnToMainMenu() {
        createMainMenu(); // Recreate the main menu panel
        this.setContentPane(mainMenuPanel);
        this.revalidate();
        this.repaint();
        
        // Clean up game resources if needed
        gamePanel = null;
        gameController = null;
        
        System.out.println("Returned to main menu");
    }
}