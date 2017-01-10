public class Weapon extends Gear
{
    // Basic damage info
    public int damage = 0;
    
    // Critical hit bonuses
    public double critChance = 0;
    public int critMultiplier = 1;
    
    // Weapon constructor
    public Weapon(int itemLevel, String weaponName, boolean artifact)
    {
        super(itemLevel, weaponName, artifact);
        damage = 8 + Randomizer.nextInt(itemLevel - 5, itemLevel + 5);
        if(damage < 8)
        {
            damage = 8;
        }
        critChance = Randomizer.nextInt(itemLevel) / 100.0;
        if(itemLevel > 10)
        {
            critMultiplier = Randomizer.nextInt(itemLevel - 10);
        }
    }
    
    // Weapon constructor for files loaded from saves
    public Weapon(int itemLevel, String weaponName, boolean artifact, int damage, double critChance, int critMultiplier)
    {
        super(itemLevel, weaponName, artifact);
        this.damage = damage;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
    }
    
    // Method to check if the weapon can level then levels it up and updates it's stats
    public void itemLevelUp(int itemLevel)
    {
        if(artifact)
        {
            this.itemLevel = itemLevel;
            damage = 8 + Randomizer.nextInt(itemLevel - 5, itemLevel + 5);
            if(damage < 8)
            {
                damage = 8;
            }
            critChance = Randomizer.nextInt(itemLevel) / 100.0;
            if(itemLevel > 10)
            {
                critMultiplier = Randomizer.nextInt(itemLevel - 10);
            }
        }
    }
    
    // Modification of the toString()
    public String printEquip()
    {
        return itemName + "\n  Item level: " + itemLevel + "\n  Damage: " + damage + "\n  Critical Chance Increase: " + critChance + "\n  Critical Multiplier Increase: " + critMultiplier;
    }
    
    public String toString()
    {
        return itemName + "\n Item level: " + itemLevel + "\n Damage: " + damage + "\n Critical Chance Increase: " + critChance + "\n Critical Multiplier Increase: " + critMultiplier + "\n";
    }
}
