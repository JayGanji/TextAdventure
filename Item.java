
/**
 * Class Item - an item in an adventure game.
 *
 * An item can be situated in any one of the rooms in Zuul
 * Items can have descriptions and weights associated with them.
 *
 * @author (Jay Ganji)
 * @version (03/22/2021)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, int weight)
    {
        // initialise instance variables
        this.description = description;
        this.weight = weight;
    }

}
