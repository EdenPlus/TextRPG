public class Magic
{
    // Magic damage multiplier variable
    public double damagePercent;
    
    // Magic mana consumption variable
    public int manaConsume;
    
    // Spell name variable
    public String spellName;
    
    // Magic constructor
    public Magic(double damagePercent, int manaConsume, String spellName)
    {
        this.damagePercent = damagePercent;
        this.manaConsume = manaConsume;
        this.spellName = spellName;
    }
    
    // Method to get the damage calculation for the Magic
    public int getDamage(Weapon weapon)
    {
        if(((int) (weapon.damage * damagePercent)) <= 0)    return 1;
        return (int) (weapon.damage * damagePercent);
    }
}
