package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SamuraiBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class Sword extends CustomItem {
    public void useBasicAttack(Player player, int powerLevel, float swordDamage)
    {
        SamuraiBasicProj proj = new SamuraiBasicProj(player,2,2,swordDamage,this);
        proj.moveSelf(2,false);
    }
    public void useAriel(Player player, int powerLevel, float swordDamage)
    {
        Location loc = player.getEyeLocation();

        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createCircle(
                Particle.SWEEP_ATTACK,
                loc,
                lvl.i(1, 4, powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel));
        CombatManager.DamageNearby(player.getLocation(),lvl.i(1,4,powerLevel),3,lvl.i(1,4,powerLevel),swordDamage,player);
    }
    public void useArielDash(Player player, int powerLevel, float swordDamage)
    {
        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,100,2);
        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,100,0);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                Vector dir = player.getLocation().getDirection().clone().multiply(10);
                dir.setY(0.2f);
                player.setVelocity(dir);
                ParticleMaker.SpawnParticle(player.getEyeLocation(), Particle.SWEEP_ATTACK,4,.2f,.2f,.2f);
                for(LivingEntity e : player.getLocation().getNearbyLivingEntities(3,3,3))
                {
                    if(e.equals(player)) continue;
                    CombatManager.DamageEntity(swordDamage*lvl.i(0.9,1.4,powerLevel),e,player);
                }
                if(ticks >= lvl.i(0.1,0.5, powerLevel)*20) {
                    player.setVelocity(new Vector(0,0,0));
                }
                else {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), this, 1);
                }
            }
        },1);
    }
    public void chargeChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {
        player.sendMessage(("[ERROR] Charge Charged Attack Not Overloaded"));
    }
    public void useChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {
        player.sendMessage("[ERROR] Charged Attack Not Overloaded");
    }
    public void useQuickAttack(Player player, int powerLevel, float swordDamage)
    {
        List<LivingEntity> entitiesInCone = MathUtils.getEntitiesInCone(player.getLocation());

        CombatManager.ApplyPulse(player.getLocation(),lvl.i(0.1,2,(int)swordDamage)*lvl.i(1,2,powerLevel),1.0f, entitiesInCone,player);
        CombatManager.DamageEntity(swordDamage*lvl.i(1,1.75,powerLevel),entitiesInCone,player);
        //Play audio slightly in front of user
        ParticleMaker.SpawnParticle(player.getEyeLocation().add(player.getLocation().getDirection().multiply(2)),Particle.SWEEP_ATTACK,3,1,1,1);
        GeneralUtils.PlaySound(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(2)), Sound.ENTITY_PLAYER_ATTACK_SWEEP,2,2);

    }
    public void useGroundSlam(Player player, int powerLevel, float swordDamage)
    {
        player.setVelocity(new Vector(0,-20,0));
        Location loc = player.getLocation();

        loc.add(0,2,0);
        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createCircle(
                Particle.SWEEP_ATTACK,
                loc,
                lvl.i(1,5,powerLevel),
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,16,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));
        ParticleMaker.createCircle(
                Particle.SWEEP_ATTACK,
                loc,
                lvl.i(0.5,3.75,powerLevel),
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,16,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                10);
        ParticleMaker.createCircle(
                Particle.SWEEP_ATTACK,
                loc,
                lvl.i(0,2.5,powerLevel),
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,16,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                -10);
        GeneralUtils.PlaySound(player.getEyeLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP);
    }
    public void groundSlamFalling(Player player, int powerLevel, double charge, float swordDamage)
    {
        player.sendMessage("[ERROR] Ground Slam Fall Not Overloaded");
    }
    public void useGroundSlamLanding(Player player, int powerLevel, double charge, float swordDamage)
    {
        player.sendMessage("[ERROR] Ground Slam Landing Not Overloaded");
    }
}