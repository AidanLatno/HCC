package me.testedpugtato.kingdomcraftplugin.data;

import me.testedpugtato.kingdomcraftplugin.powers.Power;
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

    public static <String, PlayerMemory> String findKeyByValue(PlayerMemory value) {
        for (Map.Entry<java.lang.String, me.testedpugtato.kingdomcraftplugin.data.PlayerMemory> entry : playerMemory.entrySet()) {
            if (value.equals(entry.getValue())) {
                return (String) entry.getKey(); // Return the first found key
            }
        }
        return null; // Not found
    }

    public static String getFolderPath(Player p)
    {
        return Bukkit.getPluginsFolder().getAbsolutePath() + "/KingdomCraft/player/" + p.getUniqueId();
    }

    static public Power GetPower(Player p)
    {
        return getPlayerMemory(p).getPower();
    }
}
