package me.testedpugtato.kingdomcraftplugin.util;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class EventUtil
{
    public static void register(Listener listener)
    {
        Bukkit.getServer().getPluginManager().registerEvents(listener, KingdomCraftPlugin.getInstance());
    }
}
