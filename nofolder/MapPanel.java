import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class MapPanel extends JPanel {
    private Controller controller;
    private int tileSize;

    public MapPanel(Controller controller) {
        this.controller = controller;
        calculateTileSize();
        setPreferredSize(new Dimension(700, 700));
        setupKeyListener();
        setFocusable(true);
    }

    private void calculateTileSize() {
        char[][] map = controller.getCurrentMap().getMap();
        int rows = map.length;
        int cols = map[0].length;
        int maxTiles = Math.max(rows, cols);
        tileSize = 700 / maxTiles;
    }

    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        controller.handleMove('W');
                        repaint();
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        controller.handleMove('S');
                        repaint();
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        controller.handleMove('A');
                        repaint();
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        controller.handleMove('D');
                        repaint();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        handleEscapeKey();
                        break;
                }
            }

            private void handleEscapeKey() {
            if (controller.isLevelComplete()) {
                controller.advanceLevel();
                repaint();
            } else if (!controller.isPlayerAlive()) {
                controller.resetLevel();
                repaint();
            } else {
                controller.returnToMainMenu();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    char[][] map = controller.getCurrentMap().getMap();
    Chip player = controller.getPlayer();

    // Draw map tiles (but skip the player's current position)
    for (int row = 0; row < map.length; row++) {
        for (int col = 0; col < map[row].length; col++) {
            // Skip drawing the tile where the player is currently standing
            if (row == player.getY() && col == player.getX()) {
                continue;
            }
            
            char tileChar = map[row][col];
            Tiles tile = controller.getTileForChar(tileChar);
            
            if (tile != null && tile.getSprite() != null) {
                g.drawImage(tile.getSprite().getImage(), col * tileSize, row * tileSize, tileSize, tileSize, this);
            }
        }
    }
    
        // Draw player on top
        Tiles playerTile = controller.getTileForChar('@');
        if (playerTile != null && playerTile.getSprite() != null) {
            g.drawImage(playerTile.getSprite().getImage(), 
                    player.getX() * tileSize, 
                    player.getY() * tileSize, 
                    tileSize, tileSize, this);
        }

        // Draw HUD
        drawHUD(g);
    }

    private void drawHUD(Graphics g) {
        g.setColor(java.awt.Color.BLACK);
        g.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.BOLD, 20));
        g.drawString("Level: " + (controller.getCurrentLevelIndex() + 1), 10, 30);
        g.drawString("Chips: " + controller.getChipsCollected() + "/" + controller.getTotalChips(), 10, 60);

        g.drawString("ESC - Main Menu", 500, 30);
        
        if (controller.hasRedKey()) {
            g.drawString("Red Key: ✓", 10, 90);
        }
        if (controller.hasBlueKey()) {
            g.drawString("Blue Key: ✓", 10, 120);
        }

        if (controller.isLevelComplete()) {
            g.setColor(java.awt.Color.GREEN);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            g.drawString("LEVEL COMPLETE!", 150, 350);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
            g.drawString("Press ESC to continue", 250, 400);
        }

        if (!controller.isPlayerAlive()) {
            g.setColor(java.awt.Color.RED);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            g.drawString("YOU DIED!", 250, 350);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
            g.drawString("Press ESC to restart", 250, 400);
        }
    }
}