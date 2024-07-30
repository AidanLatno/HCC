package me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles;

import me.testedpugtato.kingdomcraftplugin.util.GeneralUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class SWaterChargeProj extends SamuraiChargeProj{
    public SWaterChargeProj(LivingEntity caster, float speed, float radius, float swordDamage) {
        super(caster, speed, radius, swordDamage);
    }

    public SWaterChargeProj(LivingEntity caster, float speed, float swordDamage) {
        super(caster, speed, swordDamage);
    }



    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        super.entityInteract(entities);

        for(LivingEntity e : entities)
        {
            e.setVelocity(e.getVelocity().add(getLocation().getDirection()));
        }
    }

    @Override
    public void logic(boolean controllable)
    {
        super.logic(controllable);

        ParticleMaker.SpawnParticle(getLocation(), Particle.WATER_SPLASH,1);
        GeneralUtils.PlaySound(getLocation(), Sound.ENTITY_DOLPHIN_SPLASH,.4f,2);

    }
}
