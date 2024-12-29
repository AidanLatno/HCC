package me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Collection;

public class AirQuickAttackProj extends PowerProjectile
{
    public AirQuickAttackProj(LivingEntity caster, float speed, float radius) {
        super(caster, speed, radius);
    }

    public AirQuickAttackProj(LivingEntity caster, float speed) {
        super(caster, speed);
    }

    @Override
    public void logic(boolean controllable)
    {
        if(controllable)
            getLocation().setDirection(getCaster().getEyeLocation().getDirection());

        ParticleMaker.createSphere(
                Particle.SWEEP_ATTACK,
                getLocation(),
                getRadius(),
                1,
                lvl.i(0.2,0.4,getPowerLevel()),
                1,
                1,
                1,
                0
        );

        GeneralUtils.PlaySound(getLocation(),Sound.ENTITY_CREEPER_HURT,0.1f,5);
    }

    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        CombatManager.ApplyPulse(getLocation(),
                lvl.i(0.5,1,getPowerLevel()),
                lvl.i(0.1,0.5,getPowerLevel()),
                entities,
                getCaster());
        CombatManager.DamageEntity(lvl.i(2,7,getPowerLevel()),entities,getCaster());

    }

    @Override
    public boolean onCollide()
    {
        return true;
    }
}
