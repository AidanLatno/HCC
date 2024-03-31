package me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FireChargedAttack extends PowerProjectile
{
    private double charge;
    private Particle particle;

    public FireChargedAttack(Player player, float speed, double charge, Particle particle, float radius)
    {
        super(player, speed, radius);
        this.charge = charge;
        this.particle = particle;


    }
    public FireChargedAttack(Player player, float speed, double charge, Particle particle)
    {
        super(player, speed, 1);
        this.charge = charge;
        this.particle = particle;
    }



    @Override
    public void logic(boolean controllable)
    {
        Location loc = getLocation();
        if(controllable) loc.setDirection(getCaster().getEyeLocation().getDirection());

        GeneralUtils.PlaySound(getLocation(), Sound.BLOCK_FIRE_AMBIENT,.8f);

        ParticleMaker.createSphere(
                particle,
                getLocation(),
                lvl.i(1,2,getPowerLevel())*charge,
                1,
                lvl.i(0.5,1.5,getPowerLevel())*charge,
                0,
                0,
                0,
                0.004*charge);
    }

    @Override
    public boolean onCollide()
    {
        CombatManager.DamageNearby(getLocation(),4,4,4,(float)(lvl.i(5,20,getPowerLevel())*charge),(Player)getCaster());

        getLocation().createExplosion((float)(lvl.i(2,6,getPowerLevel())*charge),true,true);
        return true;
    }

    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {
        for(LivingEntity e : entities)
        {
            if(e.equals(getCaster())) continue;

            e.setFireTicks(5*20);
        }

    }

}
