package me.testedpugtato.kingdomcraftplugin;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.powers.Air;
import me.testedpugtato.kingdomcraftplugin.powers.Earth;
import me.testedpugtato.kingdomcraftplugin.powers.Lightning;
import me.testedpugtato.kingdomcraftplugin.powers.Power;
import me.testedpugtato.kingdomcraftplugin.util.EventUtil;
import net.kyori.adventure.text.Component;
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
import java.time.LocalDateTime;

public class GeneralEvents implements Listener {
    public GeneralEvents() { EventUtil.register(this); }

    @EventHandler
    private void OnJoin(PlayerJoinEvent event)
    {
        PlayerMemory memory = new PlayerMemory();
        File f = new File(PlayerUtility.getFolderPath(event.getPlayer()) + "/general.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

        // If a file exists for the user
        if(f.exists())
        {
            // Load file into memory
            memory.setPower(config.getString("stats.power"));
            memory.setPlayerEXP(config.getInt("stats.player_exp"));
            memory.setPlayerLevel(config.getInt("stats.player_level"));
            memory.setPowerEXP(config.getInt("stats.power_exp"));
            memory.setPowerLevel(config.getInt("stats.power_level"));
            memory.setPowerSlot(config.getInt("stats.power_slot"));
            memory.setKing(config.getBoolean("stats.is_king"));
            memory.setDead(config.getBoolean("stats.is_dead"));
            memory.setUnBanTime(LocalDateTime.parse(config.getString("stats.unban_time")));
            memory.setSpecialist(config.getString("stats.specialist"));
            PlayerUtility.setPlayerMemory(event.getPlayer(), memory);
        }
        // if the file does not exist
        else
        {
            // Create default values for new player memory
            memory.setPowerLevel(1);
            memory.setPlayerLevel(1);
            memory.setPowerEXP(0);
            memory.setPlayerEXP(0);
            memory.setPowerSlot(8);
            memory.setPower(new Power());
            memory.setDead(false);
            memory.setUnBanTime(LocalDateTime.now().plusHours(-1));
            memory.setSpecialist(new Power());

            PlayerUtility.setPlayerMemory(event.getPlayer(), memory);


            // Create file for new player
            File theDir = new File(PlayerUtility.getFolderPath(event.getPlayer()));
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            f.getParentFile().mkdirs();

            // Set newly created file's values
            config.set("stats.name", event.getPlayer().getName());
            config.set("stats.power", memory.getPower().id);
            config.set("stats.player_exp", memory.getPlayerEXP());
            config.set("stats.player_level", memory.getPlayerLevel());
            config.set("stats.power_exp", memory.getPowerEXP());
            config.set("stats.power_level", memory.getPowerLevel());
            config.set("stats.power_slot", memory.getPowerSlot());
            config.set("stats.is_king",memory.isKing());
            config.set("stats.is_dead", memory.isDead());
            config.set("stats.unban_time",memory.getUnBanTime().toString());
            config.set("stats.specialist",memory.getSpecialist().id);


            // save file
            try {
                config.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // check to see if user is death banned
        if(memory.getUnBanTime().isAfter(LocalDateTime.now()))
        {
            event.getPlayer().kick(Component.text("You are still death banned. You will be able to rejoin: " + memory.getUnBanTime().toString()));
        }
    }

     @EventHandler
    private void onQuit(PlayerQuitEvent event)
    {
        // Save player's data to their file
        PlayerMemory memory = PlayerUtility.getPlayerMemory(event.getPlayer());
        File f = new File(PlayerUtility.getFolderPath(event.getPlayer()) + "/general.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("stats.name", event.getPlayer().getName());
        config.set("stats.power", memory.getPower().id);
        config.set("stats.player_exp", memory.getPlayerEXP());
        config.set("stats.player_level", memory.getPlayerLevel());
        config.set("stats.power_exp", memory.getPowerEXP());
        config.set("stats.power_level", memory.getPowerLevel());
        config.set("stats.power_slot", memory.getPowerSlot());
        config.set("stats.is_king",memory.isKing());
        config.set("stats.isDead", memory.isDead());
        config.set("stats.unban_time",memory.getUnBanTime().toString());
        config.set("stats.specialist",memory.getSpecialist().id);

        try{config.save(f);} catch(IOException e) {e.printStackTrace();}
        PlayerUtility.setPlayerMemory(event.getPlayer(), null);

    }

    @EventHandler
    private void onPlayerFall(EntityDamageEvent e)
    {
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            Power power = PlayerUtility.getPlayerMemory(player).getPower();

            if(Database.cancelFall.contains(player))
            {
                player.setFallDistance(0);
                Database.cancelFall.remove(player);
                e.setCancelled(true);
                return;
            }
            else if(power instanceof Air)
            {
                player.setFallDistance(0);
                e.setCancelled(true);
                return;
            }
        }
    }

}
