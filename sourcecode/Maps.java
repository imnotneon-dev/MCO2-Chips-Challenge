/**
 * This class contains the construction of map layous in the game, this will create the tiles, find the start position of chip, contains getting of tiles
 * and setting of tiles, controls the bounds of the map and walkability of player.
 *  @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Maps {
    private Tiles[][] tiles; 
    private int requiredChips; 
    private int startX;
    private int startY;

    /**
     * This constructor for the map tile sets the tile map and required chips for each map, it also finds the starting position
     * 
     * @param charTiles the 2d array of the tiles that are going to be used for the map
     * @param requiredChips the number of chips required to pass a level for the map
     */
    public Maps(char[][] charTiles, int requiredChips) {
        this.tiles = createTileMap(charTiles); 
        this.requiredChips = requiredChips; 
        findStartPosition();
    }

    /**
     * This method helps construct the tile map layout for the level, it first instantiates all available tiles, gets the source of the
     * x and y coordinates and creates the tile on the specific coordinates
     * 
     * @param source the 2d array of characters that contain the tiles
     */
    private Tiles[][] createTileMap(char[][] source) {
        Tiles[][] newTiles = new Tiles[source.length][source[0].length];
        TileRegistry registry = TileRegistry.getInstance();
        
        System.out.println("Creating tile map from source...");
        
        for (int y = 0; y < source.length; y++) {
            for (int x = 0; x < source[y].length; x++) {
                char originalChar = source[y][x];
                System.out.println("Source char at (" + x + "," + y + "): '" + originalChar + "'");
                
                newTiles[y][x] = registry.createTile(originalChar);
                
                char createdChar = newTiles[y][x].getSymbol();
                System.out.println("Created tile at (" + x + "," + y + "): '" + createdChar + "'");
            }
        }
        return newTiles;
    }
    
    /**
     * This method helps find the starting position of the player chip '@', it returns the starting position based on the position of
     * the '@' character, it returns the default (0,0) if no symbol is found
     * @return [x,y] the starting position of the map
     * @return [0,0] the default starting position
     */
    public int[] findStartPosition() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x].getSymbol() == '@') { 
                    this.startX = x;
                    this.startY = y;
                    System.out.println("Found start position at: " + x + ", " + y); // DEBUG
                    return new int[] {x, y};
                }
            }
        }
            this.startX = 0;
            this.startY = 0;
            System.out.println("WARNING: No start position found, using (0,0)"); // DEBUG
            return new int[] {0, 0};
        }

    /**
     * This method helps cloning of the original map to be used for maintaining map state purposes
     * @return Maps() returns a cloned map with the tiles added and required chips
     */
    public Maps cloneMap() { 
        char[][] newTiles = getMap(); 
        return new Maps(newTiles, this.requiredChips);
    }


    /**
     * This method helps to get a map from the maps array
     * @return charMap the map to get
     */
    public char[][] getMap() {
        char[][] charMap = new char[tiles.length][tiles[0].length];
        Chip player = new Chip(-1, -1); 
        for (int y = 0; y < tiles.length; y++) {
        for (int x = 0; x < tiles[y].length; x++) {
            if (x == player.getX() && y == player.getY()) {
                charMap[y][x] = player.getCurrentTileBelow();
            } else {
                charMap[y][x] = tiles[y][x].getSymbol(); 
            }
        }
    }
    return charMap;
}

    /**
     * This method helps to get a tile from the map layout
     * @return tiles the tile to get
     */
    public char getTile(int x, int y) {
        return tiles[y][x].getSymbol(); 
    }

    /**
     * This method helps to set a tile to a different tile from the map layout
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @param tileType character symbol of the type of tile
     */
    public void setTile(int x, int y, char tileType) {
        TileRegistry registry = TileRegistry.getInstance();
        tiles[y][x] = registry.createTile(tileType);
    }
    
    /**
     * A new method to get the actual Tiles object for game logic
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @return tiles[y][x] the specific tile to get
     */
    public Tiles getTileObject(int x, int y) {
        return tiles[y][x];
    }
    
    /**
     * A new method to check if a move is valid using tile logic
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @param chip the player chip 
     * @param inv the current inventory of chip
     * @return {@code true} if said tile is walkable and the move is valid
     * @return {@code false} if out of bounds from the map
     * @return {@code false} if said tile is not walkable and the move is invalid
     */
    public boolean isMoveValid(int x, int y, Chip chip, Inventory inv) {
        if (!inBounds(x, y)) return false;
        
        Tiles targetTile = tiles[y][x];
        return targetTile.isWalkable(chip, this, inv, requiredChips);
    }

    /**
     * This method gets the starting position of the x coordinate
     * @return startX the starting x coordinate
     */
    public int getStartX() {
        return startX;
    }

    /**
     * This method gets the starting position of the y coordinate
     * @return startY the starting y coordinate
     */
    public int getStartY() {
        return startY;
    }

    /**
     * This method determines if the tiles are still in bound
     * @return {@code true} if the tiles are still in bound of the map
     * @return {@code false} if the tiles are out of bounds from the map
     */
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < tiles[0].length && y >= 0 && y < tiles.length;
    }

    /**
     * This method gets the required chips of the map
     * @return requiredChips the number of chips required for the player to pass
     */
    public int getRequiredChips() { 
        return requiredChips;
    }
}