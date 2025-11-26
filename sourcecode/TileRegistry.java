/**
 * This class features the helper for initializing all the tile types based on their character symbols
 * This class is mostly used in controller and map parts of the game
 * 
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class TileRegistry {
    private static TileRegistry instance;
    private Tiles[] tileTypes;
    
    //Setting final characters of the symbol of tile types
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
    public static final char ENEMY = 'e';
    public static final char PURPLE_TELEPORTER = 'P';
    public static final char GREEN_TELEPORTER = 'G';
    public static final char TELEPORTATION_DEVICE = 't';
    
    /**
     * Helps initialize the tiles when called
     */
    private TileRegistry() {
        initializeTiles();
    }
    
    /**
     * A method to gain the instance of a tile
     * @return the instance of the new tile type based on character symbol
     */
    public static TileRegistry getInstance() {
        if (instance == null) {
            instance = new TileRegistry();
        }
        return instance;
    }
    
    /**
     * This method helps initialize all the tile types, calling all their classes and storing them in tileTypes
     */
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
            new ForceRightTile(),
            new Enemy(),
            new PurpleTeleporter(),
            new GreenTeleporter(),
            new TeleportationDevice()
        };   
    }
    
    /**
     * This is the getter for the tiles, if the symbol matches the type of tile that was gotten from the for loop it will return that tile
     * else it would return a blank tile
     * @param type the specific character symbol for the type of tile
     * @return {@code tile} if the tile was found in the loop that matches the symbol
     * @return {@code BlankTile} if the tile was not found in the loop
     */
    public Tiles getTile(char type) {
        for (Tiles tile : tileTypes) {
            if (tile.getSymbol() == type) {
                return tile;
            }
        }
        return new BlankTile(); 
    }
    
    /**
     * This method helps to create a specific tile based on type
     * @param type the specific tile type
     * @return a new tile that has the same properties of its type
     */
    public Tiles createTile(char type) {
        Tiles template = getTile(type);
        return new Tiles(type) {
            @Override
            public boolean isWalkable(Chip chip, Maps map, Inventory inv, int requiredChips) {
                return template.isWalkable(chip, map, inv, requiredChips);
            }
        };
    }
    
    /**
     * This getter helps in getting all the tile types for looping functions and specific tile finding functions
     * 
     * @return types all the tile types that were stored
     */
    public char[] getAllTileTypes() {
        char[] types = new char[tileTypes.length];
        for (int i = 0; i < tileTypes.length; i++) {
            types[i] = tileTypes[i].getSymbol();
        }
        return types;
    }
}