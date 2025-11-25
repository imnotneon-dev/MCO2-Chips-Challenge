public class Controller {
    private Maps[] levels;
    private Maps currentMap;
    private Chip chip;
    private NextLevel nextLevel;
    private MyFrame mainMenu;
    
    // Game state
    private int chipsCollected = 0;
    private int totalChips = 0;
    private boolean hasRedKey = false;
    private boolean hasBlueKey = false;
    private boolean levelComplete = false;
    private boolean playerAlive = true;

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
    }

    public void setMainMenu(MyFrame mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void handleMove(char move) {
        if (levelComplete || !playerAlive) return;

        String moved = chip.move(move, currentMap); 
        
        if (moved.equals("blocked")) {
            return;
        }

        // Handle tile interactions
        char currentTile = currentMap.getTile(chip.getX(), chip.getY());
        handleTileInteraction(currentTile);

        // Check for level completion
        if (moved.equals("exit") && nextLevel.canAdvance(chip.getInventory().getChips())) {
            levelComplete = true;
        }

        // Check for death
        if (!chip.isAlive() && moved.equals("died")) {
            playerAlive = false;
        }
    }

   public Tiles getTileForChar(char tileChar) {
        if (tileChar == '@') {
            // Return the actual player's tile sprite
            return new PlayerTile();
        }
        return TileRegistry.getInstance().getTile(tileChar);
    }

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
        }
    }

    public void advanceLevel() {
        if (levelComplete && nextLevel.advance()) {
            currentMap = nextLevel.getCurrentMap();
            chip.setX(currentMap.getStartX());
            chip.setY(currentMap.getStartY());
            resetGameState();
            countTotalChips();
        }
    }

    public void resetLevel() {
        currentMap = nextLevel.getCurrentMap();
        chip.setX(currentMap.getStartX());
        chip.setY(currentMap.getStartY());
        chip.revive();
        resetGameState();
        countTotalChips();
    }

    private void resetGameState() {
        chipsCollected = 0;
        hasRedKey = false;
        hasBlueKey = false;
        levelComplete = false;
        playerAlive = true;
        chip.getInventory().resetInventory();
    }

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

    public char[][] getMapForRendering() {
        char[][] map = currentMap.getMap();
        // Create a copy and remove the player marker for rendering
        char[][] renderMap = new char[map.length][map[0].length];
    
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (row == chip.getY() && col == chip.getX()) {
                    // Show what's under the player, not the player itself
                    renderMap[row][col] = chip.getCurrentTileBelow();
                } else {
                    renderMap[row][col] = map[row][col];
                }
            }
        }
        return renderMap;
    }

    public void returnToMainMenu() {
        if (mainMenu != null) {
            mainMenu.returnToMainMenu();
        }
    }

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

    


    // Getters
    public Maps getCurrentMap() { return currentMap; }
    public Chip getPlayer() { return chip; }
    public int getChipsCollected() { return chip.getInventory().getChips(); }
    public int getTotalChips() { return totalChips; }
    public boolean hasRedKey() { return chip.getInventory().hasRedKey(); }
    public boolean hasBlueKey() { return chip.getInventory().hasBlueKey(); }
    public boolean isLevelComplete() { return levelComplete; }
    public boolean isPlayerAlive() { return playerAlive; }
    public int getCurrentLevelIndex() { return nextLevel.getCurrentLevel(); }
    
    // New method to get tile sprite for rendering
}