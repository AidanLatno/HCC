package me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles;

import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.GeneralUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class SLightningChargeProj extends SamuraiChargeProj{
    public SLightningChargeProj(LivingEntity caster, float speed, float radius, float swordDamage) {
        super(caster, speed, radius, swordDamage);
    }

    public SLightningChargeProj(LivingEntity caster, float speed, float swordDamage) {
        super(caster, speed, swordDamage);
    }

    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        super.entityInteract(entities);

        for(LivingEntity e : entities)
        {
            if(e instanceof Player)
            {
                PlayerUtility.getPlayerMemory((Player)e).stun(200);
            }
        }
    }

    @Override
    public void logic(boolean controllable)
    {
        super.logic(controllable);

        ParticleMaker.SpawnParticle(getLocation(), Particle.SCRAPE,1);
        GeneralUtils.PlaySound(getLocation(), Sound.BLOCK_BEEHIVE_WORK,100,0);

    }
}
