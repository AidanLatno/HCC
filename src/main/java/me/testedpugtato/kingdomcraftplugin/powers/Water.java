package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.barriers.Domain;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles.WaterBasicProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles.WaterQuickProj;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Water extends Power
{
    public Water()
    {
        setBasicCooldown(3);
        setDashCooldown(5);
        setQuickCooldown(0.1f);
        setArielCooldown(3);
        setChargedCooldown(15);
        setSlamCooldown(15);
        id = "water";
    }
    @Override
    public void useBasicAttack(Player player, int powerLevel)
    {
        if(isInNether(player)) return;

        WaterBasicProj proj = new WaterBasicProj(player, lvl.i(1,2,powerLevel),1);
        proj.moveSelf(2,false);
    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        if(isInNether(player)) return;

        List<LivingEntity> entitiesInCone = MathUtils.getEntitiesInCone(player.getLocation());

        ParticleMaker.SpawnParticle(player.getLocation().clone().add(player.getLocation().getDirection()),
                Particle.WATER_SPLASH,
                (int)lvl.i(500,1500,powerLevel),
                1,1,1);

        CombatManager.ApplyPulse(player.getLocation(),
                lvl.i(1,2,powerLevel),
                lvl.i(0.4,1.3,powerLevel),
                entitiesInCone,
                player);
    }
    @Override
    public void useArielDash(Player player, int powerLevel)
    {
        if(isInNether(player)) return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks += 1;

                Block block = player.getLocation().getBlock();

                block.setType(Material.WATER,true);

                BlockUtil.setBlockLevel(block,1);

                Vector vec = player.getLocation().getDirection();
                vec.setY(0.1);
                player.setVelocity(vec);

                if(ticks >= lvl.i(8,30,powerLevel)) return;


                Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
            }
        },1);
    }
    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        if(isInNether(player)) return;

        WaterQuickProj proj = new WaterQuickProj(player,lvl.i(2,3,powerLevel),1);
        proj.moveSelf(lvl.i(0.3,1,powerLevel),true);
    }
    @Override
    public void useGroundSlam(Player player, int powerLevel)
    {
        if(isInNether(player)) return;

        player.setVelocity(new Vector(0,-20,0));
        Location loc = player.getLocation();

        loc.add(0,2,0);
        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createCircle(
                Particle.WATER_SPLASH,
                loc,
                lvl.i(2,10,powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.5,powerLevel),
                lvl.i(0,0.05,powerLevel));

        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_AXOLOTL_SPLASH,10,0);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {
        if(isInNether(player)) return;

        ParticleMaker.SpawnParticle(player.getLocation(), Particle.WATER_SPLASH,100,1,1,1,1);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_AXOLOTL_SPLASH,10,0);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(isInNether(player)) return;

        if(charge > .6) charge = .6;
        charge /= .6;


        ParticleMaker.SpawnParticle(
                player.getLocation(),
                Particle.SPIT,
                (int)(lvl.i(500,4000,powerLevel)*charge),
                (float)(lvl.i(5,10,powerLevel)*charge),
                1,
                (float)(lvl.i(5,10,powerLevel)*charge));

        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_AXOLOTL_SPLASH,100,0);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_DOLPHIN_SPLASH,100,2);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ENTITY_PLAYER_SPLASH_HIGH_SPEED,100,1);

        CombatManager.DamageNearby(
                player.getLocation(),
                lvl.i(7,10,powerLevel),
                3,
                lvl.i(7,10,powerLevel),
                (int)(lvl.i(5,18,powerLevel)*charge),
                player);

        Vector boundingBox = new Vector(
                lvl.i(7,10,powerLevel),
                3,
                lvl.i(7,10,powerLevel));

        CombatManager.ApplyPulse(player.getLocation(), (float) (lvl.i(1,2,powerLevel)*charge), (float) (lvl.i(0.4,1.3,powerLevel)*charge), boundingBox, player);

        // Get blocks in a circular shape and fill them with water
        ArrayList<Block> blocks = BlockUtil.getBlocksCircular(player.getLocation(),(int)lvl.i(5,10,powerLevel));

        BlockUtil.fillBlocks(blocks,Material.WATER,BlockUtil.ignoreList);
        for(Block block : blocks)
            BlockUtil.setBlockLevel(block,1);

    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge)
    {
        if(isInNether(player)) return;

        if(charge > 4) charge = 4;
        charge /= 4;

        ParticleMaker.SpawnParticle(player.getLocation(), Particle.BUBBLE_POP, (int)(lvl.i(100,1000, powerLevel)*charge), (float)(lvl.i(5,10,powerLevel)*charge),0,(float)(lvl.i(5,10,powerLevel)*charge));
        if(charge == 1) GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_AXOLOTL_SPLASH,0.5f,0);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {
        if(isInNether(player)) return;

        if(charge > 4) charge = 4;
        charge /= 4;

        int radius = (int)(lvl.i(5,10,powerLevel)*charge);
        int trapTicks = (int)(lvl.i(20,100,powerLevel)*charge);

        ParticleMaker.SpawnParticle(player.getLocation(),Particle.SPIT,(int)(lvl.i(100,1000, powerLevel)*charge), radius,0,radius);

        List<LivingEntity> entities = MathUtils.getEntitiesInSphere(player.getLocation(),radius,new Vector(radius,2,radius));

        for(LivingEntity entity : entities)
        {
            if(entity.equals(player)) continue;

            if(entity instanceof Player) PlayerUtility.getPlayerMemory((Player) entity).stun(trapTicks);

            Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
                int ticks = 0;
                @Override
                public void run() {
                    ticks++;

                    entity.setVelocity(new Vector(0,0,0));

                    Location pos1 = entity.getLocation().clone(); // Bottom corner
                    Location pos2 = entity.getLocation().clone(); // Top corner

                    pos1.subtract(1,1,1);
                    pos2.add(1,2,1);

                    for(int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++)
                    {
                        for(int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++)
                        {
                            for(int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++)
                            {
                                Block block = entity.getWorld().getBlockAt(x,y,z);
                                if(block.getType() != Material.AIR) continue;
                                block.setType(Material.WATER,true);

                                BlockUtil.setBlockLevel(block,1);
                            }
                        }
                    }

                    if(ticks < trapTicks)  Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
                }
            },1);

        }

        ArrayList<Block> blocks = BlockUtil.getBlocksCircular(player.getLocation(),radius);

        BlockUtil.fillBlocks(blocks,Material.WATER,BlockUtil.ignoreList);
        for(Block block : blocks)
            BlockUtil.setBlockLevel(block,1);
    }

    @Override
    public void domainExpand(Player player)
    {
        if(isInNether(player)) return;

        // Change to your domain
        Domain domain = new Domain(player,20, player.getLocation(),30,1,2,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }

    private boolean isInNether(Player player)
    {
        if(player.getWorld().getEnvironment().equals(World.Environment.NETHER))
        {
            GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_GENERIC_EXTINGUISH_FIRE);
            ParticleMaker.SpawnParticle(player.getLocation().clone().add(player.getLocation().getDirection().clone().multiply(3)),Particle.SMOKE_LARGE,4,.1f,.1f,.1f,1);
            return true;
        }
        return false;
    }

}
