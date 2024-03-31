package me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import me.testedpugtato.kingdomcraftplugin.util.lvl;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class WaterBasicProj extends PowerProjectile {
    public WaterBasicProj(LivingEntity caster, float speed, float radius) {
        super(caster, speed, radius);
    }

    public WaterBasicProj(LivingEntity caster, float speed) {
        super(caster, speed);
    }

    // Will be called when projectile collides with block
    @Override
    public boolean onCollide()
    {
        // Return true if you want the projectile to vanish after collision, otherwise return false;
        return true;
    }

    // Will be called every step
    // entities contains a list of entities that are NOT the caster and are within the radius of the projectile
    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        for(LivingEntity entity : entities)
        {
            CombatManager.DamageEntity(lvl.i(3,17,getPowerLevel()),entity,getCaster());
        }
    }

    // controllable determines whether the projectile can be controlled after it has been shot based on the player's direction
    // Will be called every step
    // Should contain visual effects as well as any and all logic as it travels
    @Override
    public void logic(boolean controllable)
    {
        ParticleMaker.createCircle(
                Particle.WATER_SPLASH,
                getLocation(),
                getRadius(),
                1,
                lvl.i(2,8,getPowerLevel()),
                0.2,
                0.2,
                0.2,
                0.01,
                1);
        ParticleMaker.createCircle(
                Particle.BUBBLE_POP,
                getLocation(),
                getRadius()/2,
                1,
                lvl.i(2,8,getPowerLevel()),
                0.2,
                0.2,
                0.2,
                0.01,
                1);
        ParticleMaker.createCircle(
                Particle.FALLING_WATER,
                getLocation(),
                getRadius(),
                1,
                lvl.i(2,8,getPowerLevel()),
                0.2,
                0.2,
                0.2,
                0.01,
                1);
    }
}
