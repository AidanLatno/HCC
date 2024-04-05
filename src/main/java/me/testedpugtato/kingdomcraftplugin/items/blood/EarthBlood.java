package me.testedpugtato.kingdomcraftplugin.items.blood;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class EarthBlood extends CustomItem
{
    public EarthBlood()
    {
        name = ChatColor.GREEN + ChatColor.BOLD.toString() + "Earth Blood";
        lore.add(ChatColor.DARK_RED + "The blood of a fallen earth wielder.");
        CustomModelData = -1;
        baseItem = Material.MILK_BUCKET;
    }
}
