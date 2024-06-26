package me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles;

import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FireBasicAttackProj extends PowerProjectile
{

    private Particle particle;

    public FireBasicAttackProj(LivingEntity caster, float speed, Particle particle, float radius) {
        super(caster, speed, radius);
        this.particle = particle;
    }

    public FireBasicAttackProj(LivingEntity caster, float speed, Particle particle) {
        super(caster, speed);
        this.particle = particle;
    }

    @Override
    public void logic(boolean controllable)
    {
        Location loc = getLocation();
        if(controllable) loc.setDirection(getCaster().getEyeLocation().getDirection());

        GeneralUtils.PlaySound(getLocation(),Sound.ENTITY_BLAZE_SHOOT);

        ParticleMaker.createCircle(
                particle,
                loc,
                getRadius(),
                1,
                lvl.i(2,8,getPowerLevel()),
                0.2,
                0.2,
                0.2,
                0.01,
                1);

        CombatManager.DamageNearby(getLocation(),getRadius(),getRadius(),getRadius(),(float)lvl.i(3,7,getPowerLevel()),getCaster());
    }

    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        int powerLevel = PlayerUtility.getPlayerMemory((Player)getCaster()).getPowerLevel();

        for(LivingEntity e : entities)
        {
            e.setFireTicks((int)lvl.i(20,50,powerLevel));
        }
    }

    @Override
    public boolean onCollide()
    {
        getCaster().getWorld().spawnParticle(particle,getLocation(),10,.3,.3,.3,.2,null,true);
        return true;
    }
}
