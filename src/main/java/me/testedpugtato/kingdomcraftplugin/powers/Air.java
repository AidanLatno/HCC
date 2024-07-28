package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.Database;
import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.barriers.AirDomain;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.AirBasicAttackProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.AirDashProjectile;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.AirQuickAttackProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.TornadoProj;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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
        projectile.moveSelf((int)lvl.i(2,3,powerLevel),true,1);
    }

    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        AirQuickAttackProj projectile = new AirQuickAttackProj(player, 2,3);
        projectile.moveSelf((int)lvl.i(1,2,powerLevel),true,1);
    }

    @Override
    public void useArielDash(Player player, int powerLevel)
    {
        airDashRef = new AirDashProjectile(player,0.5f,2);
        airDashRef.moveSelf(lvl.i(2,4,powerLevel),true,1);
    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        Location bottom = player.getLocation();
        player.setVelocity(new Vector(0, lvl.i(1,3, powerLevel), 0));

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

                GeneralUtils.PlaySound(player.getEyeLocation(),Sound.ENTITY_CREEPER_HURT,100,2);
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
                            lvl.i(1,2,powerLevel) < 0.5 ? 1 : 2,
                            0.1,0.1,0.1,
                            0.01
                            );

                    for (LivingEntity entity : loc.getNearbyLivingEntities(lvl.i(7,10,powerLevel),lvl.i(3,10,powerLevel),lvl.i(7,10,powerLevel))) {
                        if(entity.equals(player))
                            continue;

                        Location playerCenterLocation = player.getLocation();
                        Location playerToThrowLocation = entity.getLocation();
                        CombatManager.DamageEntity(lvl.i(0.6,5,powerLevel),entity,player);
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,10,1),true);

                        double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
                        double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
                        double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

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
                lvl.i(2,10,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                Particle.CLOUD,
                loc,
                lvl.i(1,7.5,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                10);
        ParticleMaker.createCircle(
                Particle.CLOUD,
                loc,
                lvl.i(0,5,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                -10);

        GeneralUtils.PlaySound(player.getEyeLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,10,2);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {
        if(charge > 1) charge = 1;
        charge /= 1;

        Location loc = player.getLocation();

        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createHelix(
                Particle.CLOUD,
                loc,
                lvl.i(0.5,2,powerLevel)*charge,
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,4,powerLevel),
                0.03,
                0.03,
                0.03,
                0,
                1);

        GeneralUtils.PlaySound(player.getEyeLocation(),Sound.ENTITY_CREEPER_HURT,100,2);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(charge > 1) charge = 1;
        charge /= 1;

        ParticleMaker.SpawnParticle(player.getLocation(),Particle.CLOUD,(int)lvl.i(100,800,powerLevel), 1,0,1,0.3f);
        ParticleMaker.SpawnParticle(player.getLocation(),Particle.CLOUD,(int)lvl.i(1000,8000,powerLevel), lvl.i(5,15,powerLevel),1,lvl.i(5,15,powerLevel),0.3f);

        GeneralUtils.PlaySound(player.getEyeLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,100,2);
        GeneralUtils.PlaySound(player.getEyeLocation(),Sound.ENTITY_GENERIC_EXPLODE,100,2);

        CombatManager.DamageNearby(player.getLocation(),7,3,7,(int)(lvl.i(2,12,powerLevel)*charge),player);
        CombatManager.ApplyPulse(player.getLocation(), (float) (3f*charge),(float) (2f*charge),new Vector(7,3,7),player);
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge)
    {
        if(charge > 4) charge = 4;
        charge /= 4;

        Location loc = player.getEyeLocation();

        loc.add(player.getLocation().getDirection().multiply(6));

        GeneralUtils.PlaySound(loc,Sound.ENTITY_PLAYER_ATTACK_SWEEP,lvl.i(0.05f,0.5f,(int)(charge*100)),0);
        if(charge == 1)  GeneralUtils.PlaySound(loc,Sound.ENTITY_CREEPER_HURT,0.2f,2);

        double scale = 1.1 - charge;

        ParticleMaker.createSphere(
                Particle.CLOUD,
                loc,
                lvl.i(1,2,powerLevel)*scale,
                1,
                lvl.i(0.5,1,powerLevel)*scale,
                0,
                0,
                0,
                0.004*scale);
        ParticleMaker.createSphere(
                Particle.END_ROD,
                loc,
                1*scale,
                (int)(lvl.i(1,5,powerLevel)),
                0.1,
                0,
                0,
                0,
                0.3*scale);
    }
    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {
        if(charge > 4) charge = 4;
        charge /= 4;

        Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(6));

        ParticleMaker.SpawnParticle(loc, Particle.CLOUD,500,1,5,1,0.5f);
        TornadoProj proj = new TornadoProj(player,0.4f,charge);
        proj.setLocation(loc);
        proj.moveSelf(lvl.i(6,10,powerLevel),true,1);

    }

    @Override
    public void domainExpand(Player player)
    {
        AirDomain domain = new AirDomain(player,10, player.getLocation(),30,3,1,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }
}
