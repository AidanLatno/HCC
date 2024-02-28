package me.testedpugtato.kingdomcraftplugin.util;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CombatManager
{
    public static void DamageNearby(Location loc, float x, float y, float z, float damage, LivingEntity cause)
    {
        for (Entity entity : loc.getNearbyEntities(x, y, z)) {
            if(entity.equals(cause)) continue;

            EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;

            if(cause instanceof Player) {
                PlayerMemory memory = PlayerUtility.getPlayerMemory((Player)cause);
                switch (memory.getPower().id) {
                    case "lightning":
                        damageCause = EntityDamageEvent.DamageCause.LIGHTNING;
                        break;
                    case "fire":
                        damageCause = EntityDamageEvent.DamageCause.FIRE;
                        break;


                }
            }
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                nearbyPlayer.damage(damage, cause);
                EntityDamageEvent event = new EntityDamageEvent(cause,damageCause,damage);
                nearbyPlayer.setLastDamageCause(event);
            } else if (entity instanceof Damageable) {
                Damageable damageableEntity = (Damageable) entity;
                damageableEntity.damage(damage, cause);
                EntityDamageEvent event = new EntityDamageEvent(cause,damageCause,damage);
                entity.setLastDamageCause(event);

            }
        }
    }
    public static void DamageEntity(float damage, LivingEntity entity, LivingEntity cause)
    {
        EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;

        if(cause instanceof Player) {
            PlayerMemory memory = PlayerUtility.getPlayerMemory((Player)cause);
            switch (memory.getPower().id) {
                case "lightning":
                    damageCause = EntityDamageEvent.DamageCause.LIGHTNING;
                    break;
                case "fire":
                    damageCause = EntityDamageEvent.DamageCause.FIRE;
                    break;
            }
        }
        entity.damage(damage, cause);
        entity.setLastDamageCause(new EntityDamageEvent(cause,damageCause,damage));

    }
    public static ArrayList<Entity> getEntitiesAroundPoint(Location location, double radius) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        World world = location.getWorld();

        // To find chunks we use chunk coordinates (not block coordinates!)
        int smallX = (int) Math.floor((location.getX() - radius) / 16.0D);
        int bigX = (int) Math.floor((location.getX() + radius) / 16.0D);
        int smallZ = (int) Math.floor((location.getZ() - radius) / 16.0D);
        int bigZ = (int) Math.floor((location.getZ() + radius) / 16.0D);

        for (int x = smallX; x <= bigX; x++) {
            for (int z = smallZ; z <= bigZ; z++) {
                if (world.isChunkLoaded(x, z)) {
                    entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities())); // Add all entities from this chunk to the list
                }
            }
        }
        return entities;
    }
}
