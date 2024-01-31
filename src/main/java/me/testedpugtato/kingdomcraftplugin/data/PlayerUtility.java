package me.testedpugtato.kingdomcraftplugin.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerUtility {
    private static Map<String,PlayerMemory> playerMemory = new HashMap<>();

    public static PlayerMemory getPlayerMemory(Player player)
    {
        if(!playerMemory.containsKey(player.getUniqueId().toString()))
        {
            PlayerMemory m = new PlayerMemory();
            playerMemory.put(player.getUniqueId().toString(),m);
            return m;
        }
        return playerMemory.get(player.getUniqueId().toString());
    }


    public static void setPlayerMemory(Player p, PlayerMemory memory)
    {
        if(memory == null) playerMemory.remove(p.getUniqueId().toString());
        else playerMemory.put(p.getUniqueId().toString(),memory);
    }

    public static String getFolderPath(Player p)
    {
        return Bukkit.getPluginsFolder().getAbsolutePath() + "/KingdomCraft/player/" + p.getUniqueId();
    }
}
