import java.util.*;
import java.math.*;

public class Player extends Entity
{
    // Player's Experience
    public int experience = 0;
    
    // Player's equipped items
    public Weapon equippedWeapon = new Weapon(1, "Mohagany Stick", false);
    public Armor equippedArmor = new Armor(1, "Reynold's Wrap Tin Foil Armor", false);
    
    // Inventory for the player's potions
    public ArrayList<Consumable> inventory = new ArrayList<Consumable>();
    
    // Arrays for magic
    public ArrayList<Magic> spellbook = new ArrayList<Magic>();
    public ArrayList<Magic> unlearnedSpells = new ArrayList<Magic>();
    
    // Player constructor
    public Player(String name)
    {
        super(1, name, true);
        spellbook.add(new Magic(1.0, 10, "Magic Missle"));
        unlearnedSpells.add(new Magic(1.4, 20, "Lightning Bolt"));
        unlearnedSpells.add(new Magic(1.2, 10, "Frost bolt"));
        unlearnedSpells.add(new Magic(1.3, 15, "Fireball"));
        unlearnedSpells.add(new Magic(2.0, 150, "Nebulous Blast"));
        unlearnedSpells.add(new Magic(1.5, 90, "Blizzard"));
        unlearnedSpells.add(new Magic(1.5, 90, "Fire Storm"));
        for(int i = 0; i < 10; i++)
        {
            inventory.add(new Consumable(0.3, "Health Potion"));
        }
    }
    
    // Player constructor if player is loaded from file
    public Player(String name, int level, int experience, int inventoryCount, Weapon equippedWeapon, Armor equippedArmor, ArrayList<Magic> spellbook, ArrayList<Magic> unlearnedSpells)
    {
        super(level, name, true);
        this.experience = experience;
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        for(int i = 0; i < inventoryCount; i++)
        {
            inventory.add(new Consumable(0.3, "Health Potion"));
        }
        this.spellbook = spellbook;
        this.unlearnedSpells = unlearnedSpells;
    }
    
    // Method for the player to use a potion
    public void usePotion()
    {
        if((currentHealth + (maxHealth * inventory.get(0).healthRestore)) > maxHealth)
        {
            System.out.println("You used a potion. It restored " + ((int) ((maxHealth * inventory.get(0).healthRestore) - ((currentHealth + (maxHealth * inventory.get(0).healthRestore)) - maxHealth))) + " points of health.");
            currentHealth += ((int) ((maxHealth * inventory.get(0).healthRestore) - ((currentHealth + (maxHealth * inventory.get(0).healthRestore)) - maxHealth)));
        }
        else
        {
            System.out.println("You used a potion. It restored " + ((int) (maxHealth * inventory.get(0).healthRestore)) + " points of health.");
            currentHealth += (maxHealth * inventory.get(0).healthRestore);
        }
        System.out.println(printHealthBar() + "\n");
        inventory.remove(0);
    }
    
    // Method for the player to perform a basic attack
    public void attack(Enemy enemy)
    {
        if(equippedWeapon.critChance > 0.0)
        {
            if(Randomizer.nextBoolean(equippedWeapon.critChance))
            {
                enemy.currentHealth -= equippedWeapon.damage * equippedWeapon.critMultiplier;
            }
        }
        enemy.currentHealth -= equippedWeapon.damage;
    }
    
    // Method for the player to level up
    public void levelUp()
    {
        if(experience >= level * 107)
        {
            System.out.println("\nYou have leveled up!\nYour health and magic have been increased.\nFuture loot and enemies are now stronger.");
            if(level == 1)
            {
                level++;
            }
            else if(experience % 107 == 0)
            {
                level++;
            }
            else
            {
                level += (experience / 107.0) - level;
            }
            if(equippedWeapon.artifact || equippedArmor.artifact)
            {
                System.out.println("Your Artifacts have leveled up!");
                equippedWeapon.itemLevelUp(level);
                equippedArmor.itemLevelUp(level);
            }
            int temp = Randomizer.nextInt(unlearnedSpells.size());
            System.out.println("You learned " + unlearnedSpells.get(temp).spellName);
            spellbook.add(unlearnedSpells.get(temp));
            unlearnedSpells.remove(temp);
            updateStats();
        }
    }
    
    // Method for the player to equip a weapon
    public void equipWeapon(Weapon item)
    {
        equippedWeapon = item;
    }
    
    // Method for the player to equip a weapon
    public void equipArmor(Armor item)
    {
        equippedArmor = item;
    }
    
    // Method for the player to loot potions
    public void lootConsumable(Consumable item)
    {
        System.out.println(item);
        inventory.add(item);
    }
    
    // Method to get a string of a weapon and it's stat differences to the player's weapon
    public String printComparison(Weapon weapon)
    {
        return weapon.itemName + "\n Item Level: " + weapon.itemLevel + "\n Damage: " + weapon.damage + " (" + (weapon.damage - equippedWeapon.damage) + ")\n Critical Chance: " + weapon.critChance + " (" + (weapon.critChance - equippedWeapon.critChance) + ")\n Critical Multiplier: " + weapon.critMultiplier + " (" + (weapon.critMultiplier - equippedWeapon.critMultiplier) + ")\n";
    }
    
    // Method to get a string of an armor and it's stat differences to the player's armor
    public String printComparison(Armor armor)
    {
        return armor.itemName + "\n Item Level: " + armor.itemLevel + "\n Armor: " + armor.defense + " (" + (armor.defense - equippedArmor.defense) + ")\n";
    }
    
    // Method to get a formatted string of the player's spellbook
    public String spellbookString()
    {
        String temp1 = "";
        for(int i = 0; i < spellbook.size(); i++)
        {
            Magic temp2 = spellbook.get(i);
            temp1 += (i + 1) + ". " + temp2.spellName + " (" + temp2.getDamage(equippedWeapon) + ") " + "\n";
        }
        return temp1;
    }
    
    // Method to get a formatted string of the player's health
    public String printHealthBar()
    {
        return "Your health: " + currentHealth + "/" + maxHealth;
    }
    
    // Player's standard toString
    public String toString()
    {
        return name + "\n Health: " + currentHealth + "/" + maxHealth + "\n Mana: " + currentMana + "/" + maxMana + "\n Level: " + level + " (" + experience + "/" + (level * 107) + ")\n Potions: " + inventory.size() + "\n Equipped weapon: \n " + equippedWeapon.printEquip() + "\n Equipped armor: \n " + equippedArmor.printEquip() + "\n";
    }
}
