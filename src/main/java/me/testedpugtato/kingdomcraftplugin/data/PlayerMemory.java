package me.testedpugtato.kingdomcraftplugin.data;

import me.testedpugtato.kingdomcraftplugin.powers.*;

public class PlayerMemory {
    // Database variables
    private int powerLevel;
    private int playerLevel;
    private int powerEXP;
    private int playerEXP;
    private Power power;
    private int powerSlot = 8;
    private boolean isKing = false;


    // Temporary variables
    public int shiftCount = 0;


    public void addPowerLevel(int x) {
        powerLevel+=x;
        if(powerLevel <= 0)
            powerLevel = 1;
    }
    public void addPlayerLevel(int x) {
        playerLevel+=x;
        if(playerLevel <= 0)
            playerLevel = 1;
    }

    public void addPowerEXP(int xp) {
        powerEXP += xp;
        for(int i = 0; i < 100; i++)
        {
            // Get amount needed till next level
            double xpNeeded = powerEXPNeeded();

            if(powerEXP>=xpNeeded)
            {
                addPowerLevel(1);
                powerEXP -= xpNeeded;
            }
        }
    }

    public void addPlayerEXP(int xp) {
        playerEXP += xp;
        for(int i = 0; i < 100; i++)
        {
            // Get amount needed till next level
            double xpNeeded = playerEXPNeeded();

            if(playerEXP>=xpNeeded)
            {
                addPlayerLevel(1);
                playerEXP -= xpNeeded;
            }
        }
    }

    public double powerEXPNeeded(){
        double xpNeeded = powerLevel + 1;
        xpNeeded *= xpNeeded;
        xpNeeded *= 8;
        xpNeeded /= 5;
        return xpNeeded;
    }

    public double playerEXPNeeded(){
        double xpNeeded = playerLevel + 1;
        xpNeeded *= xpNeeded;
        xpNeeded *= 4;
        xpNeeded /= 5;
        return xpNeeded;
    }


    public int getPowerLevel() { return powerLevel; }
    public void setPowerLevel(int level) { this.powerLevel = level; }
    public int getPowerEXP() { return powerEXP; }
    public void setPowerEXP(int EXP) { this.powerEXP = EXP; }
    public Power getPower() { return power; }
    public void setPower(Power power) { this.power = power; }
    public void setPower(String power)
    {
        switch(power)
        {
            case "fire":
                this.power = new Fire();
                break;
            case "water":
                this.power = new Water();
                break;
            case "lightning":
                this.power = new Lightning();
                break;
            case "samurai":
                this.power = new Samurai();
                break;
            case "mage":
                this.power = new Mage();
                break;
            case "earth":
                this.power = new Earth();
                break;
            case "air":
                this.power = new Air();
                break;
            default:
                this.power = new Power();
        }
    }
    public int getPlayerLevel() { return playerLevel; }

    public void setPlayerLevel(int playerLevel) { this.playerLevel = playerLevel; }

    public int getPlayerEXP() { return playerEXP; }

    public void setPlayerEXP(int playerEXP) { this.playerEXP = playerEXP; }
    public int getPowerSlot() { return powerSlot; }
    public void setPowerSlot(int powerSlot) { this.powerSlot = powerSlot; }
    public boolean isKing() { return isKing; }
    public void setKing(boolean king) { isKing = king; }

    public String toString()
    {
        String s = "Power: ";
        s += power.id + "\n";
        s += "Power Level: ";
        s += Integer.toString(powerLevel) + "\n";
        s += "Power Level EXP: ";
        s += Integer.toString(powerEXP) + "\n";
        s += "Power Level EXP needed till next level: ";
        s += Integer.toString((int)powerEXPNeeded()) + "\n";
        s += "Player Level: ";
        s += Integer.toString(playerLevel) + "\n";
        s += "Player Level EXP: ";
        s += Integer.toString(playerEXP) + "\n";
        s += "Player Level EXP needed till next level: ";
        s += Integer.toString((int)playerEXPNeeded()) + "\n";
        return s;
    }
}
