/**
 * Maps Class
 * 
 * - Handles the playable map in the game.
 * Each map has a 2d array of tiles and those tiles correspond to different collectibles(keys/equipment/chips) or walls for in bounds control or hazards and doors or it can be blank. This map class also is in charge of the requiredChips in order to pass the map layout and move on to the next level, the map is constantly updated when chip/player interacts with different objects.
 * It also is responsible for cloning a map upon resetting a level and storing the start position of the player.
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Maps {

    /**
     * 2d array consisting of tiles that represents the layout of the map, it is from made with the Tiles class
     */
    private Tiles[][] tiles; 
    /**
     * requiredChips the player needs to acquire to move on to the next map/level
     */
    private int requiredChips; 
    /**
     * Coordinate for the starting position of the x-coordinate
     */
    private int startX;
    /**
     * Coordinate for the starting position of the y-coordinate
     */
    private int startY;

    /**
     * Constructor for the Maps class and it creates the Map upon accepting tiles and required chips
     * Also handles on getting the starting position of the map for chip to spawn in
     * 
     * @param charTiles - 2d array consisting of tiles to be used for the layout
     * @param requiredChips - number of chips required for the player to pass onto the next map/level
     */
     public Maps(char[][] charTiles, int requiredChips) {
        this.tiles = createTileMap(charTiles); 
        this.requiredChips = requiredChips; 
        findStartPosition();
    }

    /**
     * Used by the constructor and is the one responsible for creating new tiles inside the map, different types of tiles will be created upon getting the      source of the 2d array
     * 
     * @param source - accepts 2d array layout for creation
     * @return newTiles - returns the new 2d tiles array for map creation
     */
    private Tiles[][] createTileMap(char[][] source) {
        Tiles[][] newTiles = new Tiles[source.length][source[0].length];
        for (int y = 0; y < source.length; y++) {
            for (int x = 0; x < source[y].length; x++) {
                newTiles[y][x] = new Tiles(source[y][x]); 
            }
        }
        return newTiles;
    }

    /**
     * Finds the starting position for chip to spawn in depending on the map. If the method detects '@', it has found chip and sets the starting position on chip's position.
     * @return int[] - returns an array consisting of the starting x and y coordinates
     */
    public int[] findStartPosition() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x].getType() == '@') { 
                    this.startX = x;
                    this.startY = y;
                }
            }
        }
        return new int[] {startX, startY};
    }

    /**
     * Responsible for cloning a map for purposes of restarting a level upon player death.
     * 
     * @return - returns the original state of the map for the player to use once the level has restarted
     */
    public Maps cloneMap() { 
        char[][] newTiles = getMap(); 
        return new Maps(newTiles, this.requiredChips);
    }

    /**
     * Returns the current 2d array of characters that represent the current map state of the game
     * 
     * @return charMap - 2d character array of the map layout
     */
    public char[][] getMap() {
        char[][] charMap = new char[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                charMap[y][x] = tiles[y][x].getType(); 
            }
        }
        return charMap;
    }

    /**
     * Returns the tile type at the specific coordinate located
     * @param x - x-coordinate of tile
     * @param y - y-coordinate of tile
     * @return tiles type - returns the type of the tile that is located at the specific x and y coordinates
     */
    public char getTile(int x, int y) {
        return tiles[y][x].getType(); 
    }

    /**
     * Sets the indicated x and y coordinate to the tile type accepted.
     * 
     * @param x - x-coordinate of tile
     * @param y - y-coordinate of tile
     * @param tileType - indicates the type of tile based on the character tileType
     */
    public void setTile(int x, int y, char tileType) {
        tiles[y][x].setType(tileType); 
    }
    
    /**
     * Returns the starting position of the x-coordinate of the map
     * 
     * @return startX - returns the x-coordinate
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Returns the starting position of the y-coordinate of the map
     * 
     * @return startY - returns the y-coordinate
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Checks the boundary rules of the map layout on if the coordinates are within the boundaries of the map
     * 
     * @param x - the x-coordinate to check
     * @param y - the y-coordinate to check
     * @return true - if the coordinates are still inside the map
     * @return false - if the coordinates are outside the map
     */
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < tiles[0].length && y >= 0 && y < tiles.length;
    }

    /**
     * Returns the required chips for the player to pass the map
     * 
     * @return requiredChips - returns required chips needed to progress
     */
    public int getRequiredChips() { 
        return requiredChips;
    }
}
