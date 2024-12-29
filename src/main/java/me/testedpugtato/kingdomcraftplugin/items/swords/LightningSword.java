package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.projectiles.LightningProjectiles.LightningBasicProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SLightningChargeProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SWaterChargeProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SamuraiBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LightningSword extends Sword{
    public LightningSword()
    {
        name = ChatColor.YELLOW + ChatColor.BOLD.toString() + "Lightning Sword";
        lore.add(ChatColor.DARK_RED + "A sword infused with lightning blood.");
        CustomModelData = 3;
        baseItem = Material.NETHERITE_SWORD;
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel, float swordDamage)
    {
        SamuraiBasicProj proj = new SamuraiBasicProj(player,6,5,swordDamage,this);
        proj.moveSelf(0.3f,false);



        LightningBasicProj ZapSwordProj = new LightningBasicProj(player, 6, 5);
        ZapSwordProj.moveSelf(0.3f, false);

    }

    @Override
    public void useAriel(Player player, int powerLevel, float swordDamage)
    {
        super.useAriel(player,powerLevel,swordDamage);
        Location loc = player.getEyeLocation();

        loc.setPitch(90);
        loc.setYaw(0);


        ParticleMaker.createCircle(
                Particle.SCRAPE,
                loc,
                lvl.i(1, 4, powerLevel),
                (int)lvl.i(1,6,powerLevel),
                lvl.i(2,16,powerLevel),
                1, 0.1,1);
        CombatManager.DamageNearby(player.getLocation(),lvl.i(1,4,powerLevel),3,lvl.i(1,4,powerLevel),swordDamage+4,player);
        for(LivingEntity e : MathUtils.getEntitiesInSphere(player.getLocation(),(int)(lvl.i(1, 4, powerLevel))))
        {
            if(e instanceof Player && !e.equals(player))
                PlayerUtility.getPlayerMemory((Player)e).stun(40);
        }
    }
    @Override
    public void useArielDash(Player player, int powerLevel, float swordDamage)
    {
        GeneralUtils.PlaySound(player.getLocation(),Sound.BLOCK_GRAVEL_FALL,100,2);
        GeneralUtils.PlaySound(player.getLocation(),Sound.BLOCK_BEEHIVE_WORK,100,0);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            ArrayList<LivingEntity> hitEnemies = new ArrayList<>();
            @Override
            public void run() {
                ticks++;
                Vector dir = player.getLocation().getDirection().clone().multiply(10);
                dir.setY(0.2f);
                player.setVelocity(dir);
                ParticleMaker.SpawnParticle(player.getEyeLocation(), Particle.SCRAPE,4,.2f,.2f,.2f);
                for(LivingEntity e : player.getLocation().getNearbyLivingEntities(3,3,3))
                {
                    if(e.equals(player)) continue;
                    CombatManager.DamageEntity(swordDamage*lvl.i(0.9,1.4,powerLevel),e,player);
                    hitEnemies.add(e);
                }
                if(ticks >= lvl.i(0.1,0.35, powerLevel)*20) {
                    player.setVelocity(new Vector(0,0,0));
                    for(LivingEntity entity : hitEnemies)
                    {
                        player.getWorld().strikeLightningEffect(entity.getLocation());
                    }
                }
                else {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), this, 1);
                }
            }
        },1);

    }
    @Override
    public void useQuickAttack(Player player, int powerLevel, float swordDamage)
    {
        super.useQuickAttack(player,powerLevel,swordDamage);
        List<LivingEntity> entitiesInCone = MathUtils.getEntitiesInCone(player.getLocation());
        Location loc = player.getEyeLocation().clone();

        loc.add(loc.getDirection().multiply(2));

        ParticleMaker.SpawnParticle(loc, Particle.SCRAPE,100,2,2,2);

        for(LivingEntity e : entitiesInCone)
        {
            if(e.equals(player)) continue;

            e.getWorld().strikeLightningEffect(e.getLocation());
            ParticleMaker.SpawnParticle(e.getLocation(),Particle.SCRAPE,5,2,2,2);
        }
    }
    @Override
    public void useGroundSlam(Player player, int powerLevel, float swordDamage)
    {
        super.useGroundSlam(player,powerLevel,swordDamage);

        ParticleMaker.createCircle(
                Particle.SCRAPE,
                player.getLocation(),
                lvl.i(1,5,powerLevel),
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,16,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge, float swordDamage)
    {
        super.groundSlamFalling(player,powerLevel,charge,swordDamage);
        if(charge > 0.3) charge = 0.3;
        charge /= 0.3;


        Location loc = player.getEyeLocation().clone();

        loc.setYaw(loc.getYaw()+90);
        loc.setPitch(0);

        ParticleMaker.createCircle(
                Particle.SCRAPE,
                loc,
                lvl.i(1.6,6,powerLevel)*charge,
                1,
                lvl.i(2,4,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));

        player.getWorld().strikeLightningEffect(player.getLocation());

    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge, float swordDamage)
    {
        super.useGroundSlamLanding(player,powerLevel,charge,swordDamage);

        for(LivingEntity e : MathUtils.getEntitiesInSphere(player.getLocation(),(int)(lvl.i(4, 8, powerLevel))))
        {
            if(e instanceof Player && !e.equals(player))
                PlayerUtility.getPlayerMemory((Player)e).stun(40);
        }
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {
        super.chargeChargedAttack(player,powerLevel,charge,swordDamage);
        ParticleMaker.SpawnParticle(player.getEyeLocation(),Particle.SCRAPE,(int)(5*charge),2.5f,2.5f,2.5f,1);
        GeneralUtils.PlaySound(player.getLocation(),Sound.BLOCK_BEEHIVE_WORK,0.1f,0.7f);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {
        if(charge >= 4) {
            GeneralUtils.PlaySound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 2);
            CombatManager.DamageNearby(player.getLocation(), lvl.i(4, 8, powerLevel), lvl.i(14, 20, powerLevel), lvl.i(4, 8, powerLevel), (float) (swordDamage * lvl.i(2.5, 4.5, powerLevel)), player);
            SLightningChargeProj proj = new SLightningChargeProj(player,2,swordDamage);
            proj.moveSelf(3,false);

            Vector left = MathUtils.rotateAroundY(player.getEyeLocation().getDirection(),90);
            Vector right = MathUtils.rotateAroundY(player.getEyeLocation().getDirection(),-90);
            right.setY(0);
            left.setY(0);

            for(int i = 1; i <= 6; i++)
            {
                left.normalize();
                right.normalize();
                left.multiply(i);
                right.multiply(i);
                Location loc1 = player.getEyeLocation().clone().add(left);
                Location loc2 = player.getEyeLocation().clone().add(right);

                SLightningChargeProj projLeft = new SLightningChargeProj(player,2,swordDamage);
                projLeft.setLocation(loc1);
                projLeft.moveSelf(3,false);
                SLightningChargeProj projRight = new SLightningChargeProj(player,2,swordDamage);
                projRight.setLocation(loc2);
                projRight.moveSelf(3,false);



            }
        } else GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_PHANTOM_FLAP,1,2);
    }
}
