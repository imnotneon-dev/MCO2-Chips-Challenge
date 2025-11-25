import javax.swing.ImageIcon;

public abstract class Item {
    private final String name;
    private final char symbol;
    protected ImageIcon sprite;

    public Item(String name, char symbol, ImageIcon sprite) {
        this.name = name;
        this.symbol = symbol;
        this.sprite = sprite;
    }


    public char getSymbol() { 
        return symbol; 
    }

    public String getName() {
        return name;
    }

    public ImageIcon getSprite() {
        return sprite;
    }
}
