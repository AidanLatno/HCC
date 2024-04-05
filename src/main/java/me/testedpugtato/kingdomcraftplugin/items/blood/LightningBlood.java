package me.testedpugtato.kingdomcraftplugin.items.blood;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class LightningBlood extends CustomItem
{
    public LightningBlood()
    {
        name = ChatColor.AQUA + ChatColor.BOLD.toString() + "Lightning Blood";
        lore.add(ChatColor.DARK_RED + "The blood of a fallen lightning wielder.");
        CustomModelData = -1;
        baseItem = Material.MILK_BUCKET;
    }
}
