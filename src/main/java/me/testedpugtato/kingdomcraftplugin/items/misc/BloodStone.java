package me.testedpugtato.kingdomcraftplugin.items.misc;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class BloodStone extends CustomItem {
    public BloodStone()
    {
        name = ChatColor.RED + ChatColor.BOLD.toString() + "Blood Stone";
        lore.add(ChatColor.DARK_RED + "Merge with a sword to allow the infusion of blood.");
        CustomModelData = -1;
        baseItem = Material.ENDER_CHEST;
    }
}
