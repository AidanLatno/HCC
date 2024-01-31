package me.testedpugtato.kingdomcraftplugin;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.EventUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class GeneralEvents implements Listener {
    public GeneralEvents() { EventUtil.register(this); }

    @EventHandler
    private void OnJoin(PlayerJoinEvent event)
    {
        PlayerMemory memory = new PlayerMemory();
        File f = new File(PlayerUtility.getFolderPath(event.getPlayer()) + "/general.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

        if(f.exists())
        {
            memory.setPower(config.getString("stats.power"));
            memory.setPlayerEXP(config.getInt("stats.player_exp"));
            memory.setPlayerLevel(config.getInt("stats.player_level"));
            memory.setPowerEXP(config.getInt("stats.power_exp"));
            memory.setPowerLevel(config.getInt("stats.power_level"));
            memory.setPowerSlot(config.getInt("stats.power_slot"));
            memory.setKing(config.getBoolean("stats.is_king"));
            PlayerUtility.setPlayerMemory(event.getPlayer(), memory);
        }
        else
        {
            memory.setPower("no power");
            memory.setPowerLevel(1);
            memory.setPlayerLevel(1);
            memory.setPowerEXP(0);
            memory.setPlayerEXP(0);
            memory.setPowerSlot(8);

            File theDir = new File(PlayerUtility.getFolderPath(event.getPlayer()));
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            f.getParentFile().mkdirs();
//            JavaPlugin.getPlugin(KingdomCraftPlugin.class).saveResource("asdad", false);

            config.set("stats.power", memory.getPower());
            config.set("stats.player_exp", memory.getPlayerEXP());
            config.set("stats.player_level", memory.getPlayerLevel());
            config.set("stats.power_exp", memory.getPowerEXP());
            config.set("stats.power_level", memory.getPowerLevel());
            config.set("stats.power_slot", memory.getPowerSlot());
            config.set("stats.is_king",memory.isKing());

            // save file
            try {
                config.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        PlayerMemory memory = PlayerUtility.getPlayerMemory(event.getPlayer());
        File f = new File(PlayerUtility.getFolderPath(event.getPlayer()) + "/general.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("stats.power", memory.getPower().id);
        config.set("stats.player_exp", memory.getPlayerEXP());
        config.set("stats.player_level", memory.getPlayerLevel());
        config.set("stats.power_exp", memory.getPowerEXP());
        config.set("stats.power_level", memory.getPowerLevel());
        config.set("stats.power_slot", memory.getPowerSlot());
        config.set("stats.is_king",memory.isKing());

        try{config.save(f);} catch(IOException e) {e.printStackTrace();}
        PlayerUtility.setPlayerMemory(event.getPlayer(), null);

    }

    @EventHandler
    private void onPlayerFall(EntityDamageEvent e)
    {
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            if(Database.cancelFall.contains(player))
            {
                player.setFallDistance(0);
                Database.cancelFall.remove(player);
                e.setCancelled(true);
                return;
            }
            else if(PlayerUtility.getPlayerMemory(player).getPower().equals("air") || PlayerUtility.getPlayerMemory(player).getPower().equals("earth"))
            {
                player.setFallDistance(0);
                e.setCancelled(true);
                return;
            }
        }
    }

}
