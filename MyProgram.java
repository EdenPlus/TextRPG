import java.util.*;
import java.net.*;
import java.io.*;

// Warning: This program's IO operations should work in permanent storage.
// I believe it fails to do so due to CodeHS not giving the program write access to the programs own files.

// Resources for working with java.io.*
// How to use OutputStream: http://stackoverflow.com/a/1830948
// IO stuff: http://tutorials.jenkov.com/java-io/outputstreamwriter.html
// Specific line writing: http://stackoverflow.com/questions/25714779/how-can-i-write-to-a-specific-line-number-in-a-txt-file-in-java
// Clearing file: http://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself
// Newline: http://stackoverflow.com/questions/3596766/newline-in-filewriter

public class MyProgram extends ConsoleProgram
{
    // Arrays for Gear stuff
    public static final String[] equipmentPrefixes = {"", "Preserving ", "Aggresive ", "Threatening ", "Flawed ", "Thick ", "Throny ", "Perfect ", "Sharpened ", "Blunt ", "Piercing ", "Gilded ", "Hardcore ", "Impracticle ", "Archaic "};
    public static final String[] equipmentSuffixes = {"", " of Success", " of Aggression", " of Bad Parenting", " of Protection", " of Negativity", " of Motion Sickness", " of Domination", " of Angry"};
    public static final String[] weaponNames = {"Katar", "Staff", "Wombat on a Stick", "Longsword", "Shortsword", "Greatsword", "Battleaxe", "Dagger", "Butter Knife", "Shiv", "Carrot on a Stick", "Scimitar", "Maul", "Morningstar", "Katana", "Nikana", "Harsh Words", "Gauntlets", "Kindness", "Smashing Board", "Frisbee", "Yoyo"};
    public static final String[] armorNames = {"Leather Armor", "Chainmail", "Platemail", "Loin cloth", "FUPA", "Dorito Samurai Armor", "Mountain Dew Glaze", "Steam Knight Armor", "Thick Skin", "Positive Vibes"};
    
    // Arrays of artifacts
    public static final String[] artifactWeaponNames = {"Axe of TerROAR", "Reality Check of Perception", "Hammer of Thor", "Stained Glass Window", "Raw Fire"};
    public static final String[] artifactArmorNames = {"Plate of a Forsaken God", "Tattoos of Protection", "Unrelenting Belief", "Keemstar (Get it?)"};
    
    // Arrays for Enemy stuff
    public static final String[] enemyPrefixes = {"", "Intimidating ", "Overpowering ", "Dominating ", "Cruel ", "Torturous ", "Zealeous ", "Disappointing ", "Fervorous ", "Harmful ", "Barbaric ", "Ferocious ", "Furious ", "Chaotic "};
    public static final String[] enemyNames = {"Goblin", "Orc", "Wolf", "Troll", "Wyvern", "Dragon", "Kobold", "Gremlin", "Treant", "Cyclops", "Hydra", "Naga"};
    
    // Stuff for the save system
    public SaveFiles saves = new SaveFiles();
    public int encounterCount = 0;
    public int saveRequirement = 1; // 5;
    
    // Temporary (Author-side) variable being used for exitting the game
    public boolean breaker = false;
    
    // Primary method that runs all the stuff
    public void run()
    {
        readLine(" __          __  _                          \n \\ \\        / / | |                         \n  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___ \n   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\\n    \\  /\\  /  __/ | (_| (_) | | | | | |  __/\n     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|\n\nThis 'game' is for a final project.\nIt acts as a demonstration of my understanding.\nActions will be wrapped in hyphens (-LIKE SO-).\nYou only need to input the first letter for actions.\nI recommend reading everything for the first few fights.\nI expect you to be able to read/skim for information.\nIt's a \"Text RPG\" afterall.\n\nRead above before continuing.\nPress Enter(Return) to continue ");
        Player player = initializePlayer();
        
        readLine("\n-------------------------\n\nYour character sheet:\n" + player + "\nPress Enter(Return) to continue. ");
        
        while(true)
        {
            System.out.println("\n-------------------------\n");
            playEncounter(player);
            if(playerDied(player))
            {
                System.out.println("   _____                         ____                 \n  / ____|                       / __ \\                \n | |  __  __ _ _ __ ___   ___  | |  | |_   _____ _ __ \n | | |_ |/ _` | '_ ` _ \\ / _ \\ | |  | \\ \\ / / _ \\ '__|\n | |__| | (_| | | | | | |  __/ | |__| |\\ V /  __/ |   \n  \\_____|\\__,_|_| |_| |_|\\___|  \\____/  \\_/ \\___|_|   \n");
                while(true)
                {
                    String userInputString = readLine("You have died...\nWould you like to start over and play again? (Y/N) ").toUpperCase();
                    if(userInputString.length() > 0)
                    {
                        char userInput = userInputString.charAt(0);
                        if(userInput == 'Y')
                        {
                            player = new Player(readLine("\nWhat is your name? "));
                            
                            readLine("\nYour character sheet:\n" + player + "\nPress Enter(Return) to continue. ");
                        }
                        else if(userInput == 'N')
                        {
                            System.out.println("\nThanks for playing! Feel free to play again later!");
                            breaker = true;
                            break;
                        }
                        else
                        {
                            System.out.println("******************************************************\n*                                                    *\n*  Error:                                            *\n*     That is not a valid input.                     *\n*                                                    *\n******************************************************\n");
                        }
                        
                    }
                    else
                    {
                        System.out.println("******************************************************\n*                                                    *\n*  Error:                                            *\n*     You have not provided any input.               *\n*                                                    *\n******************************************************\n");
                    }
                }
            }
            else
            {
                while(true)
                {
                    String userInputString = readLine("\nWould you like to continue? (Y/N) ").toUpperCase();
                    if(userInputString.length() > 0)
                    {
                        char userInput = userInputString.charAt(0);
                        if(userInput == 'N')
                        {
                            System.out.println("\nThanks for playing! Feel free to play again later!");
                            breaker = true;
                            break;
                        }
                        else if(userInput == 'Y')
                        {
                            if(player.currentHealth < player.maxHealth)
                            {
                                int temp = (int) (player.maxHealth * 0.15);
                                if(player.currentHealth + temp > player.maxHealth)
                                {
                                    player.currentHealth += (player.maxHealth - player.currentHealth);
                                }
                                else
                                {
                                    player.currentHealth += temp;
                                }
                            }
                            break;
                        }
                        else
                        {
                            System.out.println("******************************************************\n*                                                    *\n*  Error:                                            *\n*     That is not a valid input.                     *\n*                                                    *\n******************************************************\n");
                        }
                    }
                    else
                    {
                        System.out.println("******************************************************\n*                                                    *\n*  Error:                                            *\n*     You have not provided any input.               *\n*                                                    *\n******************************************************\n");
                    }
                }
            }
            if(breaker)
            {
                break;
            }
            System.out.println();
        }
    }
    
    // Primary method, basically a snippet of the run()
    public void playEncounter(Player player)
    {
        Enemy currentEnemy = spawnRandomEnemy(player);
        readLine("You encounter a:\n" + currentEnemy + "\nPress Enter(Return) to begin combat. ");
        System.out.println();
        while(true)
        {
            System.out.println("-------------------------\n");
            getPlayerAction(player, currentEnemy);
            if(enemyDied(currentEnemy))
            {
                currentEnemy.rewardXP(player);
                player.levelUp();
                System.out.println("You have defeated the " + currentEnemy.name + ".\nYou have been awarded " + currentEnemy.rewardXP + " experience points.\nYou are now at: "  + player.experience + "/" + (player.level * 107));
                spawnRandomLoot(player);
                encounterCount++;
                if(encounterCount == saveRequirement)
                {
                    encounterCount = 0;
                    playerSaveCheck(player);
                }
                else
                {
                    System.out.println("You have survived " + encounterCount + " encounters.\nYou can save after " + (saveRequirement - encounterCount) + " more encounters.");
                }
                break;
            }
            currentEnemy.attack(player);
            if(playerDied(player))
            {
                break;
            }
            if(player.currentMana < player.maxMana)
            {
                int temp = (int) (player.maxMana * 0.04);
                if(player.currentMana + temp > player.maxMana)
                {
                    player.currentMana += (player.maxMana - player.currentMana);
                }
                else
                {
                    player.currentMana += temp;
                }
            }
            if(player.currentHealth < player.maxHealth)
            {
                int temp = (int) (player.maxHealth * 0.025);
                if(player.currentHealth + temp > player.maxHealth)
                {
                    player.currentHealth += (player.maxHealth - player.currentHealth);
                }
                else
                {
                    player.currentHealth += temp;
                }
            }
        }
    }
    
    // Method to get the Player's action and performs the specified action
    public void getPlayerAction(Player player, Enemy enemy)
    {
        while(true)
        {
            String userInputString = readLine("HP: " + player.currentHealth + "/" + player.maxHealth + "\tPotions: " + player.inventory.size() + "\nMP: " + player.currentMana + "/" + player.maxMana + "\tDamage: " + player.equippedWeapon.damage + "\nWhat would you like to do?\n-ATTACK- -MAGIC- -POTION-\n").toUpperCase();
            if(userInputString.length() > 0)
            {
                char userInput = userInputString.charAt(0);
                System.out.println();
                if(userInput == 'A')
                {
                    System.out.println("You attack for " + player.equippedWeapon.damage + " damage.");
                    player.attack(enemy);
                    System.out.println(enemy);
                    break;
                }
                else if(userInput == 'M')
                {
                    while(true)
                    {
                        int spellBookIndex = readInt("Your spellbook:\n\n" + player.spellbookString() + "\nIndex of the spell you want to use: ") - 1;
                        System.out.println();
                        if(spellBookIndex >= 0 && spellBookIndex < player.spellbook.size())
                        {
                            Magic castingSpell = player.spellbook.get(spellBookIndex);
                            if(player.currentMana - castingSpell.manaConsume >= 0)
                            {
                                player.currentMana -= castingSpell.manaConsume;
                                enemy.currentHealth -= castingSpell.getDamage(player.equippedWeapon);
                                System.out.println("You cast " + castingSpell.spellName + " and deal " + castingSpell.getDamage(player.equippedWeapon) + " damage.\n" + enemy);
                                break;
                            }
                            else
                            {
                                System.out.println("You don't have enough mana to cast that spell.\n");
                            }
                        }
                        else
                        {
                            System.out.println("******************************************************\n*                                                    *\n*  Error:                                            *\n*     That is not an index in the spellbook.         *\n*                                                    *\n*  Hint:                                             *\n*     The index is the number with the dot after it. *\n*                                                    *\n******************************************************\n");
                        }
                    }
                    break;
                }
                else if(userInput == 'P')
                {
                    if(!(player.inventory.isEmpty()))
                    {
                        player.usePotion();
                        break;
                    }
                    else
                    {
                        System.out.println("You don't have any potions.\n");
                    }
                }
                else
                {
                    System.out.println("******************************************************\n*                                                    *\n*  Error:                                            *\n*     That is not an available action.               *\n*                                                    *\n******************************************************\n");
                }
            }
            else
            {
                System.out.println("******************************************************\n*                                                    *\n*  Error:                                            *\n*     You have not provided any input.               *\n*                                                    *\n******************************************************\n");
            }
        }
    }
    
    // Helper method to check if the player is dead
    public boolean playerDied(Player player)
    {
        if(player.currentHealth <= 0)
        {
            return true;
        }
        return false;
    }
    
    // Helper method to check if the enemy is dead
    public boolean enemyDied(Enemy enemy)
    {
        if(enemy.currentHealth <= 0)
        {
            return true;
        }
        return false;
    }
    
    // Method to generate a random enemy
    public Enemy spawnRandomEnemy(Player player)
    {
        return new Enemy(Randomizer.nextInt(1, player.level + 2), enemyPrefixes[Randomizer.nextInt(enemyPrefixes.length)] + enemyNames[Randomizer.nextInt(enemyNames.length)], false);
    }
    
    /** 
     *  A lot goes on in this method
     *  
     *  1. Decides if the player gets an artifact or a common
     *  2. Decides what kind of item the Player gets
     *  3. Uses helper methods to generate an item
     *  4. Displays item and it's stat differences
     *  5a. Checks if the Player wants to equip the item (if it's equippable)
     *  5b. Adds item to Player inventory (if it's a consumable)
     */
    public void spawnRandomLoot(Player player)
    {
        if(Randomizer.nextBoolean(0.05))
        {
            System.out.println("\nYou have found an Artifact!\nThis item will scale to your level.");
            int temp1 = Randomizer.nextInt(2);
            if(temp1 == 0)
            {
                Weapon temp = new Weapon(player.level, artifactWeaponNames[Randomizer.nextInt(artifactWeaponNames.length)], true);
                System.out.println(player.printComparison(temp));
                playerEquip(temp, player);
            }
            else if(temp1 == 1)
            {
                Armor temp = new Armor(player.level, artifactArmorNames[Randomizer.nextInt(artifactArmorNames.length)], true);
                System.out.println(player.printComparison(temp));
                playerEquip(temp, player);
            }
        }
        else
        {
            System.out.println("\nYou have found a(n):");
            int temp1 = Randomizer.nextInt(3);
            if(temp1 == 0)
            {
                Weapon temp = new Weapon(player.level, generateRandomName(player, "WEAPON"), false);
                System.out.println(player.printComparison(temp));
                playerEquip(temp, player);
            }
            else if(temp1 == 1)
            {
                Armor temp = new Armor(player.level, generateRandomName(player, "ARMOR"), false);
                System.out.println(player.printComparison(temp));
                playerEquip(temp, player);
            }
            else
            {
                player.lootConsumable(new Consumable(0.3, "Health Potion"));
                System.out.println("It has been added to your inventory.\n");
            }
        }
    }
    
    // Helper method to generate a random Weapon/Armor name for non-artifacts
    public String generateRandomName(Player player, String type)
    {
        if(type.equals("WEAPON"))
        {
            return equipmentPrefixes[Randomizer.nextInt(equipmentPrefixes.length)] + weaponNames[Randomizer.nextInt(weaponNames.length)] + equipmentSuffixes[Randomizer.nextInt(equipmentSuffixes.length)];
        }
        else if(type.equals("ARMOR"))
        {
            return equipmentPrefixes[Randomizer.nextInt(equipmentPrefixes.length)] + armorNames[Randomizer.nextInt(armorNames.length)] + equipmentSuffixes[Randomizer.nextInt(equipmentSuffixes.length)];
        }
        return "Error: Something went wrong with gear generation";
    }
    
    // Method to checks if the Player wants to equip the passed Weapon
    public void playerEquip(Weapon weapon, Player player)
    {
        while(true)
        {
            String userInputString = readLine("Would you like to equip this? (Y/N) ").toUpperCase();
            if(userInputString.length() > 0)
            {
                char userInput = userInputString.charAt(0);
                if(userInput == 'Y')
                {
                    player.equipWeapon(weapon);
                    System.out.println(weapon.itemName + " has been equiped.\nYour previous weapon has been deleted.\n");
                    break;
                }
                else if(userInput == 'N')
                {
                    String userConfirmString = readLine("If you do not want to equip this, it will be deleted.\nAre you sure you don't want to equip this? (Y/N) ").toUpperCase();
                    if(userConfirmString.length() > 0)
                    {
                        char userConfirm = userConfirmString.charAt(0);
                        if(userConfirm == 'Y')
                        {
                            System.out.println(weapon.itemName + " has been deleted.\n");
                            break;
                        }
                    }
                    else
                    {
                        System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*     You have not provided any input.               *\n*                                                    *\n******************************************************\n");
                    }
                }
                else
                {
                    System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*     That is not a valid option.                    *\n*                                                    *\n******************************************************\n");
                }
            }
            else
            {
                System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*     You have not provided any input.               *\n*                                                    *\n******************************************************\n");
            }
        }
    }
    
    // Method to check if the Player wants to equip the passed Armor
    public void playerEquip(Armor armor, Player player)
    {
        while(true)
        {
            String userInputString = readLine("Would you like to equip this? (Y/N) ").toUpperCase();
            if(userInputString.length() > 0)
            {
                char userInput = userInputString.charAt(0);
                if(userInput == 'Y')
                {
                    player.equipArmor(armor);
                    System.out.println(armor.itemName + " has been equiped.\nYour previous armor has been deleted.\n");
                    break;
                }
                else if(userInput == 'N')
                {
                    String userConfirmString = readLine("If you do not want to equip this, it will be deleted.\nAre you sure you don't want to equip this? (Y/N) ").toUpperCase();
                    if(userConfirmString.length() > 0)
                    {
                        char userConfirm = userConfirmString.charAt(0);
                        if(userConfirm == 'Y')
                        {
                            System.out.println(armor.itemName + " has been deleted.\n");
                            break;
                        }
                    }
                    else
                    {
                        System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    You have not provided any input.                *\n*                                                    *\n******************************************************\n");
                    }
                }
                else
                {
                    System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    That is not a valid option.                     *\n*                                                    *\n******************************************************\n");
                }
            }
            else
            {
                System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    You have not provided any input.                *\n*                                                    *\n******************************************************\n");
            }
        }
    }
    
    // Initialize the Player variable
    public Player initializePlayer()
    {
        while(true)
        {
            String temp3 = readLine("\nWhat would you like to do?\n-NEW GAME- -LOAD SAVE- -DELETE SAVE-\n").toUpperCase();
            if(temp3.length() > 0)
            {
                fileSwitch: switch(temp3.charAt(0))
                {
                    case 'N':
                        return new Player(readLine("\nWhat is your name? "));
                    case 'L':
                        if(saves.SAVEFILES.size() != 0)
                        {
                            String storage = "";
                            for(int h = 0; h < saves.SAVEFILES.size(); h++)
                            {
                                storage += "" + (h + 1) + ". " + saves.SAVEFILES.get(h).name + "\n";
                            }
                            for(int i = saves.SAVEFILES.size(); i < 3; i++)
                            {
                                storage += "" + (i + 1) + ". No save data\n";
                            }
                            loadFileLoop: while(true)
                            {
                                int temp2 = readInt("\nWhich file?\n" + storage);
                                if(temp2 > 0 && temp2 <= saves.SAVEFILES.size())
                                {
                                    return saves.SAVEFILES.get(temp2 - 1);
                                }
                                else
                                {
                                    System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    Invalid input, try again.                       *\n*                                                    *\n******************************************************\n");
                                }
                            }
                        }
                        else
                        {
                            System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    There is no save data.                          *\n*                                                    *\n******************************************************\n");
                        }
                    case 'D':
                        if(saves.SAVEFILES.size() != 0)
                        {
                            String storage = "";
                            for(int h = 0; h < saves.SAVEFILES.size(); h++)
                            {
                                storage += "" + (h + 1) + ". " + saves.SAVEFILES.get(h).name + "\n";
                            }
                            for(int i = saves.SAVEFILES.size(); i < 3; i++)
                            {
                                storage += "" + (i + 1) + ". No save data\n";
                            }
                            while(true)
                            {
                                int temp2 = readInt("\nWhich file?\n" + storage);
                                if(temp2 > 0 && temp2 <= saves.SAVEFILES.size())
                                {
                                    try {
                                        FileWriter fw = new FileWriter("player" + temp2 + ".txt");
                                        PrintWriter pw = new PrintWriter(fw);
                                        pw.write("No save data");
                                        pw.flush();
                                        pw.close();
                                        
                                        saves = new SaveFiles();
                                        
                                        break fileSwitch;
                                    }
                                    
                                    catch (IOException e) {
                                        System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    Failure to get player" + temp2 + ".txt                      *\n*                                                    *\n******************************************************\n");
                            			e.printStackTrace();
                                    }
                                }
                                else
                                {
                                    System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    Invalid input, try again.                       *\n*                                                    *\n******************************************************\n");
                                }
                            }
                        }
                        else
                        {
                            System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    There is no save data.                          *\n*                                                    *\n******************************************************\n");
                        }
                    default:
                        System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    Invalid input, try again.                       *\n*                                                    *\n******************************************************\n");
                }
            }
            else
            {
                System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    No input given, try again.                      *\n*                                                    *\n******************************************************\n");
            }
        }
    }
    
    // Method to check if the player wants to save their progress
    public void playerSaveCheck(Player player)
    {
        saveFileCheckLoop: while(true)
        {
            switch(readLine("Would you like to save? (Y/N) ").toUpperCase())
            {
                case "Y":
                    String storage = "";
                    for(int h = 0; h < saves.SAVEFILES.size(); h++)
                    {
                        storage += "" + (h + 1) + ". " + saves.SAVEFILES.get(h).name + "\n";
                    }
                    for(int i = saves.SAVEFILES.size(); i < 3; i++)
                    {
                        storage += "" + (i + 1) + ". No save data\n";
                    }
                    saveFileLoop: while(true)
                    {
                        int temp2 = readInt("\nWhich file would you like to save to?\n" + storage);
                        if(temp2 > 0 && temp2 <= 3)
                        {
                            playerSaveWrite(player, temp2);
                            saves = new SaveFiles();
                            break saveFileLoop;
                        }
                        else
                        {
                            System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    Invalid input, try again.                       *\n*                                                    *\n******************************************************\n");
                        }
                    }
                    break saveFileCheckLoop;
                case "N":
                    switch(readLine("Are you sure you don't want to save?\nYou can only save every " + saveRequirement + " encounters. (Y/N) ").toUpperCase())
                    {
                        case "Y":
                            System.out.println("Your progress will not be saved.\nLet's hope that doesn't turn out to be a mistake.");
                            break saveFileCheckLoop;
                        default:
                            System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    Invalid input, try again.                       *\n*                                                    *\n******************************************************\n");
                    }
                    break;
                default:
                    System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    Invalid input, try again.                       *\n*                                                    *\n******************************************************\n");
            }
        }
    }
    
    // Method to save the player's progress
    public void playerSaveWrite(Player player, int fileNumber)
    {
        try
        {
            FileWriter fw = new FileWriter("player" + fileNumber + ".txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.write("");
            
            ArrayList<String> playerDataStrings = initializePlayerDataArray(player);
            
            for(int c = 0; c < playerDataStrings.size(); c++)
            {
                pw.append(playerDataStrings.get(c) + System.getProperty("line.separator"));
            }
            
            pw.flush();
            pw.close();
            
            saves = new SaveFiles();
        }
        
        catch (IOException e) {
            System.out.println("\n******************************************************\n*                                                    *\n*  Error:                                            *\n*    Failure to get player" + fileNumber + ".txt                      *\n*                                                    *\n******************************************************\n");
			e.printStackTrace();
        }
    }
    
    /**
     * A method to get the ArrayList for the player's data.
     * Ignore how awful it is to look at this.
     * I have no theories on how to turn this into a loop at this time.
     */
    public ArrayList<String> initializePlayerDataArray(Player player)
    {
        ArrayList<String> playerDataStrings = new ArrayList<String>();
        playerDataStrings.add("**PLAYER**");
        playerDataStrings.add(player.name);
        playerDataStrings.add(Integer.toString(player.level));
        playerDataStrings.add(Integer.toString(player.experience));
        playerDataStrings.add(Integer.toString(player.inventory.size()));
        playerDataStrings.add("======");
        playerDataStrings.add("**WEAPON**");
        playerDataStrings.add(Integer.toString(player.equippedWeapon.itemLevel));
        playerDataStrings.add(player.equippedWeapon.itemName);
        playerDataStrings.add(Boolean.toString(player.equippedWeapon.artifact));
        playerDataStrings.add(Integer.toString(player.equippedWeapon.damage));
        playerDataStrings.add(Double.toString(player.equippedWeapon.critChance));
        playerDataStrings.add(Integer.toString(player.equippedWeapon.critMultiplier));
        playerDataStrings.add("======");
        playerDataStrings.add("**ARMOR**");
        playerDataStrings.add(Integer.toString(player.equippedArmor.itemLevel));
        playerDataStrings.add(player.equippedArmor.itemName);
        playerDataStrings.add(Boolean.toString(player.equippedArmor.artifact));
        playerDataStrings.add(Integer.toString(player.equippedArmor.defense));
        playerDataStrings.add("======");
        playerDataStrings.add("**SPELLBOOK**");
        playerDataStrings.add(Integer.toString(player.spellbook.size()));
        for(int a = 0; a < player.spellbook.size(); a++)
        {
            playerDataStrings.add(Double.toString(player.spellbook.get(a).damagePercent));
            playerDataStrings.add(Integer.toString(player.spellbook.get(a).manaConsume));
            playerDataStrings.add(player.spellbook.get(a).spellName);
            playerDataStrings.add("------");
        }
        playerDataStrings.add("======");
        playerDataStrings.add("**UNLEARNEDSPELLS**");
        playerDataStrings.add(Integer.toString(player.unlearnedSpells.size()));
        for(int b = 0; b < player.unlearnedSpells.size(); b++)
        {
            playerDataStrings.add(Double.toString(player.unlearnedSpells.get(b).damagePercent));
            playerDataStrings.add(Integer.toString(player.unlearnedSpells.get(b).manaConsume));
            playerDataStrings.add(player.unlearnedSpells.get(b).spellName);
            playerDataStrings.add("------");
        }
        playerDataStrings.add("======");
        
        return playerDataStrings;
    }
}
