package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class Power
{

    public String id = "no power";
    private float basicCooldown = 1;
    private float quickCooldown = 1;
    private float dashCooldown = 1;
    private float chargedCooldown = 1;
    private float arielCooldown = 1;
    private float slamCooldown = 1;
    public boolean isDomainActive = false;
    public PlayerMemory memory;

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


    public static Material[] ignoreList = {
            Material.AIR,
            Material.GRASS,
            Material.TALL_GRASS,
            Material.SEAGRASS,
            Material.TALL_SEAGRASS,
            Material.SEA_PICKLE,
            Material.KELP_PLANT,
            Material.OXEYE_DAISY,
            Material.DANDELION,
            Material.ROSE_BUSH,
            Material.POPPY,
            Material.AZURE_BLUET,
            Material.CORNFLOWER,
            Material.SUNFLOWER,
            Material.BAMBOO,
            Material.SMALL_DRIPLEAF,
            Material.BIG_DRIPLEAF,
            Material.BIG_DRIPLEAF_STEM,
            Material.COBWEB,
            Material.FIRE,
            Material.SOUL_FIRE,
            Material.BLUE_ORCHID,
            Material.ALLIUM,
            Material.PEONY,
            Material.ORANGE_TULIP,
            Material.PINK_TULIP,
            Material.RED_TULIP,
            Material.WHITE_TULIP,
            Material.LILAC,
            Material.LILY_OF_THE_VALLEY,
            Material.LILY_PAD,
            Material.WITHER_ROSE,
            Material.TORCH,
            Material.SOUL_TORCH
    };
}
