package me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Collection;

public class AirQuickAttackProj extends PowerProjectile
{
    public AirQuickAttackProj(LivingEntity caster, float speed, float radius) {
        super(caster, speed, radius);
    }

    public AirQuickAttackProj(LivingEntity caster, float speed) {
        super(caster, speed);
    }

    @Override
    public void logic(boolean controllable)
    {
        if(controllable)
            getLocation().setDirection(getCaster().getEyeLocation().getDirection());

        ParticleMaker.createSphere(
                Particle.SWEEP_ATTACK,
                getLocation(),
                getRadius(),
                1,
                MathUtils.levelInter(0.2,0.4,getPowerLevel()),
                1,
                1,
                1,
                0
        );

        getLocation().getWorld().playSound(getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER,2,2);
        getCaster().getWorld().playSound(getCaster().getEyeLocation().add(getCaster().getEyeLocation().getDirection()),Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER,0.3f,2);
        getLocation().getWorld().playSound(getLocation(), Sound.ENTITY_CREEPER_HURT,0.1f,5);
        getCaster().getWorld().playSound(getCaster().getEyeLocation().add(getCaster().getEyeLocation().getDirection()),Sound.ENTITY_CREEPER_HURT, SoundCategory.MASTER,0.1f,5);
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
            throwVector.multiply(MathUtils.levelInter(1,2,getPowerLevel()));
            throwVector.setY(MathUtils.levelInter(0.5,1.3,getPowerLevel()));

            entity.setVelocity(throwVector);
            CombatManager.DamageEntity(MathUtils.levelInter(1,3,getPowerLevel()),entity,getCaster());
        }

    }

    @Override
    public boolean onCollide()
    {
        return true;
    }
}
