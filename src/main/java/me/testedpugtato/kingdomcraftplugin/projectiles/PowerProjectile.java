package me.testedpugtato.kingdomcraftplugin.projectiles;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.powers.Power;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;

public class PowerProjectile
{


    private LivingEntity caster;
    private Location location;
    private Location prevLocation;
    private float speed;
    private float radius;
    private int powerLevel;
    private boolean destroy = false;

    public PowerProjectile(LivingEntity caster, float speed, float radius) {
        this.caster = caster;
        this.speed = speed;
        this.radius = radius;
        this.location = caster.getEyeLocation();

        if(caster instanceof Player)
            powerLevel = PlayerUtility.getPlayerMemory((Player)caster).getPowerLevel();
        else powerLevel = 30;
    }
    public PowerProjectile(LivingEntity caster , float speed) {
        this.caster = caster;
        this.speed = speed;
        this.radius = 1;
        this.location = caster.getEyeLocation();

        if(caster instanceof Player)
            powerLevel = PlayerUtility.getPlayerMemory((Player)caster).getPowerLevel();
        else powerLevel = 30;
    }

    public void move(boolean controllable)
    {
        Vector vec;
        if(controllable)
            vec = caster.getLocation().getDirection().multiply(speed);
        else
            vec = getLocation().getDirection().multiply(speed);
        prevLocation = location;
        location.add(vec);
    }

    public boolean isColliding()
    {
        Location current = prevLocation;
        Vector vec = location.toVector();

        Vector prevVec = prevLocation.toVector();
        Location mid = current.add(vec.subtract(prevVec));
        int i = 0;
        for(Material m : Power.ignoreList)
        {
            if(type(location).equals(m) && type(mid).equals(m))
                return false;
        }
        return true;

    }
    Material type(Location location)
    {
        return caster.getWorld().getBlockAt(location).getType();
    }
    // TO BE OVERRIDDEN
    public void logic(boolean controllable)
    {
        Bukkit.getLogger().info("[WARNING] PROJECTILE CREATED WITH NO LOGIC OVERRIDE");
    }
    // TO BE OVERRIDDEN
    public void entityInteract(Collection<LivingEntity> entities)
    {
        Bukkit.getLogger().info("[WARNING] PROJECTILE CREATED WITH NO INTERACT OVERRIDE");
    }
    // TO BE OVERRIDDEN
    // Return true to terminate loop
    public boolean onCollide()
    {
        Bukkit.getLogger().info("[WARNING] PROJECTILE CREATED WITH NO COLLISION OVERRIDE");
        return false;
    }

    public void moveSelf(float time, boolean controllable, int ticksPerMovement)
    {
        prevLocation = location;

        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable(){
            int ticks = 0;

            @Override
            public void run() {
                ticks += ticksPerMovement;
                if(ticks >= time*20) return;

                if(isColliding())
                    if(onCollide()) return;

                if(destroy) return;

                move(controllable);
                logic(controllable);

                Collection<LivingEntity> entities = location.getNearbyLivingEntities(radius,radius,radius);
                entities.remove(caster);
                entityInteract(entities);

                Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), this, ticksPerMovement);

            }
        },ticksPerMovement);
    }

    public void moveSelf(float time, boolean controllable) { moveSelf(time,controllable,1); }

    public Location getLocation() { return location; }
    public float getSpeed() { return speed; }
    public float getRadius() { return radius; }
    public void setLocation(Location location) { this.location = location;}
    public void setSpeed(float speed) { this.speed = speed; }
    public void setRadius(float radius) { this.radius = radius; }
    public LivingEntity getCaster() { return caster; }
    public void setCaster(LivingEntity caster) { this.caster = caster; }
    public int getPowerLevel() { return powerLevel; }
    public void setPowerLevel(int powerLevel) { this.powerLevel = powerLevel; }
    public void destroySelf() { destroy = true; }
}
