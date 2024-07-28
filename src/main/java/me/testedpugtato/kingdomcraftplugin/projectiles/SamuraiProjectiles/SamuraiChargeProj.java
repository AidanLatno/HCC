package me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles;

import me.testedpugtato.kingdomcraftplugin.items.swords.Sword;
import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class SamuraiChargeProj extends PowerProjectile {
    private final float swordDamage;
    private final Sword sword;

    public SamuraiChargeProj(LivingEntity caster, float speed, float radius, float swordDamage, Sword sword) {
        super(caster, speed, radius);
        this.swordDamage = swordDamage;
        this.sword = sword;
    }

    public SamuraiChargeProj(LivingEntity caster, float speed, float swordDamage, Sword sword) {
        super(caster, speed);
        this.swordDamage = swordDamage;
        this.sword = sword;
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
        CombatManager.DamageEntity(swordDamage*lvl.i(2,4,getPowerLevel()),entities,getCaster());
        for(LivingEntity e : entities)
        {
            e.setVelocity(e.getVelocity().add(getLocation().getDirection()));
        }
    }

    // controllable determines whether the projectile can be controlled after it has been shot based on the player's direction
    // Will be called every step
    // Should contain visual effects as well as any and all logic as it travels
    @Override
    public void logic(boolean controllable)
    {
        ParticleMaker.SpawnParticle(getLocation(), Particle.SWEEP_ATTACK,1);
        GeneralUtils.PlaySound(getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP,2,2);

    }
}
