package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.projectiles.LightningProjectiles.LightningBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collection;

public class Lightning extends Power
{
    public int dashesUsed = 0;
    public Lightning()
    {
        setBasicCooldown(3);
        setDashCooldown(1);
        setQuickCooldown(10);
        setArielCooldown(5);
        setChargedCooldown(15);
        setSlamCooldown(15);
        id = "lightning";
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel) {
        LightningBasicProj proj = new LightningBasicProj(player,4,7);
        proj.moveSelf(MathUtils.levelInter(0.4,1,powerLevel),true);
    }

    @Override
    public void useAriel(Player player, int playerLevel) {
        Vector dir = player.getLocation().getDirection();

        dir.add(new Vector(0,3,0));
        dir.multiply(new Vector(-1,1,-1));
        player.setVelocity(dir);

        Location loc1;
        Location loc2;
        for(int x = 0; x < 20; x++) {
            loc1 = player.getLocation().clone();
            loc2 = loc1.clone();
            Vector vec = loc1.getDirection();
            vec.multiply(new Vector(0, -1, 0));
            for (int i = 0; i < 5; i++) {
                loc2.add(vec);
                ParticleMaker.createLine(Particle.SCRAPE, loc1, loc2);
                loc1 = loc2;
                vec.add(new Vector(0.3, 0, 0.3));

            }
        }
    }

    @Override
    public void useArielDash(Player player, int powerLevel) {
        if(dashesUsed >= 4) return;

        player.setVelocity(player.getLocation().getDirection().clone().multiply(10));
        dashesUsed++;

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
        double scale = 1.1 - charge;

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
                0.3*scale);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge) {
        super.useChargedAttack(player, powerLevel, charge);
    }

    @Override
    public void useQuickAttack(Player player, int powerLevel) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            final float time = MathUtils.levelInter(3,6,powerLevel);
            @Override
            public void run(){
                ticks++;
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
                    player.sendMessage(entity.getName());
                    CombatManager.DamageEntity(MathUtils.levelInter(3,10,powerLevel),entity,player);
                    player.spawnParticle(Particle.SCRAPE,loc,60,.1,.1,.1,0.6);
                    player.spawnParticle(Particle.BUBBLE_POP,loc,60,.1,.1,.1,0.6);
                    player.setWalkSpeed(0.2f);
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
    public void useGroundSlam(Player player, int powerLevel) {
        super.useGroundSlam(player, powerLevel);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge) {
        super.groundSlamFalling(player, powerLevel, charge);
    }

    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge) {
        super.useGroundSlamLanding(player, powerLevel, charge);
    }
}
