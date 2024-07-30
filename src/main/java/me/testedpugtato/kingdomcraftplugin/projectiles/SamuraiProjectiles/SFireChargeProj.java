package me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles;

import me.testedpugtato.kingdomcraftplugin.util.GeneralUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class SFireChargeProj extends SamuraiChargeProj{
    public SFireChargeProj(LivingEntity caster, float speed, float radius, float swordDamage) {
        super(caster, speed, radius, swordDamage);
    }

    public SFireChargeProj(LivingEntity caster, float speed, float swordDamage) {
        super(caster, speed, swordDamage);
    }



    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        super.entityInteract(entities);

        for(LivingEntity e : entities)
        {
            e.setFireTicks(100);
        }
    }

    @Override
    public void logic(boolean controllable)
    {
        super.logic(controllable);

        ParticleMaker.SpawnParticle(getLocation(), Particle.FLAME,1);
        GeneralUtils.PlaySound(getLocation(), Sound.ENTITY_BLAZE_SHOOT,100,0);

    }
}
