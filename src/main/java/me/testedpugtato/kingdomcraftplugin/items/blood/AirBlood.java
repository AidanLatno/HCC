package me.testedpugtato.kingdomcraftplugin.items.blood;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class AirBlood extends CustomItem
{
    public AirBlood()
    {
        name = ChatColor.WHITE + ChatColor.BOLD.toString() + "Air Blood";
        lore.add(ChatColor.DARK_RED + "The blood of a fallen air wielder.");
        CustomModelData = -1;
        baseItem = Material.MILK_BUCKET;
    }
}
