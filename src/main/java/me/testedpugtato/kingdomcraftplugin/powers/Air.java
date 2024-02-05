package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.Database;
import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.AirBasicAttackProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.AirDashProjectile;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.AirQuickAttackProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.TornadoProj;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collection;

public class Air extends Power
{
    private AirDashProjectile airDashRef;
    public Air()
    {
        setDashCooldown(4);
        setBasicCooldown(3);
        setQuickCooldown(2);
        setArielCooldown(10);
        setSlamCooldown(15);
        setChargedCooldown(35);
        id = "air";
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel)
    {
        AirBasicAttackProj projectile = new AirBasicAttackProj(player,1.5f,2);
        projectile.moveSelf((int)MathUtils.levelInter(2,3,powerLevel),true,1);
    }

    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        AirQuickAttackProj projectile = new AirQuickAttackProj(player, 2,3);
        projectile.moveSelf((int)MathUtils.levelInter(1,2,powerLevel),true,1);
    }

    @Override
    public void useArielDash(Player player, int powerLevel)
    {
        airDashRef = new AirDashProjectile(player,0.5f,2);
        airDashRef.moveSelf(MathUtils.levelInter(2,4,powerLevel),true,1);
    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        Location bottom = player.getLocation();
        player.setVelocity(new Vector(0, MathUtils.levelInter(1,3, powerLevel), 0));

        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(player.getVelocity().getY() <= 0 || Database.isOnDashCooldown(player))
                    arielInAir(player,powerLevel,(int)bottom.getY(),20*5);
                else
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,2);
            }
        },2);
    }

    public void arielInAir(Player player, int powerLevel, int bottomY, int duration)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {

            int ticks = 0;
            double theta = 0;
            @Override
            public void run() {
                if(ticks >= duration)
                    return;

                ticks++;

                Vector vec = player.getVelocity();

                vec.setY(0);

                player.setVelocity(vec);

                int topY = (int)player.getLocation().getY();
                int difference = topY - bottomY;
                Location loc = new Location(player.getWorld(),player.getLocation().getX(),bottomY,player.getLocation().getZ(),0,90);
                loc.add(0,5,0);
                for(int i = 0; i < difference; i++)
                {

                    loc.add(0,1,0);

                    ParticleMaker.createCircle(
                            Particle.CLOUD,
                            loc,
                            1*(i/10.0f),
                            1,
                            MathUtils.levelInter(1,2,powerLevel) < 0.5 ? 1 : 2,
                            0.1,0.1,0.1,
                            0.01
                            );

                    for (LivingEntity entity : loc.getNearbyLivingEntities(MathUtils.levelInter(7,10,powerLevel),MathUtils.levelInter(3,10,powerLevel),MathUtils.levelInter(7,10,powerLevel))) {
                        if(entity.equals(player))
                            continue;

                        Location playerCenterLocation = player.getLocation();
                        Location playerToThrowLocation = entity.getLocation();
                        entity.damage(MathUtils.levelInter(0.6,5,powerLevel),player);
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,10,1),true);

                        player.sendMessage(entity.getName());
                        double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
                        double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
                        double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

                        double spiralX = Math.sin(theta)*3;
                        double spiralZ = Math.cos(theta)*3;

                        theta += Math.PI/4;

                        Vector throwVector = new Vector(x, y, z);

                        throwVector.normalize();

                        throwVector.rotateAroundY(theta);

                        throwVector.multiply(-1);
                        throwVector.setY(0.2);

                        entity.setVelocity(throwVector);

                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
            }
        });
    }

    @Override
    public void useGroundSlam(Player player, int powerLevel)
    {
        airDashRef.destroySelf();

        player.setVelocity(new Vector(0,-20,0));
        Location loc = player.getLocation();

        loc.add(0,2,0);
        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createCircle(
                Particle.CLOUD,
                loc,
                MathUtils.levelInter(2,10,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                Particle.CLOUD,
                loc,
                MathUtils.levelInter(1,7.5,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel),
                10);
        ParticleMaker.createCircle(
                Particle.CLOUD,
                loc,
                MathUtils.levelInter(0,5,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel),
                -10);

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP,10,2);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {
        if(charge > 2) charge = 2;
        charge /= 2;

        Location loc = player.getLocation();

        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createHelix(
                Particle.CLOUD,
                loc,
                MathUtils.levelInter(0.5,2,powerLevel)*charge,
                (int)MathUtils.levelInter(1,3,powerLevel),
                MathUtils.levelInter(2,4,powerLevel),
                0.03,
                0.03,
                0.03,
                0,
                1);

        player.playSound(player.getLocation(),Sound.ENTITY_CREEPER_HURT, SoundCategory.MASTER,100,2);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(charge > 2) charge = 2;
        charge /= 2;


        player.getWorld().spawnParticle(Particle.CLOUD,player.getLocation(),(int)MathUtils.levelInter(100,800,powerLevel), 1,0,1,0.3,null,true);
        player.getWorld().spawnParticle(Particle.CLOUD,player.getLocation(),(int)MathUtils.levelInter(1000,8000,powerLevel), MathUtils.levelInter(5,15,powerLevel),1,MathUtils.levelInter(5,15,powerLevel),0.3,null,true);
        player.playSound(player.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER,100,2);
        player.playSound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,2);


        CombatManager.DamageNearby(player.getLocation(),7,3,7,(int)(MathUtils.levelInter(2,12,powerLevel)*charge),player);

        Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(7,3,7);

        for (LivingEntity entity : entities) {
            if(entity.equals(player))
                continue;

            Location playerCenterLocation = player.getEyeLocation();
            Location playerToThrowLocation = entity.getEyeLocation();

            double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
            double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
            double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

            Vector throwVector = new Vector(x, y, z);

            throwVector.normalize();
            throwVector.multiply(3.0*charge);
            throwVector.setY(2.0*charge);

            entity.setVelocity(throwVector);

        }
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge)
    {
        if(charge > 6) charge = 6;
        charge /= 6;

        Location loc = player.getEyeLocation();

        loc.add(player.getLocation().getDirection().multiply(6));

        player.playSound(loc,Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER,MathUtils.levelInter(0.05f,0.5f,(int)(charge*100)),0);
        if(charge == 1)  player.playSound(loc, Sound.ENTITY_CREEPER_HURT,0.2f,2);

        double scale = 1.1 - charge;

        ParticleMaker.createSphere(
                Particle.CLOUD,
                loc,
                MathUtils.levelInter(1,2,powerLevel)*scale,
                1,
                MathUtils.levelInter(0.5,1,powerLevel)*scale,
                0,
                0,
                0,
                0.004*scale);
        ParticleMaker.createSphere(
                Particle.END_ROD,
                loc,
                1*scale,
                (int)(MathUtils.levelInter(1,5,powerLevel)),
                0.1,
                0,
                0,
                0,
                0.3*scale);
    }
    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {
        if(charge > 6) charge = 6;
        charge /= 6;

        Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(6));

        loc.getWorld().spawnParticle(Particle.CLOUD,loc,500,1,5,1,0.5,null,true);

        TornadoProj proj = new TornadoProj(player,0.4f,charge);
        proj.setLocation(loc);
        proj.moveSelf(MathUtils.levelInter(6,10,powerLevel),true,1);

    }
}
