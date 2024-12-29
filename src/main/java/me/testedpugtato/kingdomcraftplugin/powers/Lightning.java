package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.projectiles.LightningProjectiles.LightningBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collection;

public class Lightning extends Power
{
    public Lightning()
    {
        setBasicCooldown(3);
        setDashCooldown(5);
        setQuickCooldown(10);
        setArielCooldown(5);
        setChargedCooldown(15);
        setSlamCooldown(15);
        id = "lightning";
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel) {
        LightningBasicProj proj = new LightningBasicProj(player, 4, 7);
        proj.moveSelf(lvl.i(0.4, 1, powerLevel), true);
    }

    @Override
    public void useAriel(Player player, int powerLevel) {
        Vector dir = player.getLocation().getDirection();

        dir.add(new Vector(0,lvl.i(2,3,powerLevel),0));
        dir.multiply(new Vector(-1*lvl.i(1.5,4,powerLevel),1,-1*lvl.i(1.5,4,powerLevel)));
        player.setVelocity(dir);

        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_CREEPER_PRIMED,100,2);

        Location loc = player.getLocation().clone();
        Vector vec = loc.getDirection();
        vec.setY(-0.05);
        vec.normalize();
        vec.multiply(2);
        loc.add(vec);
        ParticleMaker.SpawnParticle(loc, Particle.SCRAPE,2000,2,1.3f,2,1);
        for(LivingEntity e : MathUtils.getEntitiesInSphere(player.getLocation(),20))
        {
            if(e instanceof Player && !e.equals(player))
                PlayerUtility.getPlayerMemory((Player)e).stun(3*20);
        }


    }

    @Override
    public void useArielDash(Player player, int powerLevel) {
        player.setVelocity(player.getLocation().getDirection().clone().multiply(10));

        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_CREEPER_PRIMED,100,2);
        GeneralUtils.PlaySound(player.getLocation(),Sound.BLOCK_BEEHIVE_WORK,100,0);

        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;

                ParticleMaker.SpawnParticle(player.getEyeLocation(), Particle.SCRAPE,4,.2f,.2f,.2f,0);

                if(ticks >= lvl.i(0.1,0.5, powerLevel)*20) {
                    player.setVelocity(new Vector(0,0,0));
                }
                else {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), this, 1);
                }
            }
        },1);
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge) {
        if(charge > 4) charge = 4;
        charge /= 4;
        double scale = 1.1 - charge;

        GeneralUtils.PlaySound(player.getLocation(), Sound.BLOCK_BEEHIVE_WORK,100,0);
        if(charge == 1) GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_CREEPER_HURT,100,0);

        ParticleMaker.createSphere(
                Particle.SCRAPE,
                player.getEyeLocation(),
                lvl.i(1,2,powerLevel)*scale,
                1,
                lvl.i(0.5,1,powerLevel)*scale,
                0,
                0,
                0,
                0.004*scale);
        ParticleMaker.createSphere(
                Particle.END_ROD,
                player.getEyeLocation(),
                1*scale,
                (int)(lvl.i(1,5,powerLevel)),
                0.1,
                0,
                0,
                0,
                0.1*scale);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge) {
        if(charge > 4) charge = 4;
        charge /= 4;

        ParticleMaker.SpawnParticle(player.getLocation(),Particle.SCRAPE,(int)lvl.i(100,800,powerLevel), 1,0,1,0.3f);
        ParticleMaker.SpawnParticle(player.getLocation(),Particle.SCRAPE,(int)lvl.i(1000,8000,powerLevel), lvl.i(5*charge,10*charge,powerLevel),1,lvl.i(5*charge,15*charge,powerLevel),0.3f);

        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,100,2);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,100);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,100,0);


        CombatManager.DamageNearby(player.getLocation(),lvl.i(5*charge,10*charge,powerLevel),3,lvl.i(5*charge,10*charge,powerLevel),(int)(lvl.i(5*charge,18*charge,powerLevel)*charge),player);

        Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(lvl.i(5*charge,10*charge,powerLevel),3,lvl.i(5*charge,10*charge,powerLevel));

        for (LivingEntity entity : entities) {
            if (entity.equals(player))
                continue;

            entity.getWorld().strikeLightningEffect(entity.getLocation());
            PlayerUtility.getPlayerMemory((Player) entity).stun(2 * 80);
        }
        CombatManager.ApplyPulse(player.getLocation(),3.5f,2f,entities,player);
    }

    @Override
    public void useQuickAttack(Player player, int powerLevel) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            final float time = lvl.i(3,6,powerLevel);
            @Override
            public void run(){
                ticks++;
                GeneralUtils.PlaySound(player.getLocation(), Sound.BLOCK_BEEHIVE_WORK,100,0);
                player.setWalkSpeed(0.8f);
                Location loc = player.getEyeLocation();
                Vector direction = loc.getDirection();
                double length = direction.length();

                direction.multiply(1 - 0.2 / length);
                loc.add(direction);
                direction.normalize();
                direction.multiply(1 - 0.5 / length);
                direction.rotateAroundY(Math.toRadians(-90));
                loc.add(direction);

                loc.subtract(0,0.3,0);

                for (LivingEntity entity : loc.getNearbyLivingEntities(2, 2, 2)) {
                    if (entity.equals(player)) continue;
                    CombatManager.DamageEntity(lvl.i(3,10,powerLevel),entity,player);
                    ParticleMaker.SpawnParticle(loc, Particle.SCRAPE,60,.1f,.1f,.1f,0.6f);
                    ParticleMaker.SpawnParticle(loc, Particle.BUBBLE_POP,60,.1f,.1f,.1f,0.6f);
                    player.setWalkSpeed(0.2f);
                    entity.getWorld().strikeLightningEffect(entity.getLocation());

                    return;
                }

                ParticleMaker.SpawnParticle(loc, Particle.SCRAPE,10,.1f,.1f,.1f);
                ParticleMaker.SpawnParticle(loc, Particle.BUBBLE_POP,10,.1f,.1f,.1f);
                if(ticks/20.0f < time) Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
                else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,60,10,false,false,false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,60,-20,false,false,false));
                    player.setWalkSpeed(0.2f);
                }
            }
        },1);
    }

    @Override
    public void useGroundSlam(Player player, int powerLevel)
    {

        player.setVelocity(new Vector(0,-20,0));
        Location loc = player.getLocation();

        loc.add(0,2,0);
        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createCircle(
                Particle.SCRAPE,
                loc,
                lvl.i(2,10,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                Particle.SCRAPE,
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
                Particle.SCRAPE,
                loc,
                lvl.i(0,5,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                -10);

        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER,10,0);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {
        if(charge > .6) charge = .6;
        charge /= .6;

        Location loc = player.getLocation();

        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createHelix(
                Particle.SCRAPE,
                loc,
                lvl.i(0.5,2,powerLevel)*charge,
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,4,powerLevel),
                0.03,
                0.03,
                0.03,
                0,
                1);
        for(int i = 0; i < lvl.i(1,5,powerLevel); i++)
        {
            Location strikeLoc = player.getLocation().clone();
            strikeLoc.add((Math.random() * 2)-1,0,(Math.random() * 2)-1);
            player.getLocation().getWorld().strikeLightningEffect(strikeLoc);
        }

        GeneralUtils.PlaySound(player.getLocation(),Sound.BLOCK_BEEHIVE_WORK, 100,0);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(charge > .6) charge = .6;
        charge /= .6;

        ParticleMaker.SpawnParticle(player.getLocation(), Particle.SCRAPE, (int)lvl.i(100,800,powerLevel), 1,0,1,0.3f);
        ParticleMaker.SpawnParticle(player.getLocation(), Particle.SCRAPE, (int)lvl.i(1000,8000,powerLevel), lvl.i(5,15,powerLevel),1,lvl.i(5,15,powerLevel),0.3f);

        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100,2);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100,0);

        for(int i = 0; i < lvl.i(3,25,powerLevel); i++)
        {
            Location loc = player.getLocation().clone();
            loc.add((Math.random() * 12)-6,0,(Math.random() * 12)-6);
            player.getLocation().getWorld().strikeLightningEffect(loc);
        }

        CombatManager.DamageNearby(player.getLocation(),lvl.i(10*charge,20*charge,powerLevel),3,lvl.i(10*charge,20*charge,powerLevel),(int)(lvl.i(10,40,powerLevel)*charge),player);
        CombatManager.ApplyPulse(player.getLocation(),1.5f,1f,new Vector(lvl.i(10*charge,20*charge,powerLevel),3,lvl.i(10*charge,20*charge,powerLevel)),player);
    }
}
