package me.testedpugtato.kingdomcraftplugin;

import jdk.internal.net.http.common.Pair;
import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import me.testedpugtato.kingdomcraftplugin.items.blood.LightningBlood;
import me.testedpugtato.kingdomcraftplugin.items.swords.*;

import me.testedpugtato.kingdomcraftplugin.util.Msg;
import me.testedpugtato.kingdomcraftplugin.util.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database
{
    public static Map<Integer, Sword> SwordMap = new HashMap<>();
    public static Map<Pair<Integer,Integer>, CustomItem> RecipeMap = new HashMap<>();
    public static ArrayList<Player> cancelFall = new ArrayList<>();
    private static Map<String, Boolean> basicCooldowns = new HashMap<>();
    private static Map<String, Boolean> arielCooldowns = new HashMap<>();
    private static Map<String, Boolean> DashCooldowns = new HashMap<>();
    private static Map<String, Boolean> slamCooldowns = new HashMap<>();
    private static Map<String, Boolean> quickCooldowns = new HashMap<>();
    private static Map<String, Boolean> chargeCooldowns = new HashMap<>();


    public static boolean isOnBasicCooldown(Player player)
    {
        if(!basicCooldowns.containsKey(player.getUniqueId().toString()))
        {
            boolean m = false;
            basicCooldowns.put(player.getUniqueId().toString(),m);
            return m;
        }
        return basicCooldowns.get(player.getUniqueId().toString());
    }

    public static boolean isOnArielCooldown(Player player)
    {
        if(!arielCooldowns.containsKey(player.getUniqueId().toString()))
        {
            boolean m = false;
            arielCooldowns.put(player.getUniqueId().toString(),m);
            return m;
        }
        return arielCooldowns.get(player.getUniqueId().toString());
    }

    public static boolean isOnDashCooldown(Player player)
    {
        if(!DashCooldowns.containsKey(player.getUniqueId().toString()))
        {
            boolean m = false;
            DashCooldowns.put(player.getUniqueId().toString(),m);
            return m;
        }
        return DashCooldowns.get(player.getUniqueId().toString());
    }

    public static boolean isOnSlamCooldown(Player player)
    {
        if(!slamCooldowns.containsKey(player.getUniqueId().toString()))
        {
            boolean m = false;
            slamCooldowns.put(player.getUniqueId().toString(),m);
            return m;
        }
        return slamCooldowns.get(player.getUniqueId().toString());
    }

    public static boolean isOnQuickCooldown(Player player)
    {
        if(!quickCooldowns.containsKey(player.getUniqueId().toString()))
        {
            boolean m = false;
            quickCooldowns.put(player.getUniqueId().toString(),m);
            return m;
        }
        return quickCooldowns.get(player.getUniqueId().toString());
    }

    public static boolean isOnChargeCooldown(Player player)
    {
        if(!chargeCooldowns.containsKey(player.getUniqueId().toString()))
        {
            boolean m = false;
            chargeCooldowns.put(player.getUniqueId().toString(),m);
            return m;
        }
        return chargeCooldowns.get(player.getUniqueId().toString());
    }

    public static void startBasicCooldown(Player p, double time)
    {
        basicCooldowns.put(p.getUniqueId().toString(),true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            public void run() {
                try {
                    Database.basicCooldowns.put(p.getUniqueId().toString(),false);
                    Msg.send(p,"Basic Attack is ready.");
                }
                catch (CommandException | IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        }, (int)(20L*time));
    }

    public static void startArielCooldown(Player p, double time)
    {
        arielCooldowns.put(p.getUniqueId().toString(),true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            public void run() {
                try {
                    Database.arielCooldowns.put(p.getUniqueId().toString(),false);
                    Msg.send(p,"Ariel is ready.");
                }
                catch (CommandException | IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        }, (int)(20L*time));
    }

    public static void startDashCooldown(Player p, double time)
    {
        DashCooldowns.put(p.getUniqueId().toString(),true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            public void run() {
                try {
                    Database.DashCooldowns.put(p.getUniqueId().toString(),false);
                    Msg.send(p,"Ariel Dash is ready.");
                }
                catch (CommandException | IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        }, (int)(20L*time));
    }

    public static void startSlamCooldown(Player p, double time)
    {
        slamCooldowns.put(p.getUniqueId().toString(),true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            public void run() {
                try {
                    Database.slamCooldowns.put(p.getUniqueId().toString(),false);
                    Msg.send(p,"Slam Attack is ready.");
                }
                catch (CommandException | IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        }, (int)(20L*time));
    }

    public static void startQuickCooldown(Player p, double time)
    {
        quickCooldowns.put(p.getUniqueId().toString(),true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            public void run() {
                try {
                    Database.quickCooldowns.put(p.getUniqueId().toString(),false);
                    Msg.send(p,"Quick Attack is ready.");
                }
                catch (CommandException | IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        }, (int)(20L*time));
    }

    public static void startChargeCooldown(Player p, double time)
    {
        chargeCooldowns.put(p.getUniqueId().toString(),true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            public void run() {
                try {
                    Database.chargeCooldowns.put(p.getUniqueId().toString(),false);
                    Msg.send(p,"Charge Attack is ready.");
                }
                catch (CommandException | IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        }, (int)(20L*time));
    }

    public static void removeCancelFall(Player player, double time)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            public void run() {
                try {
                    if(!player.isOnGround()){
                        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,20);
                        return;
                    }
                    Database.cancelFall.remove(player);
                }
                catch (CommandException | IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        }, (int)(20L*time));
    }

    public static void initSwordMap()
    {
        SwordMap.put(0, new Sword());
        SwordMap.put(1, new FireSword());
        SwordMap.put(2, new EarthSword());
        SwordMap.put(3, new AirSword());
        SwordMap.put(4, new LightningSword());
        SwordMap.put(5, new WaterSword());
    }

    public static void initRecipeMap()
    {
        RecipeMap.put(Pair.pair(12,6),new AirSword());
        RecipeMap.put(Pair.pair(12,7),new LightningSword());
        RecipeMap.put(Pair.pair(12,8),new EarthSword());
        RecipeMap.put(Pair.pair(12,9),new WaterSword());
        RecipeMap.put(Pair.pair(12,10),new FireSword());
    }

}
