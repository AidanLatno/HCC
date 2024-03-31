package me.testedpugtato.kingdomcraftplugin.util;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
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
        Collection<LivingEntity> list = new ArrayList<>();
        list.add(entity);
        DamageEntity(damage,list,cause);

    }
    public static void DamageEntity(float damage, Collection<LivingEntity> entities, LivingEntity cause)
    {
        for(LivingEntity entity : entities)
        {
            EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;

            if (cause instanceof Player) {
                PlayerMemory memory = PlayerUtility.getPlayerMemory((Player) cause);
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
            entity.setLastDamageCause(new EntityDamageEvent(cause, damageCause, damage));
        }
    }

    public static void ApplyPulse(Location loc, float force, float yForce, Collection<LivingEntity> entities, Collection<LivingEntity> excluded)
    {
        for (LivingEntity entity : entities) {
            if(excluded.contains(entity)) continue;

            Location playerToThrowLocation = entity.getEyeLocation();

            double x = playerToThrowLocation.getX() - loc.getX();
            double y = playerToThrowLocation.getY() - loc.getY();
            double z = playerToThrowLocation.getZ() - loc.getZ();

            Vector throwVector = new Vector(x, y, z);

            throwVector.normalize();
            throwVector.multiply(force);
            throwVector.setY(yForce);

            entity.setVelocity(throwVector);

        }
    }
    public static void ApplyPulse(Location loc, float force, float yForce, Vector boundingBox, Collection<LivingEntity> excluded)
    {
        Collection<LivingEntity> entities = loc.getNearbyLivingEntities(boundingBox.getX(),boundingBox.getY(),boundingBox.getZ());
        ApplyPulse(loc,force,yForce,entities,excluded);
    }
    public static void ApplyPulse(Location loc, float force, float yForce, Collection<LivingEntity> entities, LivingEntity excluded)
    {
        Collection<LivingEntity> list = new ArrayList<>();
        list.add(excluded);
        ApplyPulse(loc,force,yForce,entities,list);
    }
    public static void ApplyPulse(Location loc, float force, float yForce, Vector boundingBox, LivingEntity excluded)
    {
        Collection<LivingEntity> list = new ArrayList<>();
        list.add(excluded);
        ApplyPulse(loc,force,yForce,boundingBox,list);
    }
}
