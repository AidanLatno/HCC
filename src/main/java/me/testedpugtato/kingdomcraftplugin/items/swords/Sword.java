package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SamuraiQuickProj;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Sword extends CustomItem {
    public void useBasicAttack(Player player, int powerLevel, float swordDamage)
    {
        SamuraiQuickProj proj = new SamuraiQuickProj(player,2,2,swordDamage);
        proj.moveSelf(2,false);
    }
    public void useAriel(Player player, int playerLevel, float swordDamage)
    {
        /*for(int i = 0; i < 32; i++) {
            SamuraiQuickProj proj = new SamuraiQuickProj(player, 2, 2, swordDamage);
            proj.setLocation(proj.getLocation().setDirection());
        }*/
    }
    public void useArielDash(Player player, int powerLevel, float swordDamage)
    {
        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,SoundCategory.MASTER,100,0);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                Vector dir = player.getLocation().getDirection().clone().multiply(10);
                dir.setY(0.2f);
                player.setVelocity(dir);
                player.getWorld().spawnParticle(Particle.SWEEP_ATTACK,player.getEyeLocation(),4,.2,.2,.2,0, null, true);
                for(LivingEntity e : player.getLocation().getNearbyLivingEntities(3,3,3))
                {
                    if(e.equals(player)) continue;
                    CombatManager.DamageEntity(swordDamage*MathUtils.levelInter(0.9,1.4,powerLevel),e,player);
                }
                if(ticks >= MathUtils.levelInter(0.1,0.5, powerLevel)*20) {
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

        for(LivingEntity p : entitiesInCone)
        {
            if(p.equals(player))
                continue;

            p.getLocation().getWorld().spawnParticle(Particle.SWEEP_ATTACK,p.getLocation(),3,1,1,1,0,null,true);

            Location playerCenterLocation = player.getEyeLocation();
            Location playerToThrowLocation = p.getEyeLocation();

            double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
            double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
            double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

            Vector throwVector = new Vector(x, y, z);

            throwVector.normalize();
            throwVector.multiply(MathUtils.levelInter(0.1,2,(int)swordDamage)*MathUtils.levelInter(1,2,powerLevel));
            throwVector.setY(1.0);

            p.setVelocity(throwVector);
            CombatManager.DamageEntity(swordDamage*MathUtils.levelInter(1,1.75,powerLevel),p,player);
        }

        //Play audio slightly in front of user
        player.getLocation().getWorld().spawnParticle(Particle.SWEEP_ATTACK,player.getEyeLocation().add(player.getLocation().getDirection().multiply(2)),3,1,1,1,0,null,true);
        player.getWorld().playSound(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(2)), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER,2,2);

    }
    public void useGroundSlam(Player player, int powerLevel, float swordDamage)
    {
        player.sendMessage("[ERROR] Ground Slam Not Overloaded");
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