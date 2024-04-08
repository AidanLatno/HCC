package me.testedpugtato.kingdomcraftplugin.items.blood;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class FireBlood extends Blood
{
    public FireBlood()
    {
        name = ChatColor.GOLD + ChatColor.BOLD.toString() + "Fire Blood";
        lore.add(ChatColor.DARK_RED + "The blood of a fallen fire wielder.");
        CustomModelData = 10;
        baseItem = Material.MILK_BUCKET;
    }
}
