package me.testedpugtato.kingdomcraftplugin.items.blood;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class AirBlood extends Blood
{
    public AirBlood()
    {
        name = ChatColor.WHITE + ChatColor.BOLD.toString() + "Air Blood";
        lore.add(ChatColor.DARK_RED + "The blood of a fallen air wielder.");
        CustomModelData = 6;
        baseItem = Material.MILK_BUCKET;
    }
}
