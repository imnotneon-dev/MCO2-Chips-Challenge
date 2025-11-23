import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame {

    private JButton playButton, exitButton;
    private BufferedImage backgroundImage;

    public MyFrame() {
        this.setTitle("Chips Challenge");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Load image
        try {
            backgroundImage = ImageIO.read(new File("background.png")); // change to images/background.png if packages
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Panel with background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(null);

        // Buttons
        playButton = new JButton("Play");
        exitButton = new JButton("Exit");

        playButton.setBounds(245, 350, 200, 50);
        playButton.setFont(new Font("Courier New", Font.BOLD, 24));
        playButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        exitButton.setBounds(245, 450, 200, 50);
        exitButton.setFont(new Font("Courier New", Font.BOLD, 24));
        exitButton.addActionListener(e -> System.exit(0));

        backgroundPanel.add(playButton);
        backgroundPanel.add(exitButton);

        playButton.addActionListener(e -> {
            this.setVisible(false); 
        });


        this.setContentPane(backgroundPanel);
        this.setVisible(true);
    }
}
