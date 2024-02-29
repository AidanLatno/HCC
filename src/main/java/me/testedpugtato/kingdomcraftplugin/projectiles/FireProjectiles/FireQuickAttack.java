package me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class FireQuickAttack extends PowerProjectile
{
    Particle particle;

    public FireQuickAttack(LivingEntity caster, float speed, Particle particle, float radius) {
        super(caster, speed, radius);
        this.particle = particle;
    }

    public FireQuickAttack(LivingEntity caster, float speed, Particle particle) {
        super(caster, speed);
        this.particle = particle;
    }

    @Override
    public void logic(boolean controllable)
    {
        Location loc = getLocation();
        if(controllable) loc.setDirection(getCaster().getEyeLocation().getDirection());

        getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_FIRE_AMBIENT,.5f,1);

        ParticleMaker.createHelix(
            particle,
            loc,
            MathUtils.levelInter(0.1,0.4,getPowerLevel()),
            (int)MathUtils.levelInter(1,3,getPowerLevel()),
            MathUtils.levelInter(2,4,getPowerLevel()),
            0.03,
            0.03,
            0.03,
            0,
            1);

    }

    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        for(LivingEntity e : entities)
        {
            e.setFireTicks(20*2);
            e.damage((float)MathUtils.levelInter(1,4,getPowerLevel()),getCaster());
        }
    }

    @Override
    public boolean onCollide()
    {
        getCaster().getWorld().spawnParticle(particle,getLocation(),10,.3,.3,.3,.2,null,true);
        return true;
    }
}
