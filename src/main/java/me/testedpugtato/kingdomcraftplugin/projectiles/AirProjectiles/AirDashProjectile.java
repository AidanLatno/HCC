package me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Collection;

public class AirDashProjectile extends PowerProjectile
{
    public AirDashProjectile(LivingEntity caster, float speed, float radius) {
        super(caster, speed, radius);
    }

    public AirDashProjectile(LivingEntity caster, float speed) {
        super(caster, speed);
    }

    @Override
    public void logic(boolean controllable)
    {
        Location loc = getLocation();
        if(controllable) loc.setDirection(getCaster().getEyeLocation().getDirection());
        Vector dir = getCaster().getLocation().getDirection();
        dir.multiply(getSpeed());
        getCaster().setVelocity(dir);
        getLocation().getWorld().spawnParticle(Particle.CLOUD,getLocation(),10,0.1,0.1,0.1,0.3,null,true);

        setLocation(getCaster().getLocation());

        getLocation().getWorld().playSound(getLocation(), Sound.ENTITY_CREEPER_HURT,0.7f,5);
        getCaster().getWorld().playSound(getCaster().getEyeLocation(),Sound.ENTITY_CREEPER_HURT, SoundCategory.MASTER,0.7f,5);


    }

    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        for (LivingEntity entity : entities) {

            Location playerCenterLocation = getCaster().getEyeLocation();
            Location playerToThrowLocation = entity.getEyeLocation();

            double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
            double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
            double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

            Vector throwVector = new Vector(x, y, z);

            throwVector.normalize();
            throwVector.setY(0.5D);

            entity.setVelocity(throwVector);

            getLocation().getWorld().playSound(getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER,2,2);

        }

    }
    @Override
    public boolean onCollide()
    {
        return true;
    }
}
