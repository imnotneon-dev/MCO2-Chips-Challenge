import javax.swing.ImageIcon;

public class ForceUpTile extends Tiles {

    public ForceUpTile() {
        super('^');
        sprite = new ImageIcon("ForceUpTile.png");
    }


    // or we put the old logic
    /*
    public static void applyForce(Chip chip, Maps map) {
        while (true) {
            char tileUnder = map.getTile(chip.getX(), chip.getY());
            if (!isForceTile(tileUnder)) {
                tileUnder = chip.getCurrentTileBelow();
                if (!isForceTile(tileUnder)) 
                    break;
            }

            int oldX = chip.getX();
            int oldY = chip.getY();
            int newX = oldX;
            int newY = oldY;

            switch (tileUnder) {
                case FORCE_UP:    
                    newY--; 
                    break;
                case FORCE_DOWN:  
                    newY++; 
                    break;
                case FORCE_LEFT:  
                    newX--; 
                    break;
                case FORCE_RIGHT: 
                    newX++; 
                    break;
                default: 
                    break;
            }

            if (!map.inBounds(newX, newY)) 
                break;

            char nextTile = map.getTile(newX, newY);

            if (!isWalkable(nextTile, chip.getInventory(), map.getRequiredChips()))
                break;

            map.setTile(oldX, oldY, tileUnder);

            chip.setX(newX);
            chip.setY(newY);

            chip.setCurrentTileBelow(nextTile);

            if (isCollectible(nextTile)) { 
                switch (nextTile) {
                    case Inventory.CHIP:
                        chip.getInventory().addChips();
                        break;
                    case Inventory.RED_KEY:
                        chip.getInventory().addRedKey();
                        break;
                    case Inventory.BLUE_KEY:
                        chip.getInventory().addBlueKey();
                        break;
                    case Inventory.FLIPPERS:
                        chip.getInventory().addFlippers();
                        break;
                    case Inventory.FIRE_BOOTS:
                        chip.getInventory().addFireBoots();
                        break;
                }
                chip.setCurrentTileBelow(BLANK);
                map.setTile(newX, newY, BLANK);
            }

            if (nextTile == WATER && !chip.getInventory().hasFlippers()) {
                chip.die();
                map.setTile(newX, newY, WATER);
                return;
            }
            if (nextTile == FIRE && !chip.getInventory().hasFireBoots()) {
                chip.die();
                map.setTile(newX, newY, FIRE);
                return;
            }

            map.setTile(newX, newY, Chip.CHIP);
            if (!isForceTile(nextTile)) 
                break;
        }
    }
    */
}
