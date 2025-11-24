import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPanel extends JPanel {
    private Levels level;
    private ImageIcon wallImg, blankTileImg, chipImg, redKeyImg, blueKeyImg, redDoorImg, blueDoorImg, exitImg, playerImg;
    private int tileSize;

    public MapPanel(Levels level) {
        this.level = level;
        calculateTileSize();
        loadImages();
        setPreferredSize(new Dimension(700, 700));
    }

    private void calculateTileSize() {
        char[][] map = level.getMap();
        int rows = map.length;
        int cols = map[0].length;
        
        // Calculate tile size to fit within 700x700
        int maxTiles = Math.max(rows, cols);
        tileSize = 700 / maxTiles;
    }

    private void loadImages() {
        wallImg = new ImageIcon("WallTile.png");
        blankTileImg = new ImageIcon("BlankTile.png");
        chipImg = new ImageIcon("Microchip.png");
        redKeyImg = new ImageIcon("RedKey.png");
        blueKeyImg = new ImageIcon("BlueKey.png");
        redDoorImg = new ImageIcon("RedDoor.png");
        blueDoorImg = new ImageIcon("BlueDoor.png");
        exitImg = new ImageIcon("ExitTile.png");
        playerImg = new ImageIcon("Chip.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        char[][] map = level.getMap();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                char tile = map[row][col];

                ImageIcon img = switch (tile) {
                    case 'X' -> wallImg;
                    case ' ' -> blankTileImg;
                    case '#' -> chipImg;
                    case 'r' -> redKeyImg;
                    case 'R' -> redDoorImg;
                    case 'b' -> blueKeyImg;
                    case 'B' -> blueDoorImg;
                    case 'E' -> exitImg;
                    case '@' -> playerImg; // start pos
                    default -> blankTileImg;
                };

                if (img != null && img.getImage() != null) {
                    g.drawImage(img.getImage(), col * tileSize, row * tileSize, tileSize, tileSize, this);
                }
            }
        }
    }
}
