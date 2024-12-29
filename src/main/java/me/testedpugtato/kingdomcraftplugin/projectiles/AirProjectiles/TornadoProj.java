package me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import me.testedpugtato.kingdomcraftplugin.util.lvl;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collection;

public class TornadoProj extends PowerProjectile
{
    private final double charge;

    public TornadoProj(LivingEntity caster, float speed, float radius, double charge) {
        super(caster, speed, radius);
        this.charge = charge;
    }

    public TornadoProj(LivingEntity caster, float speed, double charge) {
        super(caster, speed);
        this.charge = charge;
    }

    @Override
    public void logic(boolean controllable)
    {
        int height = (int)(lvl.i(4,20,getPowerLevel())*charge);
        Location loc = new Location(getCaster().getWorld(),getLocation().getX(),getLocation().getY(),getLocation().getZ(),0,90);
        loc.add(0,5,0);
        for(int i = 0; i < height; i++)
        {
            loc.setPitch(90);
            loc.setYaw(0);
            loc.add(0,1,0);

            ParticleMaker.createCircle(
                    Particle.CLOUD,
                    loc,
                    2*(i/10.0f),
                    1,
                    lvl.i(1,2,getPowerLevel()) < 0.5 ? 1 : 2,
                    0.1,0.1,0.1,
                    0.01
            );

            getLocation().getWorld().playSound(getLocation(), Sound.ENTITY_CREEPER_HURT,0.1f,5);

            for (LivingEntity entity : loc.getNearbyLivingEntities(lvl.i(7,10,getPowerLevel()),lvl.i(3,10,getPowerLevel()),lvl.i(7,10,getPowerLevel()))) {
                if(entity.equals(getCaster()))
                    continue;

                float theta = 0;

                Location centerLocation = getLocation();
                Location playerToThrowLocation = entity.getLocation();
                CombatManager.DamageEntity((float) (lvl.i(0.2,1,getPowerLevel())*charge),entity,getCaster());

                double x = playerToThrowLocation.getX() - centerLocation.getX();
                double y = playerToThrowLocation.getY() - centerLocation.getY();
                double z = playerToThrowLocation.getZ() - centerLocation.getZ();

                theta += (float) (Math.PI/4);

                Vector throwVector = new Vector(x, y, z);

                throwVector.normalize();

                throwVector.rotateAroundY(theta);

                throwVector.multiply(-1);
                throwVector.setY(0.2);

                entity.setVelocity(throwVector);

            }
        }
    }

    @Override
    public void entityInteract(Collection<LivingEntity> entities)
    {

    }

    @Override
    public boolean onCollide()
    {
        return false;
    }
}
