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

public class AirBasicAttackProj extends PowerProjectile {
    public AirBasicAttackProj(LivingEntity caster, float speed, float radius) {
        super(caster, speed, radius);
    }

    public AirBasicAttackProj(LivingEntity caster, float speed) {
        super(caster, speed);
    }

    @Override
    public void logic(boolean controllable)
    {
        Location loc = getLocation();
        if(controllable) loc.setDirection(getCaster().getEyeLocation().getDirection());

        ParticleMaker.createHelix(
                Particle.CLOUD,
                loc,
                MathUtils.levelInter(0.2,1,getPowerLevel()),
                1,
                MathUtils.levelInter(2,4,getPowerLevel()),
                .3,
                .3,
                .3,
                0,
                1);

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
            throwVector.multiply(MathUtils.levelInter(1.3,3,getPowerLevel()));
            throwVector.setY(MathUtils.levelInter(0.8,2,getPowerLevel()));

            entity.setVelocity(throwVector);

            CombatManager.DamageEntity(MathUtils.levelInter(1,6,getPowerLevel()),entity,getCaster());
        }

    }

    @Override
    public boolean onCollide()
    {
        return true;
    }
}
