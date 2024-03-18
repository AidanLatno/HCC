package me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class SamuraiQuickProj extends PowerProjectile {

    public SamuraiQuickProj(LivingEntity caster, float speed, float radius) {
        super(caster, speed, radius);
    }

    public SamuraiQuickProj(LivingEntity caster, float speed) {
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

    }

    // controllable determines whether the projectile can be controlled after it has been shot based on the player's direction
    // Will be called every step
    // Should contain visual effects as well as any and all logic as it travels
    @Override
    public void logic(boolean controllable)
    {
        getLocation().getWorld().spawnParticle(Particle.SWEEP_ATTACK,getLocation(),1,0,0,0,0,null,true);
        getLocation().getWorld().playSound(getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER,2,2);


    }
}
