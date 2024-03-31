package me.testedpugtato.kingdomcraftplugin.projectiles.LightningProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;

public class LightningBasicProj extends PowerProjectile
{

    public LightningBasicProj(LivingEntity caster, float speed, float radius) {
        super(caster, speed, radius);
    }

    public LightningBasicProj(LivingEntity caster, float speed) {
        super(caster, speed);
    }

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
        for(LivingEntity e : entities)
        {
            ParticleMaker.createLine(
                    Particle.SCRAPE,
                    getLocation(),
                    e.getEyeLocation(),
                    0.5f,
                    5,
                    0,0,0
            );
            CombatManager.DamageEntity(lvl.i(1,10,getPowerLevel()),e,getCaster());
        }
    }

    // controllable determines whether the projectile can be controlled after it has been shot based on the player's direction
    // Will be called every step
    // Should contain visual effects as well as any and all logic as it travels
    @Override
    public void logic(boolean controllable)
    {
        Location loc = getLocation();
        if(controllable) loc.setDirection(getCaster().getEyeLocation().getDirection());

        ParticleMaker.createHelix(
                Particle.SCRAPE,
                loc,
                lvl.i(0.1,0.4,getPowerLevel()),
                (int)lvl.i(1,3,getPowerLevel()),
                lvl.i(2,4,getPowerLevel()),
                0.25,
                0.25,
                0.25,
                0.1,
                1
        );
        GeneralUtils.PlaySound(loc, Sound.BLOCK_BEEHIVE_WORK,100,0);
    }
}
