package me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class SamuraiQuickProj extends PowerProjectile {
    private float swordDamage;

    public SamuraiQuickProj(LivingEntity caster, float speed, float radius, float swordDamage) {
        super(caster, speed, radius);
        this.swordDamage = swordDamage;
    }

    public SamuraiQuickProj(LivingEntity caster, float speed, float swordDamage) {
        super(caster, speed);
        this.swordDamage = swordDamage;
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
            CombatManager.DamageEntity(swordDamage*MathUtils.levelInter(0.5,1.3,getPowerLevel()),entity,getCaster());
        }
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
