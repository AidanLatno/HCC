package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles.FireBasicAttackProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles.FireChargedAttack;
import me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles.FireQuickAttack;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.Collection;

public class Fire extends Power
{
    Particle particle = Particle.FLAME;

    public Fire()
    {
        setBasicCooldown(3);
        setDashCooldown(5);
        setQuickCooldown(1);
        setArielCooldown(7);
        setChargedCooldown(15);
        setSlamCooldown(15);
        id = "fire";
    }
    @Override
    public void useBasicAttack(Player player, int powerLevel)
    {
        setParticle(player,powerLevel);

        FireBasicAttackProj projectile = new FireBasicAttackProj(player,1.5f,particle,MathUtils.levelInter(0.4,0.8,powerLevel));

        projectile.moveSelf(2,false,1);


    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        setParticle(player,powerLevel);

        player.setVelocity(new Vector(0, MathUtils.levelInter(1,2, powerLevel), 0));
        Location loc = player.getLocation();

        loc.setPitch(90);
        loc.setYaw(0);
        for(int i = 0; i < (int)MathUtils.levelInter(6,15, powerLevel); i++)
        {
            Location loc2 = loc.clone();
            loc2.add(0,i,0);
            ParticleMaker.createHelix(
                    particle,
                    loc2,
                    MathUtils.levelInter(0.7, 3, powerLevel),
                    3,
                    MathUtils.levelInter(2, 8, powerLevel),
                    0.2,
                    0.2,
                    0.2,
                    0.07,
                    1);
        }
        player.getWorld().playSound(player.getLocation(),Sound.BLOCK_FIRE_AMBIENT, SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,0);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,2);

    }
    @Override
    public void useArielDash(Player player, int powerLevel)
    {
        setParticle(player,powerLevel);

        player.setVelocity(player.getLocation().getDirection().clone().multiply(MathUtils.levelInter(1,3,powerLevel)));
        ParticleMaker.createCircle(
                particle,
                player.getLocation(),
                MathUtils.levelInter(2,10,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                particle,
                player.getLocation(),
                MathUtils.levelInter(1,7.5,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel),
                10);
        ParticleMaker.createCircle(
                particle,
                player.getLocation(),
                MathUtils.levelInter(0,5,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel),
                -10);
        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,2);

    }
    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        setParticle(player,powerLevel);
        FireQuickAttack proj = new FireQuickAttack(player,3,particle,2);
        proj.moveSelf(MathUtils.levelInter(0.4,1,powerLevel),true,1);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,10,2);
    }
    @Override
    public void useGroundSlam(Player player, int powerLevel)
    {
        setParticle(player,powerLevel);

        player.setVelocity(new Vector(0,-20,0));
        Location loc = player.getLocation();

        loc.add(0,2,0);
        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createCircle(
                particle,
                loc,
                MathUtils.levelInter(2,10,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                particle,
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
                particle,
                loc,
                MathUtils.levelInter(0,5,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel),
                -10);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,10,0);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {
        if(charge > 2) charge = 2;
        charge /= 2;

        setParticle(player,powerLevel);

        Location loc = player.getLocation();

        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createHelix(
                particle,
                loc,
                MathUtils.levelInter(0.5,2,powerLevel)*charge,
                (int)MathUtils.levelInter(1,3,powerLevel),
                MathUtils.levelInter(2,4,powerLevel),
                0.03,
                0.03,
                0.03,
                0,
                1);

        player.getWorld().playSound(player.getLocation(),Sound.BLOCK_FIRE_AMBIENT, SoundCategory.MASTER,100,2);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(charge > 2) charge = 2;
        charge /= 2;

        setParticle(player,powerLevel);

        player.getWorld().spawnParticle(particle,player.getLocation(),(int)MathUtils.levelInter(100,800,powerLevel), 1,0,1,0.3,null,true);
        player.getWorld().spawnParticle(particle,player.getLocation(),(int)MathUtils.levelInter(1000,8000,powerLevel), MathUtils.levelInter(5,15,powerLevel),1,MathUtils.levelInter(5,15,powerLevel),0.3,null,true);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, SoundCategory.MASTER,100,0);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,100,0);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,100,0);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,100,1);


        CombatManager.DamageNearby(player.getLocation(),7,3,7,(int)(MathUtils.levelInter(5,18,powerLevel)*charge),player);

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
            throwVector.multiply(1.5);
            throwVector.setY(1.0);

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

        player.getWorld().playSound(loc,Sound.BLOCK_FIRE_AMBIENT, SoundCategory.MASTER,0.4f,0);
        if(charge == 1)  player.getWorld().playSound(loc, Sound.ENTITY_BLAZE_SHOOT,0.2f,2);

        setParticle(player,powerLevel);

        ParticleMaker.createSphere(
                particle,
                loc,
                MathUtils.levelInter(1,2,powerLevel)*charge,
                1,
                MathUtils.levelInter(0.5,1,powerLevel)*charge,
                0,
                0,
                0,
                0.004*charge);
        ParticleMaker.createSphere(
                particle,
                loc,
                1*charge,
                (int)(MathUtils.levelInter(1,5,powerLevel)),
                0.1,
                0,
                0,
                0,
                0.1*charge);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {
        if(charge > 6) charge = 6;
        charge /= 6;

        FireChargedAttack blast = new FireChargedAttack(player,1.5f,charge*2,particle,3);
        blast.moveSelf(MathUtils.levelInter(1,3,powerLevel), false,1);

    }

    private void setParticle(Player player, int powerLevel)
    {
        if(PlayerUtility.getPlayerMemory(player).isKing() || powerLevel >= 90)
            particle = Particle.SOUL_FIRE_FLAME;
        else particle = Particle.FLAME;
    }

}
