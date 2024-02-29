package me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
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

        getCaster().getWorld().playSound(getLocation(), Sound.BLOCK_FIRE_AMBIENT,.8f,1);

        ParticleMaker.createSphere(
                particle,
                getLocation(),
                MathUtils.levelInter(1,2,getPowerLevel())*charge,
                1,
                MathUtils.levelInter(0.5,1.5,getPowerLevel())*charge,
                0,
                0,
                0,
                0.004*charge);
    }

    @Override
    public boolean onCollide()
    {
        CombatManager.DamageNearby(getLocation(),4,4,4,(float)(MathUtils.levelInter(5,20,getPowerLevel())*charge),(Player)getCaster());

        getLocation().createExplosion((float)(MathUtils.levelInter(2,6,getPowerLevel())*charge),true,true);
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
