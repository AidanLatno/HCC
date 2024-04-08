package me.testedpugtato.kingdomcraftplugin.items.blood;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class WaterBlood extends Blood
{
    public WaterBlood()
    {
        name = ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Water Blood";
        lore.add(ChatColor.DARK_RED + "The blood of a fallen Water wielder.");
        CustomModelData = 9;
        baseItem = Material.MILK_BUCKET;
    }
}
