package me.testedpugtato.kingdomcraftplugin;

import me.testedpugtato.kingdomcraftplugin.commands.*;
import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class KingdomCraftPlugin extends JavaPlugin
{
    private static KingdomCraftPlugin instance;
    public KingdomCraftPlugin() { instance = this; }

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        Bukkit.getLogger().info("Starting up KingdomCraft Plugin");
        new InputListener();
        new GeneralEvents();
        new Nerfs();
        Database.initSwordMap();
        Database.initRecipeMap();

        // Commands
        new PowerLevel();
        new PlayerLevel();
        new MakeKing();
        new RemoveKing();
        new ChangeSlot();
        new YouTubeLink();
        new Stats();
        new SetPower();
        new Expand();
        new Craft();


        // AUTO SAVE EVERY 5 MIN
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                savePlayerData();
            }
        },1200,6000);
    }

    @Override
    public void onDisable() {
        savePlayerData();
    }

    public static void savePlayerData()
    {
        int success = 0,failed = 0;
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
            File f = new File(PlayerUtility.getFolderPath(player) + "/general.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(f);

            if(memory.getPower() == null)
            {
                failed++;
                continue;
            }

            config.set("stats.power", memory.getPower().id);
            config.set("stats.player_exp", memory.getPlayerEXP());
            config.set("stats.player_level", memory.getPlayerLevel());
            config.set("stats.power_exp", memory.getPowerEXP());
            config.set("stats.power_level", memory.getPowerLevel());
            config.set("stats.power_slot", memory.getPowerSlot());

            try {
                config.save(f);
                success++;
            } catch (IOException e) {
                e.printStackTrace();
                failed++;
            }
        }
        String s = new String("\n--------SAVE REPORT--------\n" +
                                     "- Successes: " + success + "\n" +
                                     "- Fails: " + failed + "\n" +
                                     "--------SAVE REPORT--------");
        Bukkit.getLogger().info(s);
    }

    public static KingdomCraftPlugin getInstance()
    {
        return instance;
    }
}
