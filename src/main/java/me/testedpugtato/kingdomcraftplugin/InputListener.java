package me.testedpugtato.kingdomcraftplugin;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.powers.*;
import me.testedpugtato.kingdomcraftplugin.util.EventUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerToggleSneakEvent;



public class InputListener implements Listener
{
    public InputListener()
    {
        EventUtil.register(this);
    }
    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getHeldItemSlot() != PlayerUtility.getPlayerMemory(player).getPowerSlot())
            return;
        if(player.isSneaking() && !player.isOnGround() && player.getLocation().getPitch() >= 50 && !Database.isOnSlamCooldown(player)) { // Slam attack
            PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
            memory.shiftCount++;
            PlayerUtility.setPlayerMemory(player,memory);

            if(memory.shiftCount >= 3) // start launch downwards
            {
                if(!Database.cancelFall.contains(player))
                    Database.cancelFall.add(player);

                // Schedule Fall DMG delay
                Database.removeCancelFall(player,10);
                int powerLevel = PlayerUtility.getPlayerMemory(player).getPowerLevel();

                Power power = GetPower(player);
                Database.startSlamCooldown(player,power.getSlamCooldown());
                power.useGroundSlam(player,powerLevel);

                Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
                    double charge = 0;
                    @Override
                    public void run() {
                        charge += 0.05;
                        power.groundSlamFalling(player,powerLevel,charge);

                        if(charge >= 12) return;

                        if(player.isOnGround()) {
                            power.useGroundSlamLanding(player, powerLevel,charge);
                            PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
                            memory.shiftCount = 0;
                            PlayerUtility.setPlayerMemory(player, memory);
                        }
                        else {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
                        }
                    }
                },1);

            }
            else{
                Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
                        memory.shiftCount = 0;
                        PlayerUtility.setPlayerMemory(player,memory);
                    }
                }, 20);
            }
        }
    }
    @EventHandler
    public void onCLick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();

        int powerLevel = PlayerUtility.getPlayerMemory(player).getPowerLevel();

        if (player.getInventory().getHeldItemSlot() != PlayerUtility.getPlayerMemory(player).getPowerSlot())
            return;

        Power abilities = GetPower(player);

        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && player.isSneaking() && player.isOnGround()) // Charge
        {
            if(Database.isOnChargeCooldown(player))
                return;

            Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable(){
                double charge = 0;
                @Override
                public void run() {
                    charge += 0.1;

                    if(player.getInventory().getHeldItemSlot() != PlayerUtility.getPlayerMemory(player).getPowerSlot())
                        return;

                    if(!Database.isOnChargeCooldown(player))
                        return;

                    if(player.isSneaking())
                    {
                        abilities.chargeChargedAttack(player, powerLevel,charge);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,2);
                    }
                    else {
                        abilities.useChargedAttack(player,powerLevel,charge);
                        return;
                    }
                }
            },2);
            Database.startChargeCooldown(player, abilities.getChargedCooldown());
        }
        else if(event.getAction().equals(Action.LEFT_CLICK_AIR) && player.isSneaking() && !player.isOnGround() && !Database.isOnDashCooldown(player)) // Ariel Dash
        {
            Database.removeCancelFall(player,10);
            Database.startDashCooldown(player,abilities.getDashCooldown());
            Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {

                @Override
                public void run() {
                    abilities.useArielDash(player,powerLevel);
                    if(!Database.cancelFall.contains(player))
                        Database.cancelFall.add(player);
                }
            });
        }
        else if (event.getAction().equals(Action.LEFT_CLICK_AIR) && !Database.isOnQuickCooldown(player) && player.isSprinting()) // Quick Attack
        {
            Database.startQuickCooldown(player,abilities.getQuickCooldown());
            Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {

                @Override
                public void run() {
                    abilities.useQuickAttack(player, powerLevel);
                }
            });
        }
        else if(event.getAction().equals(Action.LEFT_CLICK_AIR) && !Database.isOnBasicCooldown(player)) // Basic attack
        {
            Database.startBasicCooldown(player, abilities.getBasicCooldown());
            Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
                @Override
                public void run() {
                    abilities.useBasicAttack(player, powerLevel);
                }
            });
        }
        else if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && player.isSneaking() && !Database.isOnArielCooldown(player)) // Ariel
        {
            Database.startArielCooldown(player,abilities.getArielCooldown());
            Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
                @Override
                public void run() {

                    abilities.useAriel(player,powerLevel);

                    if(!Database.cancelFall.contains(player))
                        Database.cancelFall.add(player);

                    Database.removeCancelFall(player,10);

                }
            });
        }
    }

    private Power GetPower(Player p)
    {
        return PlayerUtility.getPlayerMemory(p).getPower();
    }
}
