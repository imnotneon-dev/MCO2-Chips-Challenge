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

    public MyFrame() {
        this.setTitle("Chips Challenge");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JButton playButton = new JButton("Play");
        JButton instructionsButton = new JButton("Instructions");
        JButton exitButton = new JButton("Exit");

        playButton.setBounds(245, 300, 200, 50);
        instructionsButton.setBounds(245, 375, 200, 50);
        exitButton.setBounds(245, 450, 200, 50);

        // Style buttons (your existing font and cursor code)

        // Play button - start game
        playButton.addActionListener(e -> startGame());

        // Instructions button
        instructionsButton.addActionListener(e -> showInstructions());

        // Exit button
        exitButton.addActionListener(e -> System.exit(0));

        mainMenuPanel.add(playButton);
        mainMenuPanel.add(instructionsButton);
        mainMenuPanel.add(exitButton);
    }

    public void startGame() {
        gameController = new Controller();
        gameController.setMainMenu(this); // Pass reference to main menu
        
        gamePanel = new MapPanel(gameController);
        this.setContentPane(gamePanel);
        this.pack();  
        this.revalidate();
        gamePanel.requestFocusInWindow();
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
    JLabel titleLabel = new JLabel("CHIPS CHALLENGE - INSTRUCTIONS", JLabel.CENTER);
    titleLabel.setBounds(50, 30, 600, 40);
    titleLabel.setFont(new Font("Courier New", Font.BOLD, 28));
    titleLabel.setForeground(java.awt.Color.WHITE);
    instructionsPanel.add(titleLabel);

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
    scrollPane.setBounds(50, 80, 600, 350);
    scrollPane.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setOpaque(false);
    instructionsPanel.add(scrollPane);

    // Back button
    JButton backButton = new JButton("Back to Main Menu");
    backButton.setBounds(245, 450, 200, 50);
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