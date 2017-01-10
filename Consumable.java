public class Consumable
{
    // Heal multiplier
    public double healthRestore;
    
    // Integer version of the heal multiplier used for strings
    public int healPercent;
    
    // Consumable name
    public String itemName;
    
    // Consumable constructor
    public Consumable(double healthRestore, String itemName)
    {
        this.healthRestore = healthRestore;
        this.healPercent = (int) (healthRestore * 100);
        this.itemName = itemName;
    }
    
    // Modification of the toString()
    public String getDescription()
    {
        return " " + itemName + "\n    This consumable restores " + healPercent + "% of your maximum health.\n";
    }
    
    public String toString()
    {
        return itemName + "\n This consumable restores " + healPercent + "% of your maximum health.\n";
    }
}
