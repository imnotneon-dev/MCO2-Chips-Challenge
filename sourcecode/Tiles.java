/**
 * Tiles Class
 * 
 * - Represents all the tiles used in the map layouts, it is responsible for giving property of the special tiles including hazard tiles (water and fire), force tiles, and exit tiles. It is the one used by the Controller class (isWalkable) and Maps class to simulate the game and its objectives. 
 * This class also handles the action of the force tiles when chip has stepped upon them, pushing him into the direction where the force tile has ended or an obstacle/hazard is the next tile.
 * 
 * This class works with the Chip class, Inventory class, Maps class, Doors class as it handles the movement and interaction and collision of chip to special and blank tiles
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */

import javax.swing.ImageIcon;

public abstract class Tiles {

    private char symbol;
    protected ImageIcon sprite;

    public Tiles(char symbol) {
        this.symbol = symbol;
    }

    public void setTiles(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public ImageIcon getSprite() {
        return sprite;
    }

    public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
        return true;
    }

    public boolean applyForce(Chip chip, Maps map) {
        return false;
    }
}
