public class Gear
{
    // This class isn't extremely necessary, just cuts down a small bit on some typing
    // Primary variables used to setup stuff
    public int itemLevel;
    public String itemName;
    
    // Decides if the item is subject to leveling up
    public boolean artifact;
    
    // Gear constructor
    public Gear(int itemLevel, String itemName, boolean artifact)
    {
        this.itemLevel = itemLevel;
        this.itemName = itemName;
        this.artifact = artifact;
    }
}
