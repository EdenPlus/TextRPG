public class Entity
{
    // Entity health variables
    public int maxHealth;
    public int currentHealth;
    
    // Entity mana variables
    public int maxMana;
    public int currentMana;
    
    // Entity level
    public int level;
    
    // Entity name
    public String name;
    
    // Entity constructor
    public Entity(int level, String name, boolean player)
    {
        this.level = level;
        this.name = name;
        
        if(player)
        {
            maxHealth = (level * 6) + 100;
            currentHealth = maxHealth;
            maxMana = (level * 6) + 94;
            currentMana = maxMana;
        }
        else
        {
            maxHealth = (level * Randomizer.nextInt(1, 10)) + 100;
            currentHealth = maxHealth;
            maxMana = (level * Randomizer.nextInt(1, 8)) + 93;
            currentMana = maxMana;
        }
    }
    
    // Method to update stats based on level
    // Basically reconstructs the instance/sub-instance of this class
    public void updateStats()
    {
        maxHealth = (level * 6) + 100;
        currentHealth = maxHealth;
        maxMana = (level * 6) + 94;
        currentMana = maxMana;
    }
}
