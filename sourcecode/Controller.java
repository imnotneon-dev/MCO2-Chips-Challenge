import java.util.ArrayList;

/**
 * The Controller class handles all the movements and map interactions within the game,
 * it is the one responsible for starting and setting up a level, resetting the game's state,
 * initializing enemies, and rendering the map
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Controller {
    private Maps[] levels;
    private Maps currentMap;
    private Chip chip;
    private NextLevel nextLevel;
    private MyFrame mainMenu;
    private ArrayList<Enemy> enemies;
    
    // Game state with all items and states false / 0
    private int chipsCollected = 0;
    private int totalChips = 0;
    private boolean hasRedKey = false;
    private boolean hasBlueKey = false;
    private boolean levelComplete = false;
    private boolean playerAlive = true;

    /**
     * The Controller constructor handles all the map and level setting up, it uses the nextlevel class to get the map,
     * initializes chip and his start position, the total chips of the map, and initializing of enemies
     */
    public Controller() {
        levels = new Maps[] {
            new Maps(Levels.generateMap1().getMap(), Levels.generateMap1().getChips()),
            new Maps(Levels.generateMap2().getMap(), Levels.generateMap2().getChips()),
            new Maps(Levels.generateMap3().getMap(), Levels.generateMap3().getChips())
        };

        int[] requiredChips = {
            Levels.generateMap1().getChips(),
            Levels.generateMap2().getChips(),
            Levels.generateMap3().getChips()
        };

        nextLevel = new NextLevel(levels, requiredChips);
        currentMap = nextLevel.getCurrentMap();
        chip = new Chip(currentMap.getStartX(), currentMap.getStartY());
        countTotalChips();
        enemies = new ArrayList<>();
        initializeEnemies();
    }

    /**
     * This method sets up the main menu to use when the user wants to quit the level
     * 
     * @param mainMenu the main menu panel from the MyFrame class
     */
    public void setMainMenu(MyFrame mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * This method helps to set up the tile interactions with chip and handles simulation of movement
     * 
     * @param move the move character that helps to determine the tile interaction
     */
    public void handleMove(char move) {
        if (levelComplete || !playerAlive) return;

        String moved = chip.move(move, currentMap); 
        
        if (moved.equals("blocked")) {
            return;
        }

        // Handle tile interactions
        char currentTile = currentMap.getTile(chip.getX(), chip.getY());
        handleTileInteraction(currentTile);

        // Move all enemies
        for (Enemy enemy : enemies) {
            enemy.move(currentMap, chip);
            
            // Check if enemy caught player after moving
            if (enemy.getX() == chip.getX() && enemy.getY() == chip.getY()) {
                chip.die();
                playerAlive = false;
                return;
            }
        }

        // Check for level completion
        if (moved.equals("exit") && nextLevel.canAdvance(chip.getInventory().getChips())) {
            levelComplete = true;
        }

        // Check for death
        if (!chip.isAlive() && moved.equals("died")) {
            playerAlive = false;
        }
    }

    /**
     * This method helps to set up the tile for chip, it receives the '@' char that has chip in it
     * 
     * @param tileChar the tile character that receives the type of tile
     * @return {@code PlayerTile} if the tile char is '@' / chip
     * @return {@code TileRegistry.getTile()} if the tile char is other than '@'
     */
   public Tiles getTileForChar(char tileChar) {
        if (tileChar == '@') {
            // Return the actual player's tile sprite
            return new PlayerTile();
        }
        return TileRegistry.getInstance().getTile(tileChar);
    }

    /**
     * This method helps to settle interactions of chip and the tiles, if the tile is a
     * collectible, he then adds the tile to his inventory and sets that tile a blank tile
     * 
     * @param tile the tile character that receives the type of tile
     */
    private void handleTileInteraction(char tile) {
        switch (tile) {
            case '#' -> {
                currentMap.setTile(chip.getX(), chip.getY(), ' ');
            }
            case 'r' -> {
                hasRedKey = true;
                currentMap.setTile(chip.getX(), chip.getY(), ' ');
                chip.getInventory().addRedKey();
            }
            case 'b' -> {
                hasBlueKey = true;
                currentMap.setTile(chip.getX(), chip.getY(), ' ');
                chip.getInventory().addBlueKey();
            }
            case 'R' -> {
                if (hasRedKey) {
                    hasRedKey = false;
                    currentMap.setTile(chip.getX(), chip.getY(), ' ');
                    chip.getInventory().useRedKey();
                }
            }
            case 'B' -> {
                if (hasBlueKey) {
                    hasBlueKey = false;
                    currentMap.setTile(chip.getX(), chip.getY(), ' ');
                    chip.getInventory().useBlueKey();
                }
            }
            case '_' -> {
                chip.getInventory().addFlippers();
                currentMap.setTile(chip.getX(), chip.getY(), ' ');
            }
            case 'L' -> {
                chip.getInventory().addFireBoots();
                currentMap.setTile(chip.getX(), chip.getY(), ' ');
            }
            case 'Q' -> {
                chip.getInventory().addIceSkates();
                currentMap.setTile(chip.getX(), chip.getY(), ' ');
            }
            case 't' -> {
                chip.getInventory().addTeleportationDevice();
                currentMap.setTile(chip.getX(), chip.getY(), ' ');
            }
        }
    }
    
    /**
     * This method helps with the starting of the levels, whenever the player decides to select one 
     * from the level selection, it creates a new level based on the index 
     * 
     * @param levelIndex the level number that the player decides to play
     */
    public void setStartingLevel(int levelIndex) {
    if (levelIndex >= 0 && levelIndex < levels.length) {
        nextLevel.setCurrentLevel(levelIndex);
        currentMap = nextLevel.getCurrentMap();
        chip = new Chip(currentMap.getStartX(), currentMap.getStartY());
        resetGameState();
        countTotalChips();
        initializeEnemies();
        }
    }

    /**
     * This method helps with the transition of the levels, when the levels are complete it will return to the main menu
     * else it will continue on to the next level after
     */
    public void advanceLevel() {
        if(getMaxLevel()){
            returnToMainMenu();
        } else if (levelComplete && nextLevel.advance()) {
            currentMap = nextLevel.getCurrentMap();
            chip.setX(currentMap.getStartX());
            chip.setY(currentMap.getStartY());
            chip.setCurrentTileBelow(' ');
            resetGameState();
            countTotalChips();
            initializeEnemies();
        }
    }

    /**
     * This method helps with the creation of the levels, when this is called the level resets, setting chip to the starting
     * reviving him, and resetting the game and enemies
     */
    public void resetLevel() {
        currentMap = nextLevel.getCurrentMap();
        chip.setX(currentMap.getStartX());
        chip.setY(currentMap.getStartY());
        chip.setCurrentTileBelow(' ');
        chip.revive();
        resetGameState();
        countTotalChips();
        initializeEnemies();
    }

    /**
     * This method helps with the resetting of the levels, when this is called the level resets, the inventory of chip resets
     * therefore reset to the start of the game
     */
    private void resetGameState() {
        chipsCollected = 0;
        hasRedKey = false;
        hasBlueKey = false;
        levelComplete = false;
        playerAlive = true;
        chip.getInventory().resetInventory();
    }

    /**
     * This method helps with the creation of the levels, when this is called this method counts all the microchip characters inside the map,
     * '#' and increments the totalChips of the map
     */
    private void countTotalChips() {
        totalChips = 0;
        char[][] map = currentMap.getMap();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == '#') {
                    totalChips++;
                }
            }
        }
    }

    /**
     * This method helps with rendering the map as the start of the level, it gets the tiles below and sets it to the render map array
     * 
     * @return {@code char[][]} the map render of the current level
     */
    public char[][] getMapForRendering() {
        char[][] map = currentMap.getMap();
        char[][] renderMap = new char[map.length][map[0].length];
    
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (row == chip.getY() && col == chip.getX()) {
                    renderMap[row][col] = chip.getCurrentTileBelow();
                } else {
                    renderMap[row][col] = map[row][col];
                }
            }
        }
        return renderMap;
    }

    /**
     * This method returns the player to the main menu
     */
    public void returnToMainMenu() {
        if (mainMenu != null) {
            mainMenu.returnToMainMenu();
        }
    }

    /**
     * This method helps with the interactions of chip with the applying of force
     * It helps with the teleporter, ice, and force tiles
     */
    public static void applyForce(Chip chip, Maps map) {
        while (true) {
            char tileChar = chip.getCurrentTileBelow();
            Tiles tile = map.getTileObject(chip.getX(), chip.getY());
            
            if (tile.getSymbol() == '@') {
                tile = TileRegistry.getInstance().getTile(tileChar);
            }
            
            if (!tile.applyForce(chip, map))
                break;
        }
    }

    /**
     * This method helps within creation of the maps as it initializes the enemies, it will determine the tiles of the enemy
     * and makes sure that when they encounter the wall, they will return to their starting position
     */
    private void initializeEnemies() {
        enemies.clear();
        char[][] map = currentMap.getMap();
        
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'e') {
                    int dx = 1, dy = 0;
                    
                    if (x + 1 >= map[y].length || map[y][x + 1] == 'X') {
                        dx = -1;
                    }
                    
                    Enemy enemy = new Enemy(x, y, dx, dy);
                    enemy.setTileBelow(' '); 
                    enemies.add(enemy);
                    
                    currentMap.setTile(x, y, ' ');
                }
            }
        }
    }


    // All the getters necessary for the controller class
    public Maps getCurrentMap() { return currentMap; }
    public Chip getPlayer() { return chip; }
    public int getChipsCollected() { return chip.getInventory().getChips(); }
    public int getTotalChips() { return totalChips; }
    public boolean hasRedKey() { return chip.getInventory().hasRedKey(); }
    public boolean hasBlueKey() { return chip.getInventory().hasBlueKey(); }
    public boolean hasFlippers() { return chip.getInventory().hasFlippers(); }
    public boolean hasFireBoots() { return chip.getInventory().hasFireBoots(); }
    public boolean hasIceSkates() { return chip.getInventory().hasIceSkates(); }
    public boolean isLevelComplete() { return levelComplete; }
    public boolean getMaxLevel() { return nextLevel.getMaxLevel(); }
    public boolean isPlayerAlive() { return playerAlive; }
    public int getCurrentLevelIndex() { return nextLevel.getCurrentLevel(); }
    public ArrayList<Enemy> getEnemies() { return enemies;}
    public boolean hasTeleportationDevice() { return chip.getInventory().hasTeleportationDevice(); }

}