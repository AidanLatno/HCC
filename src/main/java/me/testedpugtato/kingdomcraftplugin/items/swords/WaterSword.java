package me.testedpugtato.kingdomcraftplugin.items.swords;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class WaterSword extends Sword{
    public WaterSword()
    {
        name = ChatColor.BLUE + ChatColor.BOLD.toString() + "Water Sword";
        lore.add(ChatColor.DARK_RED + "A sword infused with water blood.");
        CustomModelData = 2;
        baseItem = Material.NETHERITE_SWORD;
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel, float swordDamage)
    {

    }

    @Override
    public void useAriel(Player player, int powerLevel, float swordDamage)
    {

    }
    @Override
    public void useArielDash(Player player, int powerLevel, float swordDamage)
    {

    }
    @Override
    public void useQuickAttack(Player player, int powerLevel, float swordDamage)
    {

    }
    @Override
    public void useGroundSlam(Player player, int powerLevel, float swordDamage)
    {

    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge, float swordDamage)
    {

    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge, float swordDamage)
    {

    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {

    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {

    }
}
