import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
        tileSize = 32; 

        int width = cols * tileSize;
        int height = rows * tileSize;

        setPreferredSize(new Dimension(width, height));
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

        int mapWidth = map[0].length * tileSize;
        int mapHeight = map.length * tileSize;

        int offsetX = (getWidth() - mapWidth) / 2;
        int offsetY = (getHeight() - mapHeight) / 2;

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (row == player.getY() && col == player.getX())
                    continue;

                Tiles tile = controller.getTileForChar(map[row][col]);
                if (tile != null && tile.getSprite() != null) {
                    g.drawImage(tile.getSprite().getImage(),
                        offsetX + col * tileSize,
                        offsetY + row * tileSize,
                        tileSize, tileSize, this);
                }
            }
        }

        for (Enemy enemy : controller.getEnemies()) {
            Tiles enemyTile = controller.getTileForChar('e');
            if (enemyTile != null && enemyTile.getSprite() != null) {
                g.drawImage(enemyTile.getSprite().getImage(),
                    offsetX + enemy.getX() * tileSize,
                    offsetY + enemy.getY() * tileSize,
                    tileSize, tileSize, this);
            }
        }

        Tiles playerTile = controller.getTileForChar('@');
        if (player.getSprite() != null) {
            g.drawImage(player.getSprite().getImage(),
                offsetX + player.getX() * tileSize,
                offsetY + player.getY() * tileSize,
                tileSize, tileSize, this);
        }

        drawHUD(g);
    }

    private void drawHUD(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        g2.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));

        drawOutlinedText(g2, "Level: " + (controller.getCurrentLevelIndex() + 1), 10, 30, Color.WHITE);
        drawOutlinedText(g2, "Chips: " + controller.getChipsCollected() + "/" + controller.getTotalChips(), 10, 60, Color.WHITE);
        drawOutlinedText(g2, "ESC - Main Menu", 500, 30, Color.WHITE);
        
        if (controller.hasRedKey()) {
            drawOutlinedText(g2, "Red Key: ✓", 10, 90, Color.WHITE);
        }
        if (controller.hasBlueKey()) {
            drawOutlinedText(g2, "Blue Key: ✓", 10, 120, Color.WHITE);
        }
        if (controller.hasFlippers()) {
            drawOutlinedText(g2, "Flippers: ✓", 10, 150, Color.WHITE);
        }
        if (controller.hasFireBoots()) {
            drawOutlinedText(g2, "Fire Boots: ✓", 10, 180, Color.WHITE);
        }
        if (controller.hasIceSkates()) {
            drawOutlinedText(g2, "Ice Skates: ✓", 10, 210, Color.WHITE);
        }
        if (controller.hasTeleportationDevice()) {
            drawOutlinedText(g2, "Teleportation Device: ✓", 10, 240, Color.WHITE);
        }

        if (controller.isLevelComplete()) {
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            drawOutlinedText(g2, "LEVEL COMPLETE!", 150, 350, Color.GREEN);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            drawOutlinedText(g2, "Press ESC to continue", 250, 400, Color.WHITE);
        }

        if (!controller.isPlayerAlive()) {
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            drawOutlinedText(g2, "YOU DIED!", 250, 350, Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            drawOutlinedText(g2, "Press ESC to restart", 250, 400, Color.WHITE);
        }
    }

    private void drawOutlinedText(Graphics2D g2, String text, int x, int y, Color fillColor) {
        Font font = g2.getFont();
        g2.setFont(font);
        
        int thickness = Math.max(1, font.getSize() / 10); 

        g2.setColor(Color.BLACK);
        for (int dx = -thickness; dx <= thickness; dx++) {
            for (int dy = -thickness; dy <= thickness; dy++) {
                if (dx == 0 && dy == 0) continue; // skip center
                g2.drawString(text, x + dx, y + dy);
            }
        }

        g2.setColor(fillColor);
        g2.drawString(text, x, y);
    }


}
