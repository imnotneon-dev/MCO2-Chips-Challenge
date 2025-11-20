/**
 *  Levels Class
 * 
 * - Handles the layout of the maps inside the game. It currently has 3 levels and it is used by the Display class to show the map layouts and
 * it is the one to show the tile characters, hazard(water/fire) tiles, doors, force tiles, collectibles, and exit tile.
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class Levels {

    /**
     * 2d array to use for the layout, it is the one responsible to display the tile layout
     */
    private char[][] map;
    /**
     * Contains the chips required to pass a level
     */
    private int chips;

    /**
     * Constructor of Levels class that accepts the given map and chips required to pass the level/map
     * 
     * @param map - 2d array for the map layout
     * @param chips - required chips to pass a single level
     */
    public Levels(char[][] map, int chips) {
        this.map = map;
        this.chips = chips;
    }
    
    /**
     * Current layout for the first map, it is a beginner and teaching level as it only features keys and door tiles
     * Currently has a red key, red door, blue key, blue door, and 4 chips to collect to pass the level
     * 
     * @return Levels- it returns the current map layout for level 1 for the Display class to use
     */
    public static Levels generateMap1() {
        char[][] grid = {
            {'X','X','X','X','X','X','X','X','X'},
            {'X',' ','b','X','E','X','#',' ','X'},
            {'X','R','X','X',' ','X','X','B','X'},
            {'X',' ',' ',' ',' ',' ',' ',' ','X'},
            {'X',' ',' ',' ','@','#',' ',' ','X'},
            {'X','#','X','X','X','X','X','#','X'},
            {'X',' ','X',' ','r',' ','X',' ','X'},
            {'X',' ',' ',' ','X',' ',' ',' ','X'},
            {'X','X','X','X','X','X','X','X','X'}
        };

        int chips = countChips(grid);

        return new Levels(grid, chips);
    }

    /**
     * Current layout for the second map, it is a normal level as it features hazard and force tiles
     * Currently has force tiles, flippers, water tiles, fire boots, fire tiles, and 4 chips to pass the level
     * 
     * @return Levels - returns the current map layout for level 2 for the Display class to use
     */
    public static Levels generateMap2() {
        char[][] grid = {
            {'X','X','X','X','X','X','X','X','X'},
            {'X','E',' ','F','v','W','W','L','X'},
            {'X',' ',' ','F','v','W','X','X','X'},
            {'X','F','F','F','v','W','W','W','X'},
            {'X','#','<','<','#','X','X','#','X'},
            {'X','>','v','^','v','W','W','W','X'},
            {'X','^','#','^','v','W','X','X','X'},
            {'X','^','<','<','<','W','_','@','X'},
            {'X','X','X','X','X','X','X','X','X'}
        };

        int chips = countChips(grid);

        return new Levels(grid, chips);
    }

    /**
     * Current layout for the third map, it is a combination of difficulty for past levels as it features both hazard and force tiles and key and door tiles
     * This map currently has force tiles, flippers, water tiles, fire boots, fire tiles, red key, red door, blue key, blue door and 5 chips to pass the level
     * 
     * @return Levels- returns the current map layout for level 3 for the Display class to use
     */
    public static Levels generateMap3() {
        char[][] grid = {
            {'X','X','X','X','X','X','X','X','X','X','X'},
            {'X',' ',' ',' ',' ',' ',' ',' ','#','@','X'},
            {'X','v','X','X','X','^','X','X','X','B','X'},           
            {'X','v','X',' ','F',' ',' ','X','#',' ','X'},
            {'X','v','X','F','X','X',' ',' ','X',' ','X'},
            {'X','v','X','F','R','E','X','_','X',' ','X'},
            {'X','v','X','F','X','X','X','X','#',' ','X'},
            {'X','v',' ',' ','W','W','W',' ','X',' ','X'},
            {'X','X',' ','X','X','W','X','^','X',' ','X'},
            {'X','L',' ','#','X','b','X','^','#','r','X'},
            {'X','X','X','X','X','X','X','X','X','X','X'}
        };

        int chips = countChips(grid);

        return new Levels(grid, chips);
    }

    /**
     * Returns the map layout being used
     * 
     * @return map - the map layout being used
     */
    public char[][] getMap() {
        return map;
    }

    /**
     * Returns the chips required for the map being used
     * 
     * @return chips - the chips required to pass the current map level
     */
    public int getChips() {
        return chips;
    }

    /**
     * Returns the current chips placed on the map, used to simulate passing a level when all the chips
     * has been collected
     * 
     * @param map - the map layout accepted by the method
     * @return - returns the number of chips the map has for requirement purposes
     */
    private static int countChips(char[][] map) {
        int count = 0;
        for (int y = 0; y < map.length; y++) {          
            for (int x = 0; x < map[y].length; x++) {   
                char tile = map[y][x];
                if (tile == '#') {
                    count++;
                }
            }
        }
        return count;
    }
}
