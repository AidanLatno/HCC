package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.GeneralEvents;
import me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles.FireBasicAttackProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles.FireChargedAttack;
import me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles.FireQuickAttack;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;


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

        FireBasicAttackProj projectile = new FireBasicAttackProj(player,1.5f,particle,lvl.i(0.4,0.8,powerLevel));

        projectile.moveSelf(2,false,1);


    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        setParticle(player,powerLevel);

        player.setVelocity(new Vector(0, lvl.i(1,2, powerLevel), 0));
        Location loc = player.getLocation();

        loc.setPitch(90);
        loc.setYaw(0);
        for(int i = 0; i < (int)lvl.i(6,15, powerLevel); i++)
        {
            Location loc2 = loc.clone();
            loc2.add(0,i,0);
            ParticleMaker.createHelix(
                    particle,
                    loc2,
                    lvl.i(0.7, 3, powerLevel),
                    3,
                    lvl.i(2, 8, powerLevel),
                    0.2,
                    0.2,
                    0.2,
                    0.07,
                    1);
        }
        GeneralUtils.PlaySound(player.getLocation(),Sound.BLOCK_FIRE_AMBIENT,100,2);
        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,100,2);
        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,100,0);

        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,1);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,2);

    }
    @Override
    public void useArielDash(Player player, int powerLevel)
    {
        setParticle(player,powerLevel);

        player.setVelocity(player.getLocation().getDirection().clone().multiply(lvl.i(1,3,powerLevel)));
        ParticleMaker.createCircle(
                particle,
                player.getLocation(),
                lvl.i(2,10,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                particle,
                player.getLocation(),
                lvl.i(1,7.5,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                10);
        ParticleMaker.createCircle(
                particle,
                player.getLocation(),
                lvl.i(0,5,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                -10);

        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,100,2);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,1);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,2);
    }
    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        setParticle(player,powerLevel);
        FireQuickAttack proj = new FireQuickAttack(player,3,particle,2);
        proj.moveSelf(lvl.i(0.4,1,powerLevel),true,1);

        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_BLAZE_SHOOT,10,2);
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
                lvl.i(2,10,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                particle,
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
                particle,
                loc,
                lvl.i(0,5,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                -10);

        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,10,0);
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
                lvl.i(0.5,2,powerLevel)*charge,
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,4,powerLevel),
                0.03,
                0.03,
                0.03,
                0,
                1);

        GeneralUtils.PlaySound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT,100,2);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(charge > 2) charge = 2;
        charge /= 2;

        setParticle(player,powerLevel);

        GeneralUtils.SpawnParticle(player.getLocation(),particle,(int)lvl.i(100,800,powerLevel), 1,0,1,0.3f);
        GeneralUtils.SpawnParticle(player.getLocation(),particle,(int)lvl.i(1000,8000,powerLevel), lvl.i(5,15,powerLevel),1,lvl.i(5,15,powerLevel),0.3f);

        GeneralUtils.PlaySound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT,100,0);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,100,2);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,100);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,100,0);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,100,0);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT,100);

        CombatManager.DamageNearby(player.getLocation(),7,3,7,(int)(lvl.i(5,18,powerLevel)*charge),player);

        CombatManager.ApplyPulse(player.getLocation(),1.5f,1f,new Vector(7,3,7),player);
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge)
    {
        if(charge > 6) charge = 6;
        charge /= 6;

        Location loc = player.getEyeLocation();

        loc.add(player.getLocation().getDirection().multiply(6));

        GeneralUtils.PlaySound(loc,Sound.BLOCK_FIRE_AMBIENT,0.4f,0);
        if(charge == 1) GeneralUtils.PlaySound(loc, Sound.ENTITY_BLAZE_SHOOT,0.2f,2);

        setParticle(player,powerLevel);

        ParticleMaker.createSphere(
                particle,
                loc,
                lvl.i(1,2,powerLevel)*charge,
                1,
                lvl.i(0.5,1,powerLevel)*charge,
                0,
                0,
                0,
                0.004*charge);
        ParticleMaker.createSphere(
                particle,
                loc,
                1*charge,
                (int)(lvl.i(1,5,powerLevel)),
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
        blast.moveSelf(lvl.i(1,3,powerLevel), false,1);

    }

    private void setParticle(Player player, int powerLevel)
    {
        if(PlayerUtility.getPlayerMemory(player).isKing() || powerLevel >= 90)
            particle = Particle.SOUL_FIRE_FLAME;
        else particle = Particle.FLAME;
    }

}
