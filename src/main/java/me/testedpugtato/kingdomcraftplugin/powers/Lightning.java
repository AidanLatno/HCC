package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.projectiles.LightningProjectiles.LightningBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
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
        proj.moveSelf(MathUtils.levelInter(0.4, 1, powerLevel), true);
    }

    @Override
    public void useAriel(Player player, int powerLevel) {
        Vector dir = player.getLocation().getDirection();

        dir.add(new Vector(0,MathUtils.levelInter(2,3,powerLevel),0));
        dir.multiply(new Vector(-1*MathUtils.levelInter(1.5,4,powerLevel),1,-1*MathUtils.levelInter(1.5,4,powerLevel)));
        player.setVelocity(dir);

        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_CREEPER_PRIMED,SoundCategory.MASTER,100,2);

        Location loc = player.getLocation().clone();
        Vector vec = loc.getDirection();
        vec.setY(-0.05);
        vec.normalize();
        vec.multiply(2);
        loc.add(vec);
        player.spawnParticle(Particle.SCRAPE,loc,2000,2,1.3,2,1);

    }

    @Override
    public void useArielDash(Player player, int powerLevel) {
        player.setVelocity(player.getLocation().getDirection().clone().multiply(10));


        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_CREEPER_PRIMED,SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(),Sound.BLOCK_BEEHIVE_WORK,SoundCategory.MASTER,100,0);

        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                player.getWorld().spawnParticle(Particle.SCRAPE,player.getEyeLocation(),4,.2,.2,.2,0, null, true);
                if(ticks >= MathUtils.levelInter(0.1,0.5, powerLevel)*20) {
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
        if(charge > 6) charge = 6;
        charge /= 6;
        double scale = 1.1 - charge;

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEEHIVE_WORK, SoundCategory.MASTER,100,0);
        if(charge == 1) player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CREEPER_HURT, SoundCategory.MASTER,100,0);

        ParticleMaker.createSphere(
                Particle.SCRAPE,
                player.getEyeLocation(),
                MathUtils.levelInter(1,2,powerLevel)*scale,
                1,
                MathUtils.levelInter(0.5,1,powerLevel)*scale,
                0,
                0,
                0,
                0.004*scale);
        ParticleMaker.createSphere(
                Particle.END_ROD,
                player.getEyeLocation(),
                1*scale,
                (int)(MathUtils.levelInter(1,5,powerLevel)),
                0.1,
                0,
                0,
                0,
                0.1*scale);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge) {
        if(charge > 6) charge = 6;
        charge /= 6;

        player.getWorld().spawnParticle(Particle.SCRAPE,player.getLocation(),(int)MathUtils.levelInter(100,800,powerLevel), 1,0,1,0.3,null,true);
        player.getWorld().spawnParticle(Particle.SCRAPE,player.getLocation(),(int)MathUtils.levelInter(1000,8000,powerLevel), MathUtils.levelInter(5*charge,10*charge,powerLevel),1,MathUtils.levelInter(5*charge,15*charge,powerLevel),0.3,null,true);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.MASTER,100,1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.MASTER,100,0);


        CombatManager.DamageNearby(player.getLocation(),MathUtils.levelInter(5*charge,10*charge,powerLevel),3,MathUtils.levelInter(5*charge,10*charge,powerLevel),(int)(MathUtils.levelInter(5*charge,18*charge,powerLevel)*charge),player);

        Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(MathUtils.levelInter(5*charge,10*charge,powerLevel),3,MathUtils.levelInter(5*charge,10*charge,powerLevel));

        for (LivingEntity entity : entities) {
            if(entity.equals(player))
                continue;

            entity.getWorld().strikeLightningEffect(entity.getLocation());

            PlayerUtility.getPlayerMemory((Player) entity).stun(2*20);

            Location playerCenterLocation = player.getEyeLocation();
            Location playerToThrowLocation = entity.getEyeLocation();

            double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
            double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
            double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

            Vector throwVector = new Vector(x, y, z);

            throwVector.normalize();
            throwVector.multiply(3.5);
            throwVector.setY(2.0);

            entity.setVelocity(throwVector);

        }
    }

    @Override
    public void useQuickAttack(Player player, int powerLevel) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            final float time = MathUtils.levelInter(3,6,powerLevel);
            @Override
            public void run(){
                ticks++;
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEEHIVE_WORK, SoundCategory.MASTER,100,0);
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
                    CombatManager.DamageEntity(MathUtils.levelInter(3,10,powerLevel),entity,player);
                    player.spawnParticle(Particle.SCRAPE,loc,60,.1,.1,.1,0.6);
                    player.spawnParticle(Particle.BUBBLE_POP,loc,60,.1,.1,.1,0.6);
                    player.setWalkSpeed(0.2f);
                    entity.getWorld().strikeLightningEffect(entity.getLocation());
                    return;
                }

                player.spawnParticle(Particle.SCRAPE,loc,10,.1,.1,.1,0);
                player.spawnParticle(Particle.BUBBLE_POP,loc,10,.1,.1,.1,0);
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
                MathUtils.levelInter(2,10,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                Particle.SCRAPE,
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
                Particle.SCRAPE,
                loc,
                MathUtils.levelInter(0,5,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel),
                -10);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER,10,0);
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
                Particle.SCRAPE,
                loc,
                MathUtils.levelInter(0.5,2,powerLevel)*charge,
                (int)MathUtils.levelInter(1,3,powerLevel),
                MathUtils.levelInter(2,4,powerLevel),
                0.03,
                0.03,
                0.03,
                0,
                1);
        for(int i = 0; i < MathUtils.levelInter(1,5,powerLevel); i++)
        {
            Location strikeLoc = player.getLocation().clone();
            strikeLoc.add((Math.random() * 2)-1,0,(Math.random() * 2)-1);
            player.getLocation().getWorld().strikeLightningEffect(strikeLoc);
        }

        player.getWorld().playSound(player.getLocation(),Sound.BLOCK_BEEHIVE_WORK, SoundCategory.MASTER,100,0);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(charge > 2) charge = 2;
        charge /= 2;

        player.getWorld().spawnParticle(Particle.SCRAPE,player.getLocation(),(int)MathUtils.levelInter(100,800,powerLevel), 1,0,1,0.3,null,true);
        player.getWorld().spawnParticle(Particle.SCRAPE,player.getLocation(),(int)MathUtils.levelInter(1000,8000,powerLevel), MathUtils.levelInter(5,15,powerLevel),1,MathUtils.levelInter(5,15,powerLevel),0.3,null,true);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,0);

        for(int i = 0; i < MathUtils.levelInter(3,25,powerLevel); i++)
        {
            Location loc = player.getLocation().clone();
            loc.add((Math.random() * 12)-6,0,(Math.random() * 12)-6);
            player.getLocation().getWorld().strikeLightningEffect(loc);
        }

        CombatManager.DamageNearby(player.getLocation(),MathUtils.levelInter(10*charge,20*charge,powerLevel),3,MathUtils.levelInter(10*charge,20*charge,powerLevel),(int)(MathUtils.levelInter(10,40,powerLevel)*charge),player);

        Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(MathUtils.levelInter(10*charge,20*charge,powerLevel),3,MathUtils.levelInter(10*charge,20*charge,powerLevel));

        for (LivingEntity entity : entities) {
            if(entity.equals(player))
                continue;

            if(entity instanceof Player) PlayerUtility.getPlayerMemory((Player) entity).stun(2*20);

            Location playerCenterLocation = player.getEyeLocation();
            Location playerToThrowLocation = entity.getEyeLocation();

            double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
            double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
            double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

            Vector throwVector = new Vector(x, y, z);

            throwVector.normalize();
            throwVector.multiply(1.5);
            throwVector.setY(1.0);

            entity.setVelocity(throwVector);

        }
    }
}
