package me.testedpugtato.kingdomcraftplugin.items.swords;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class BloodStoneSword extends Sword
{
    public BloodStoneSword()
    {
        name = ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Blood Stone Sword";
        lore.add(ChatColor.DARK_RED + "A sword infused with blood.");
        CustomModelData = 12;
        baseItem = Material.NETHERITE_SWORD;
    }
}
