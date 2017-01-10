import java.util.*;
import java.net.*;
import java.io.*;

public class SaveFiles
{
    String[] fileNames = {"player1.txt", "player2.txt", "player3.txt"};
    
    public ArrayList<Player> SAVEFILES = new ArrayList<Player>();
    
    ArrayList<String> lines;
    private int currentLineIndex;
    private int fileLineLimit = 53;
    
    public SaveFiles()
    {
        for(String i : fileNames)
        {
            currentLineIndex = 0;
            lines = loadSaveFile(i);
            
            if(!lines.get(0).equals("No save data"))
            {
                ArrayList<String> tempPlayer = new ArrayList<String>();
                ArrayList<String> tempWeapon = new ArrayList<String>();
                ArrayList<String> tempArmor = new ArrayList<String>();
                ArrayList<String[]> tempSpellbook = new ArrayList<String[]>();
                ArrayList<String[]> tempUnlearnedSpells = new ArrayList<String[]>();
                
                while(hasMoreLines())
                {
                    String currentLine = getNextLine();
                    switch(currentLine)
                    {
                        case "**PLAYER**":
                            for(int a = 0; a < 4; a++)
                            {
                                tempPlayer.add(getNextLine());
                            }
                            break;
                        case "**WEAPON**":
                            for(int b = 0; b < 6; b++)
                            {
                                tempWeapon.add(getNextLine());
                            }
                            break;
                        case "**ARMOR**":
                            for(int c = 0; c < 4; c++)
                            {
                                tempArmor.add(getNextLine());
                            }
                            break;
                        case "**SPELLBOOK**":
                            int temp2 = Integer.parseInt(getNextLine());
                            for(int d = 0; d < temp2; d++)
                            {
                                String[] tempMagic = {getNextLine(), getNextLine(), getNextLine()};
                                tempSpellbook.add(tempMagic);
                                currentLineIndex++;
                            }
                            break;
                        case "**UNLEARNEDSPELLS**":
                            int temp3 = Integer.parseInt(getNextLine());
                            for(int e = 0; e < temp3; e++)
                            {
                                String[] tempMagic = {getNextLine(), getNextLine(), getNextLine()};
                                tempUnlearnedSpells.add(tempMagic);
                                currentLineIndex++;
                            }
                            break;
                        default:
                            break;
                    }
                }
                
                Weapon tWeapon = new Weapon(Integer.parseInt(tempWeapon.get(0)), tempWeapon.get(1), Boolean.parseBoolean(tempWeapon.get(2)), Integer.parseInt(tempWeapon.get(3)), Double.parseDouble(tempWeapon.get(4)), Integer.parseInt(tempWeapon.get(5)));
                Armor tArmor = new Armor(Integer.parseInt(tempArmor.get(0)), tempArmor.get(1), Boolean.parseBoolean(tempArmor.get(2)), Integer.parseInt(tempArmor.get(3)));
                
                ArrayList<Magic> tSpellbook = new ArrayList<Magic>();
                ArrayList<Magic> tUnlearnedSpells = new ArrayList<Magic>();
                
                for(int f = 0; f < tempSpellbook.size(); f++)
                {
                    String[] temp4 = tempSpellbook.get(f);
                    tSpellbook.add(new Magic(Double.parseDouble(temp4[0]), Integer.parseInt(temp4[1]), temp4[2]));
                }
                for(int g = 0; g < tempUnlearnedSpells.size(); g++)
                {
                    String[] temp5 = tempUnlearnedSpells.get(g);
                    tUnlearnedSpells.add(new Magic(Double.parseDouble(temp5[0]), Integer.parseInt(temp5[1]), temp5[2]));
                }
                
                SAVEFILES.add(new Player(tempPlayer.get(0), Integer.parseInt(tempPlayer.get(1)), Integer.parseInt(tempPlayer.get(2)), Integer.parseInt(tempPlayer.get(3)), tWeapon, tArmor, tSpellbook, tUnlearnedSpells));
            }
        }
    }
	
	/**
	 * Returns true if there are still more lines to
	 * be returned.
	 * Returns false otherwise
	 */
	public boolean hasMoreLines()
	{
		return currentLineIndex < lines.size();
	}
	
	/**
	 * Returns the next line in the file that has
	 * not yet been returned. If there are no lines
	 * left, this method returns null.
	 */
	public String getNextLine()
	{
		if(!hasMoreLines())
		{
			return null;
		}
		String line = lines.get(currentLineIndex);
		currentLineIndex++;
		return line;
	}
	
	/**
	 * Converts the contents of the file located at `filename`
	 * into an ArrayList of Strings. 
	 * 
	 * Returns an ArrayList of Strings where each String is
	 * one line of the file. The Strings are stored in the 
	 * ArrayList in the same order they appear in the file.
	 * 
	 * If there are errors reading the file, returns an empty ArrayList
	 */
	private ArrayList<String> loadSaveFile(String filename)
	{
	    // Construct an empty ArrayList
		ArrayList<String> linesInFile = new ArrayList<String>();
		
		// Try to do the following block of code. If any errors
		// occur, they will be caught by the "catch" statements below.
		try
		{
		    // Create a BufferedReader to read the contents of the file
			FileReader fileReader = new FileReader(filename);
			BufferedReader input = new BufferedReader(fileReader);
			
			// Store each line of the file in the ArrayList
			String currentLine = input.readLine();
			while(currentLine != null)
			{
				linesInFile.add(currentLine);
				currentLine = input.readLine();
			}
			
			// Close the input stream
			input.close();
		}
	
		// If there were any errors when reading the file
		// they will be handled by these `catch` clauses
		catch (FileNotFoundException e) {
			System.out.println("Couldn't open file: " + filename);
		} catch (IOException e) {
			System.out.println("There was an error while reading the file: " + filename);
			e.printStackTrace();
		}
		
		return linesInFile;
	}
}
