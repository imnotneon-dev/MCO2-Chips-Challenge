import javax.swing.ImageIcon;

public class Enemy extends Tiles {
    private int x;
    private int y;
    private int dx; // Direction: -1 or 1 for horizontal, 0 for vertical
    private int dy; // Direction: -1 or 1 for vertical, 0 for horizontal
    private char tileBelow; // What tile the enemy is standing on

    public Enemy() {
        super('e');
        sprite = new ImageIcon("Enemy.png");
        this.tileBelow = ' '; // Default to blank
    }

    public Enemy(int startX, int startY, int directionX, int directionY) {
        super('e');
        sprite = new ImageIcon("Enemy.png");
        this.x = startX;
        this.y = startY;
        this.dx = directionX;
        this.dy = directionY;
        this.tileBelow = ' ';
    }

    @Override
    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        // Enemy kills chip on contact
        chip.die();
        return false;
    }

    public void move(Maps map, Chip chip) {
        int newX = x + dx;
        int newY = y + dy;

        if (!map.inBounds(newX, newY) || map.getTileObject(newX, newY).getSymbol() == 'X') {
            dx = -dx;
            dy = -dy;
            return;
        }

        if (newX == chip.getX() && newY == chip.getY()) {
            chip.die();
            return;
        }

        map.setTile(x, y, tileBelow);
        tileBelow = map.getTile(newX, newY);
        
        x = newX;
        y = newY;
        map.setTile(x, y, 'e');
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setTileBelow(char tile) { this.tileBelow = tile; }
}