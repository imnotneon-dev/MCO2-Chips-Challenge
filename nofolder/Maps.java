public class Maps {
    private Tiles[][] tiles; 
    private int requiredChips; 
    private int startX;
    private int startY;

    public Maps(char[][] charTiles, int requiredChips) {
        this.tiles = createTileMap(charTiles); 
        this.requiredChips = requiredChips; 
        findStartPosition();
    }

    private Tiles[][] createTileMap(char[][] source) {
        Tiles[][] newTiles = new Tiles[source.length][source[0].length];
        TileRegistry registry = TileRegistry.getInstance();
        
        System.out.println("Creating tile map from source...");
        
        for (int y = 0; y < source.length; y++) {
            for (int x = 0; x < source[y].length; x++) {
                char originalChar = source[y][x];
                System.out.println("Source char at (" + x + "," + y + "): '" + originalChar + "'");
                
                // Make sure '@' characters are preserved
                newTiles[y][x] = registry.createTile(originalChar);
                
                char createdChar = newTiles[y][x].getSymbol();
                System.out.println("Created tile at (" + x + "," + y + "): '" + createdChar + "'");
            }
        }
        return newTiles;
    }
    

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
            // If no start position found, default to (0,0) or center
            this.startX = 0;
            this.startY = 0;
            System.out.println("WARNING: No start position found, using (0,0)"); // DEBUG
            return new int[] {0, 0};
        }

    public Maps cloneMap() { 
        char[][] newTiles = getMap(); 
        return new Maps(newTiles, this.requiredChips);
    }


    public char[][] getMap() {
        char[][] charMap = new char[tiles.length][tiles[0].length];
        Chip player = new Chip(-1, -1); // get player reference from controller
        for (int y = 0; y < tiles.length; y++) {
        for (int x = 0; x < tiles[y].length; x++) {
            // If this is the player position, return what's actually under the player
            if (x == player.getX() && y == player.getY()) {
                charMap[y][x] = player.getCurrentTileBelow();
            } else {
                charMap[y][x] = tiles[y][x].getSymbol(); 
            }
        }
    }
    return charMap;
}

    public char getTile(int x, int y) {
        return tiles[y][x].getSymbol(); 
    }

    public void setTile(int x, int y, char tileType) {
        TileRegistry registry = TileRegistry.getInstance();
        tiles[y][x] = registry.createTile(tileType);
    }
    
    // New method to get the actual Tiles object for game logic
    public Tiles getTileObject(int x, int y) {
        return tiles[y][x];
    }
    
    // New method to check if a move is valid using tile logic
    public boolean isMoveValid(int x, int y, Chip chip, Inventory inv) {
        if (!inBounds(x, y)) return false;
        
        Tiles targetTile = tiles[y][x];
        return targetTile.isWalkable(chip, this, inv, requiredChips);
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < tiles[0].length && y >= 0 && y < tiles.length;
    }

    public int getRequiredChips() { 
        return requiredChips;
    }
}