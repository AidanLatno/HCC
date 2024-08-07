package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class Power
{

    public String id;
    private float basicCooldown;
    private float quickCooldown;
    private float dashCooldown;
    private float chargedCooldown;
    private float arielCooldown;
    private float slamCooldown;
    public boolean isDomainActive = false;
    public PlayerMemory memory;

    public Power()
    {
        id = "no power";
    }

    public void useBasicAttack(Player player, int powerLevel)
    {
        player.sendMessage("[ERROR] Basic Attack Not Overloaded");
    }
    public void useAriel(Player player, int playerLevel)
    {
        player.sendMessage("[ERROR] Ariel Not Overloaded");
    }
    public void useArielDash(Player player, int powerLevel)
    {
        player.sendMessage("[ERROR] Ariel Dash Not Overloaded");
    }
    public void chargeChargedAttack(Player player, int powerLevel, double charge)
    {
        player.sendMessage(("[ERROR] Charge Charged Attack Not Overloaded"));
    }
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {
        player.sendMessage("[ERROR] Charged Attack Not Overloaded");
    }
    public void useQuickAttack(Player player, int powerLevel)
    {
        player.sendMessage("[ERROR] Quick Attack Not Overloaded");
    }
    public void useGroundSlam(Player player, int powerLevel)
    {
        player.sendMessage("[ERROR] Ground Slam Not Overloaded");
    }
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {
        player.sendMessage("[ERROR] Ground Slam Fall Not Overloaded");
    }
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        player.sendMessage("[ERROR] Ground Slam Landing Not Overloaded");
    }
    public float getBasicCooldown() { return basicCooldown; }

    public void setBasicCooldown(float basicCooldown) { this.basicCooldown = basicCooldown; }

    public float getQuickCooldown() { return quickCooldown; }

    public void setQuickCooldown(float quickCooldown) { this.quickCooldown = quickCooldown; }

    public float getDashCooldown() { return dashCooldown; }

    public void setDashCooldown(float dashCooldown) { this.dashCooldown = dashCooldown; }

    public float getChargedCooldown() { return chargedCooldown; }

    public void setChargedCooldown(float chargedCooldown) { this.chargedCooldown = chargedCooldown; }

    public float getArielCooldown() { return arielCooldown; }

    public void setArielCooldown(float arielCooldown) { this.arielCooldown = arielCooldown; }

    public float getSlamCooldown() { return slamCooldown; }

    public void setSlamCooldown(float slamCooldown) { this.slamCooldown = slamCooldown; }

    public void domainExpand(Player player) {}
}
