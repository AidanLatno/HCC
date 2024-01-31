package me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles;

import me.testedpugtato.kingdomcraftplugin.projectiles.PowerProjectile;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collection;

public class TornadoProj extends PowerProjectile
{
    private double charge;

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
        int height = (int)(MathUtils.levelInter(4,20,getPowerLevel())*charge);
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
                    MathUtils.levelInter(1,2,getPowerLevel()) < 0.5 ? 1 : 2,
                    0.1,0.1,0.1,
                    0.01
            );

            for (LivingEntity entity : loc.getNearbyLivingEntities(MathUtils.levelInter(7,10,getPowerLevel()),MathUtils.levelInter(3,10,getPowerLevel()),MathUtils.levelInter(7,10,getPowerLevel()))) {
//            for (LivingEntity entity : loc.getNearbyLivingEntities(4,4,4)) {

                if(entity.equals(getCaster()))
                    continue;

                float theta = 0;

                Location centerLocation = getLocation();
                Location playerToThrowLocation = entity.getLocation();
                entity.damage(MathUtils.levelInter(0.6,2.5,getPowerLevel())*charge,getCaster());
                entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,10,1),true);

                double x = playerToThrowLocation.getX() - centerLocation.getX();
                double y = playerToThrowLocation.getY() - centerLocation.getY();
                double z = playerToThrowLocation.getZ() - centerLocation.getZ();

                theta += Math.PI/4;

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
