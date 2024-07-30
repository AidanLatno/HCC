package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.projectiles.AirProjectiles.AirBasicAttackProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SamuraiChargeProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SWaterChargeProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles.WaterBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WaterSword extends Sword{
    public WaterSword()
    {
        name = ChatColor.BLUE + ChatColor.BOLD.toString() + "Water Sword";
        lore.add(ChatColor.DARK_RED + "A sword infused with water blood.");
        CustomModelData = 2;
        baseItem = Material.NETHERITE_SWORD;
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel, float swordDamage)
    {
        super.useBasicAttack(player,powerLevel,swordDamage);

        WaterBasicProj proj = new WaterBasicProj(player,2,2);
        proj.moveSelf(2,false);
    }
    @Override
    public void useQuickAttack(Player player, int powerLevel, float swordDamage)
    {
        super.useQuickAttack(player,powerLevel,swordDamage);
        List<LivingEntity> entitiesInCone = MathUtils.getEntitiesInCone(player.getLocation());
        Location loc = player.getEyeLocation().clone();

        loc.add(loc.getDirection().multiply(2));

        GeneralUtils.PlaySound(loc,Sound.ENTITY_AXOLOTL_SPLASH,2,2);
        ParticleMaker.SpawnParticle(loc,Particle.WATER_SPLASH,100,2,2,2);

        for(LivingEntity e : entitiesInCone)
        {
            if(e.equals(player)) continue;

            ParticleMaker.SpawnParticle(e.getLocation(),Particle.WATER_SPLASH,10,2,2,2);
        }
    }
    @Override
    public void useAriel(Player player, int powerLevel, float swordDamage)
    {
        super.useAriel(player,powerLevel,swordDamage);
        Location loc = player.getEyeLocation();

        loc.setPitch(90);
        loc.setYaw(0);


        ParticleMaker.createCircle(
                Particle.WATER_SPLASH,
                loc,
                lvl.i(1, 4, powerLevel),
                (int)lvl.i(2,4,powerLevel),
                lvl.i(2,16,powerLevel),
                1,.1,1);

        CombatManager.ApplyPulse(player.getLocation(),0.4f,0.2f,MathUtils.getEntitiesInSphere(player.getLocation(),(int)(lvl.i(2,6,powerLevel))),player);
    }
    @Override
    public void useArielDash(Player player, int powerLevel, float swordDamage)
    {
        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,100,2);
        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,100,0);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            ArrayList<LivingEntity> hitEnemies = new ArrayList<>();
            @Override
            public void run() {
                ticks++;
                Vector dir = player.getLocation().getDirection().clone().multiply(10);
                dir.setY(0.2f);
                player.setVelocity(player.getVelocity().add(dir));
                ParticleMaker.SpawnParticle(player.getEyeLocation(), Particle.WATER_SPLASH,4,.2f,.2f,.2f);
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
                        ParticleMaker.SpawnParticle(entity.getLocation(),Particle.BUBBLE_POP,500,2,2,2,1);
                        ParticleMaker.SpawnParticle(entity.getLocation(),Particle.WATER_SPLASH,500,2,2,2,1);
                    }
                }
                else {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), this, 1);
                }
            }
        },1);
    }
    @Override
    public void useGroundSlam(Player player, int powerLevel, float swordDamage)
    {
        super.useGroundSlam(player,powerLevel,swordDamage);
        ParticleMaker.createCircle(
                Particle.WATER_SPLASH,
                player.getLocation(),
                lvl.i(1,5,powerLevel),
                1,
                lvl.i(2,4,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge, float swordDamage)
    {
        super.groundSlamFalling(player,powerLevel,charge,swordDamage);
        Location loc = player.getEyeLocation().clone();

        loc.setYaw(loc.getYaw()+90);
        loc.setPitch(0);

        ParticleMaker.createCircle(
                Particle.WATER_SPLASH,
                loc,
                lvl.i(1.6,6,powerLevel)*charge,
                2,
                lvl.i(2,4,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge, float swordDamage)
    {
        super.useGroundSlamLanding(player,powerLevel,charge,swordDamage);

        ArrayList<Block> blocks = BlockUtil.getBlocksCircular(player.getLocation(),(int)(lvl.i(4,8,powerLevel)));
        BlockUtil.fillBlocks(blocks,Material.WATER, BlockUtil.ignoreList);
        for(Block block : blocks) BlockUtil.setBlockLevel(block,1);
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {
        super.chargeChargedAttack(player,powerLevel,charge,swordDamage);
        ParticleMaker.SpawnParticle(player.getEyeLocation(),Particle.WATER_SPLASH,(int)(10*charge),2.5f,2.5f,2.5f,1);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {
        if(charge >= 4) {
            GeneralUtils.PlaySound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 2);
            CombatManager.DamageNearby(player.getLocation(), lvl.i(4, 8, powerLevel), lvl.i(14, 20, powerLevel), lvl.i(4, 8, powerLevel), (float) (swordDamage * lvl.i(2.5, 4.5, powerLevel)), player);
            SWaterChargeProj proj = new SWaterChargeProj(player,2,swordDamage);
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

                SWaterChargeProj projLeft = new SWaterChargeProj(player,2,swordDamage);
                projLeft.setLocation(loc1);
                projLeft.moveSelf(3,false);
                SWaterChargeProj projRight = new SWaterChargeProj(player,2,swordDamage);
                projRight.setLocation(loc2);
                projRight.moveSelf(3,false);



            }
        } else GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_PHANTOM_FLAP,1,2);
    }
}
