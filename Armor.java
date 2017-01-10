public class Armor extends Gear
{
    // Basic damage info
    public int defense = 0;
    
    // Armor constructor
    public Armor(int itemLevel, String armorName, boolean artifact)
    {
        super(itemLevel, armorName, artifact);
        defense = 10 + Randomizer.nextInt(itemLevel);
        if(defense < 10)
        {
            defense = 10;
        }
    }
    
    // Armor constructor for items loaded from saves
    public Armor(int itemLevel, String armorName, boolean artifact, int defense)
    {
        super(itemLevel, armorName, artifact);
        this.defense = defense;
    }
    
    // Method to check if this armor is allowed to level then, levels it up and updates stats
    public void itemLevelUp(int itemLevel)
    {
        if(artifact)
        {
            this.itemLevel = itemLevel;
            defense = 10 + Randomizer.nextInt(itemLevel);
            if(defense < 10)
            {
                defense = 10;
            }
        }
    }
    
    // Modification of the toString()
    public String printEquip()
    {
        return itemName + "\n  Armor: " + defense + "\n  Item Level: " + itemLevel;
    }
    
    public String toString()
    {
        return itemName + "\n Armor: " + defense + "\n Item Level: " + itemLevel + "\n";
    }
}
