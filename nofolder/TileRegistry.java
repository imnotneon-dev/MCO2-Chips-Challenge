public class TileRegistry {
    private static TileRegistry instance;
    private Tiles[] tileTypes;
    
    // Tile type constants
    public static final char WALL = 'X';
    public static final char BLANK = ' ';
    public static final char CHIP_ITEM = '#';
    public static final char RED_KEY = 'r';
    public static final char RED_DOOR = 'R';
    public static final char BLUE_KEY = 'b';
    public static final char BLUE_DOOR = 'B';
    public static final char EXIT = 'E';
    public static final char PLAYER_START = '@';
    public static final char WATER = 'W';
    public static final char FIRE = 'F';
    public static final char ICE = 'I';
    public static final char FIRE_BOOTS = 'L';
    public static final char FLIPPERS = '_';
    public static final char ICE_SKATES = 'Q';
    public static final char FORCE_UP = '^';
    public static final char FORCE_DOWN = 'v';
    public static final char FORCE_LEFT = '<';
    public static final char FORCE_RIGHT = '>';
    
    private TileRegistry() {
        initializeTiles();
    }
    
    public static TileRegistry getInstance() {
        if (instance == null) {
            instance = new TileRegistry();
        }
        return instance;
    }
    
    private void initializeTiles() {
        tileTypes = new Tiles[] {
            new WallTile(),
            new BlankTile(),
            new Microchip(),
            new RedKey(),
            new RedDoor(),
            new BlueKey(),
            new BlueDoor(),
            new ExitTile(),
            new WaterTile(),
            new FireTile(),
            new IceTile(),
            new FireBoots(),
            new PlayerTile(),
            new Flippers(),
            new IceSkates(),
            new ForceUpTile(),
            new ForceDownTile(),
            new ForceLeftTile(),
            new ForceRightTile()
        };
        
    }
    
    public Tiles getTile(char type) {
        for (Tiles tile : tileTypes) {
            if (tile.getSymbol() == type) {
                return tile;
            }
        }
        return new BlankTile(); // Default fallback
    }
    
    public Tiles createTile(char type) {
        Tiles template = getTile(type);
        // Return a new instance with the same type
        // This ensures each tile is a separate instance
        return new Tiles(type) {
            @Override
            public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
                return template.isWalkable(chip, map, inv, requiredChips);
            }
        };
    }
    
    public char[] getAllTileTypes() {
        char[] types = new char[tileTypes.length];
        for (int i = 0; i < tileTypes.length; i++) {
            types[i] = tileTypes[i].getSymbol();
        }
        return types;
    }
}