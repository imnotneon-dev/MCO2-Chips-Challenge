import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class sets up the map part of the game, it is a panel that uses the controller class and this panel sets up the key action listeners
 * that will simulate the movement of chip
 *  @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class MapPanel extends JPanel {
    private Controller controller;
    private int tileSize;

    public MapPanel(Controller controller) {
        this.controller = controller;
        calculateTileSize();
        setPreferredSize(new Dimension(700, 700));
        setupKeyListener();
        setFocusable(true);

        this.setLayout(null);

        JButton muteButton = SoundManager.createMuteButton();
        muteButton.setBounds(630, 50, 50, 50); 
        this.add(muteButton);
    }

/**
 * This class will help set up the map as it is the one to calculate the tile size of the maps, making the preferred size
 * to have the proper width and height
 */
    private void calculateTileSize() {
        char[][] map = controller.getCurrentMap().getMap();
        int rows = map.length;
        int cols = map[0].length;
        tileSize = 32; 

        int width = cols * tileSize;
        int height = rows * tileSize;

        setPreferredSize(new Dimension(width, height));
    }

/**
 * This class will simulate the movement of the player as it handles the W A S D and arrow keys for directional movement,
 * and the escape key for level completion and main menu exit of the game
 */
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
                    if (controller.getMaxLevel()) {
                        controller.returnToMainMenu();
                    } else {
                        controller.advanceLevel();
                        repaint();
                    }
            } else if (!controller.isPlayerAlive()) {
                controller.resetLevel();
                repaint();
            } else {
                controller.returnToMainMenu();
                }
            }
        });
    }

/**
 * This class will simulate the sprites of the map layout, it paints the map and its tiles, inventory items,
 * player, enemies, and special tiles
 * 
 * @param g Graphics parameter
 */
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

    /**
     * This method helps with displays of the hud part of the game, it displays the current level, microchip status, 
     * item that chip has to inform the player of the contents in his inventory
     * 
     * This is where the fonts and sizes of the hud text in
     * @param g Graphics parameter
     */
    private void drawHUD(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));

        drawOutlinedText(g2, "Level: " + (controller.getCurrentLevelIndex() + 1), 10, 30, Color.WHITE);
        int chipsTextY = 60;
        int iconSize = 26;
        int chipSpriteX = 10;
        int chipSpriteY = chipsTextY - iconSize + 5;
        
        Tiles chipTile = controller.getTileForChar('#');
        if (chipTile != null && chipTile.getSprite() != null) {
            g2.drawImage(chipTile.getSprite().getImage(), chipSpriteX, chipSpriteY, iconSize, iconSize, this);
        }
        int chipsTextX = chipSpriteX + iconSize + 5; 
        drawOutlinedText(g2, "Chips: " + controller.getChipsCollected() + "/" + controller.getTotalChips(), chipsTextX, chipsTextY, Color.WHITE);
        drawOutlinedText(g2, "ESC - Main Menu", 500, 30, Color.WHITE);

        int iconY = 90;
        
        if (controller.hasRedKey()) {
            Tiles redKeyTile = controller.getTileForChar('r');
            if (redKeyTile != null && redKeyTile.getSprite() != null) {
                g2.drawImage(redKeyTile.getSprite().getImage(), 10, iconY - iconSize, iconSize, iconSize, this);
            }
            drawOutlinedText(g2, "Red Key", 40, iconY, Color.WHITE);
            iconY += 30;
        }
        if (controller.hasBlueKey()) {
            Tiles blueKeyTile = controller.getTileForChar('b');
            if (blueKeyTile != null && blueKeyTile.getSprite() != null) {
                g2.drawImage(blueKeyTile.getSprite().getImage(), 10, iconY - iconSize, iconSize, iconSize, this);
            }
            drawOutlinedText(g2, "Blue Key", 40, iconY, Color.WHITE);
            iconY += 30;
        }
        if (controller.hasFlippers()) {
            Tiles flippersTile = controller.getTileForChar('_');
            if (flippersTile != null && flippersTile.getSprite() != null) {
                g2.drawImage(flippersTile.getSprite().getImage(), 10, iconY - iconSize, iconSize, iconSize, this);
            }
            drawOutlinedText(g2, "Flippers", 40, iconY, Color.WHITE);
            iconY += 30;
        }
        if (controller.hasFireBoots()) {
            Tiles fireBootsTile = controller.getTileForChar('L');
            if (fireBootsTile != null && fireBootsTile.getSprite() != null) {
                g2.drawImage(fireBootsTile.getSprite().getImage(), 10, iconY - iconSize, iconSize, iconSize, this);
            }
            drawOutlinedText(g2, "Fire Boots", 40, iconY, Color.WHITE);
            iconY += 30;
        }
        if (controller.hasIceSkates()) {
            Tiles iceSkatesTile = controller.getTileForChar('Q');
            if (iceSkatesTile != null && iceSkatesTile.getSprite() != null) {
                g2.drawImage(iceSkatesTile.getSprite().getImage(), 10, iconY - iconSize, iconSize, iconSize, this);
            }
            drawOutlinedText(g2, "Ice Skates", 40, iconY, Color.WHITE);
            iconY += 30;
        }
        if (controller.hasTeleportationDevice()) {
            Tiles teleportationDeviceTile = controller.getTileForChar('t');
            if (teleportationDeviceTile != null && teleportationDeviceTile.getSprite() != null) {
                g2.drawImage(teleportationDeviceTile.getSprite().getImage(), 10, iconY - iconSize, iconSize, iconSize, this);
            }
            drawOutlinedText(g2, "Teleportation", 40, iconY, Color.WHITE);
            drawOutlinedText(g2, "Device", 45, iconY + 25, Color.WHITE);
            iconY += 30;
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

    /**
     * This method mainly helps with setting up the outlined text of the map, includes the microchip status and the item statuses
     * Helps with the gui for clarity and status of the game
     * @param g2 Graphics2D parameter
     * @param text is a string that contains the text placed
     * @param x x coordinate of text
     * @param y y coordinate of text
     * @param fillColor color of the text
     */
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
