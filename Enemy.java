public class Enemy extends Entity
{
    // Enemy damage variable
    public int damage;
    
    // Enemy experience reward
    public int rewardXP;
    
    // Enemy constructor
    public Enemy(int level, String name, boolean player)
    {
        super(level, name, player);
        
        damage = (int) (5 + level * Randomizer.nextDouble(0.0, 3.0));
        rewardXP = Randomizer.nextInt(level * Randomizer.nextInt(1, 3) * 7);
    }
    
    // Gives the Player the XP this Enemy had to offer
    public void rewardXP(Player player)
    {
        player.experience += rewardXP;
    }
    
    // Method for the Enemy to attack the player
    public void attack(Player player)
    {
        int temp = (int) (damage * (100.0 / (100.0 + player.equippedArmor.defense)));
        System.out.println(name + " attacks and deals " + temp + " damage.\n");
        player.currentHealth -= temp;
    }
    
    public String toString()
    {
        return name + "\n Health: " + currentHealth + "/" + maxHealth + "\n Mana: " + currentMana + "/" + maxMana + "\n Level: " + level + "\n Damage: " + damage + "\n";
    }
}
