package me.testedpugtato.kingdomcraftplugin.util;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

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
}
