package me.testedpugtato.kingdomcraftplugin.items.blood;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class FireBlood extends CustomItem
{
    public FireBlood()
    {
        name = ChatColor.GOLD + ChatColor.BOLD.toString() + "Fire Blood";
        lore.add(ChatColor.DARK_RED + "The blood of a fallen fire wielder.");
        CustomModelData = -1;
        baseItem = Material.MILK_BUCKET;
    }
}
