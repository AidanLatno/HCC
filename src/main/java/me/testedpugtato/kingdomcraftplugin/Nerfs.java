package me.testedpugtato.kingdomcraftplugin;

import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.EventUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Nerfs implements Listener
{

    public Nerfs()
    {
        EventUtil.register(this);
    }
    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent event)
    {
        Material block = event.getBlock().getType();
        if(block == Material.ENDER_CHEST)
            event.setCancelled(true);
    }

    @EventHandler
    public void PlayerInventoryEvent(InventoryOpenEvent event)
    {
        Player player = (Player)event.getPlayer();
        if (event.getInventory().equals(player.getEnderChest()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void UsePearl(PlayerTeleportEvent event)
    {
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL))
        {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200*2,10),true);
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1,0),true);
            PlayerUtility.getPlayerMemory(event.getPlayer()).stun(40);
        }
    }



    @EventHandler
    public void OnUseTotem(EntityResurrectEvent event) {
        Entity entity = event.getEntity();

        if(!(entity instanceof Player)) { return; }

        if(((Player)entity).getItemInHand().equals(new ItemStack(Material.TOTEM_OF_UNDYING)) || ((Player)entity).getInventory().getItemInOffHand().equals(new ItemStack(Material.TOTEM_OF_UNDYING))) {
            Bukkit.getScheduler().runTaskLater(KingdomCraftPlugin.getInstance(), () -> {
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 10), true);
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 10), true);
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 10), true);
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 10), true);
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 10), true);
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 3), true);
                PlayerUtility.getPlayerMemory((Player)entity).stun(40);
            }, 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (Action.RIGHT_CLICK_BLOCK == event.getAction()) {
            if (Material.OBSIDIAN == event.getClickedBlock().getType()) {
                if (Material.END_CRYSTAL == event.getMaterial()) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
