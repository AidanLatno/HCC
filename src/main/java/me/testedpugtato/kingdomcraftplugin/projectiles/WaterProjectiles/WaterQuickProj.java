package me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class WaterQuickProj extends PowerProjectile {
    public WaterQuickProj(LivingEntity caster, float speed, float radius) {
        super(caster, speed, radius);
    }

    public WaterQuickProj(LivingEntity caster, float speed) {
        super(caster, speed);
    }

    // Will be called when projectile collides with block
    @Override
    public boolean onCollide()
    {
        // Return true if you want the projectile to vanish after collision, otherwise return false;
        return false;
    }

    // Will be called every step
    // entities contains a list of entities that are NOT the caster and are within the radius of the projectile
    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {

    }

    // controllable determines whether the projectile can be controlled after it has been shot based on the player's direction
    // Will be called every step
    // Should contain visual effects as well as any and all logic as it travels
    @Override
    public void logic(boolean controllable)
    {
        getLocation().getWorld().spawnParticle(Particle.WATER_SPLASH,getLocation(),30,.2,.2,.2,.01);
        getLocation().getWorld().spawnParticle(Particle.BUBBLE_POP,getLocation(),30,.2,.2,.2,.01);
    }
}
