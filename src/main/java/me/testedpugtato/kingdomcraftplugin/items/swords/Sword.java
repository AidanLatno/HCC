package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SamuraiBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
        Location loc = player.getEyeLocation().clone();

        loc.add(0,1.5f,0);
        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createCircle(
                Particle.SWEEP_ATTACK,
                loc,
                lvl.i(2,6,powerLevel),
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,8,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel),
                10);

        ParticleMaker.createCircle(
                Particle.SWEEP_ATTACK,
                loc,
                lvl.i(1,3,powerLevel),
                (int)lvl.i(1,3,powerLevel),
                lvl.i(1,4,powerLevel),
                0,0,0,
                lvl.i(0,0.05,powerLevel),
                10);

        CombatManager.DamageNearby(player.getLocation(),lvl.i(2,6,powerLevel),3,lvl.i(2,6,powerLevel),swordDamage,player);
        GeneralUtils.PlaySound(loc,Sound.ENTITY_PLAYER_ATTACK_SWEEP,100,0);
        GeneralUtils.PlaySound(loc,Sound.ENTITY_PHANTOM_FLAP,1000,2);

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
                if(ticks >= lvl.i(0.1,0.35, powerLevel)*20) {
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
        if(charge > 6) charge = 6;
        charge /= 6;
        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,0.7f);
        ParticleMaker.createSphere(
                Particle.SWEEP_ATTACK,
                player.getEyeLocation(),
                2.5f,
                1,
                1
        );
        PotionEffect effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,1,1,false,false,true);
        player.addPotionEffect(effect);
    }
    public void useChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {
        if(charge >= 6) {

            GeneralUtils.PlaySound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 2);
            CombatManager.DamageNearby(player.getLocation(), lvl.i(4, 8, powerLevel), lvl.i(14, 20, powerLevel), lvl.i(4, 8, powerLevel), (float) (swordDamage * lvl.i(2.5, 4.5, powerLevel) * charge), player);
        } else GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_PHANTOM_FLAP,1,2);
    }
    public void useQuickAttack(Player player, int powerLevel, float swordDamage)
    {
        List<LivingEntity> entitiesInCone = MathUtils.getEntitiesInCone(player.getLocation());

        CombatManager.ApplyPulse(player.getLocation(),lvl.i(1,3,(int)swordDamage)*lvl.i(1,2,powerLevel),1.0f, entitiesInCone,player);
        CombatManager.DamageEntity(swordDamage*lvl.i(1,1.75,powerLevel),entitiesInCone,player);
        //Play audio slightly in front of user
        ParticleMaker.SpawnParticle(player.getEyeLocation().add(player.getLocation().getDirection().multiply(2)),Particle.SWEEP_ATTACK,3,1,1,1);
        GeneralUtils.PlaySound(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(2)), Sound.ENTITY_PLAYER_ATTACK_SWEEP,2,2);

    }
    public void useGroundSlam(Player player, int powerLevel, float swordDamage)
    {
        player.setVelocity(new Vector(0,-20,0));
        Location loc = player.getLocation();

        ParticleMaker.createCircle(
                Particle.CLOUD,
                loc,
                lvl.i(1,5,powerLevel),
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,16,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));

        GeneralUtils.PlaySound(player.getEyeLocation(),Sound.ENTITY_GENERIC_EXPLODE);
    }
    public void groundSlamFalling(Player player, int powerLevel, double charge, float swordDamage)
    {
        if(charge > 0.3) charge = 0.3;
        charge /= 0.3;


        Location loc = player.getEyeLocation().clone();

        loc.setYaw(loc.getYaw()+90);
        loc.setPitch(0);

        ParticleMaker.createCircle(
                Particle.SWEEP_ATTACK,
                loc,
                lvl.i(1.6,6,powerLevel)*charge,
                1,
                lvl.i(2,4,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));

        GeneralUtils.PlaySound(player.getEyeLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,10);
    }
    public void useGroundSlamLanding(Player player, int powerLevel, double charge, float swordDamage)
    {
        if(charge > 0.3) charge = 0.3;
        charge /= 0.3;

        GeneralUtils.PlaySound(player.getLocation(),Sound.BLOCK_ANVIL_LAND,10,2);
        GeneralUtils.PlaySound(player.getLocation(),Sound.BLOCK_ANVIL_LAND,10,0);
        CombatManager.DamageNearby(player.getLocation(), lvl.i(4,8,powerLevel), lvl.i(14,20,powerLevel), lvl.i(4,8,powerLevel), (float)(swordDamage*lvl.i(2,3.5,powerLevel)*charge),player);

    }
}