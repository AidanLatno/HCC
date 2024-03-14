package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.barriers.Domain;
import me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles.WaterBasicProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles.WaterQuickProj;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
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
        WaterBasicProj proj = new WaterBasicProj(player,2,1);
        proj.moveSelf(2,false);
    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        List<LivingEntity> entitiesInCone = new ArrayList<>();
        Vector playerDirection = player.getLocation().getDirection();
        Vector playerLocation = player.getLocation().toVector();

        for (LivingEntity target : player.getLocation().getNearbyLivingEntities(10,10,10)) {
            // Don't include the player themselves
            if (target.equals(player)) {
                continue;
            }

            Vector targetLocation = target.getLocation().toVector();
            Vector directionToTarget = targetLocation.subtract(playerLocation);

            // Calculate the angle between the player's direction and the direction to the target
            double angleToTarget = playerDirection.angle(directionToTarget);

            // Convert the angle parameter to radians for comparison
            double angleRadians = Math.toRadians(100);

            // Check if the target is within the distance and the angle
            if (directionToTarget.length() <= 5 && angleToTarget <= angleRadians) {
                entitiesInCone.add(target);
            }
        }

        for(LivingEntity p : entitiesInCone)
        {
            if(p.equals(player))
                continue;

            p.getLocation().getWorld().spawnParticle(Particle.WATER_SPLASH,p.getLocation(),1000,1,1,1,0,null,true);

            Location playerCenterLocation = player.getEyeLocation();
            Location playerToThrowLocation = p.getEyeLocation();

            double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
            double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
            double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

            Vector throwVector = new Vector(x, y, z);

            throwVector.normalize();
            throwVector.multiply(1.5);
            throwVector.setY(1.0);

            p.setVelocity(throwVector);
        }
    }
    @Override
    public void useArielDash(Player player, int powerLevel)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks += 1;

                Block block = player.getLocation().getBlock();

                if(block == null) return;

                block.setType(Material.WATER,true);

                BlockData blockData = block.getBlockData();

                // Check if the BlockData is an instance of Levelled (water, lava)
                if (blockData instanceof Levelled) {
                    Levelled levelled = (Levelled) blockData;

                    // Set the water level to 1
                    levelled.setLevel(1);

                    // Apply the modified BlockData back to the block
                    block.setBlockData(levelled);
                }

                Vector vec = player.getLocation().getDirection();
                vec.setY(0.1);
                player.setVelocity(vec);

                if(ticks >= 20) return;


                Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
            }
        },1);
    }
    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        WaterQuickProj proj = new WaterQuickProj(player,3,1);
        proj.moveSelf(1,true);
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
                Particle.WATER_SPLASH,
                loc,
                MathUtils.levelInter(2,10,powerLevel),
                (int)MathUtils.levelInter(1,5,powerLevel),
                MathUtils.levelInter(2,32,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.5,powerLevel),
                MathUtils.levelInter(0,0.05,powerLevel));

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_AXOLOTL_SPLASH,10,0);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {
        player.getWorld().spawnParticle(Particle.WATER_SPLASH,player.getLocation(),100,1,1,1,1,null,true);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_AXOLOTL_SPLASH,10,0);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(charge > 2) charge = 2;
        charge /= 2;


       player.getWorld().spawnParticle(Particle.SPIT,player.getLocation(),(int)MathUtils.levelInter(500,4000,powerLevel), MathUtils.levelInter(5,10,powerLevel),1,MathUtils.levelInter(5,10,powerLevel),0,null,true);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_AXOLOTL_SPLASH, SoundCategory.MASTER,100,0);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_SPLASH, SoundCategory.MASTER,100,2);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_SPLASH_HIGH_SPEED, SoundCategory.MASTER,100,1);



        CombatManager.DamageNearby(player.getLocation(),MathUtils.levelInter(7,10,powerLevel),3,MathUtils.levelInter(7,10,powerLevel),(int)(MathUtils.levelInter(5,18,powerLevel)*charge),player);

        Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(MathUtils.levelInter(7,10,powerLevel),3,MathUtils.levelInter(7,10,powerLevel));

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

        List<Block> blocks = new ArrayList<>();
        int radius = (int)MathUtils.levelInter(5,10,powerLevel);
        Location center = player.getLocation(); // Center of the circle
        World world = center.getWorld();
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x*x + z*z <= radius*radius) { // Check if the location is within the circle
                    blocks.add(world.getBlockAt(centerX + x, centerY, centerZ + z));
                }
            }
        }

        for(Block block : blocks)
        {
            if(block.getType() != Material.AIR) continue;
            block.setType(Material.WATER,true);

            BlockData blockData = block.getBlockData();

            // Check if the BlockData is an instance of Levelled (water, lava)
            if (blockData instanceof Levelled) {
                Levelled levelled = (Levelled) blockData;

                // Set the water level to 1
                levelled.setLevel(1);

                // Apply the modified BlockData back to the block
                block.setBlockData(levelled);
            }
        }

        Collection<LivingEntity> list = center.getNearbyLivingEntities(radius,radius,radius);
        for(Entity entity : list)
        {
            if(entity.getLocation().distance(center) <= radius && entity != player) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
                    int ticks = 0;
                    @Override
                    public void run() {
                        ticks++;
                        entity.setVelocity(new Vector(0,0,0));



                        if(ticks >= (int)MathUtils.levelInter(5,10,powerLevel)) Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
                    }
                },1);
            }
        }
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge)
    {
        if(charge > 6) charge = 6;
        charge /= 6;

        player.getWorld().spawnParticle(Particle.BUBBLE_POP,player.getLocation(),(int)(MathUtils.levelInter(100,1000, powerLevel)*charge), MathUtils.levelInter(5,10,powerLevel)*charge,0,MathUtils.levelInter(5,10,powerLevel)*charge,0,null, true);
        if(charge == 1) player.getWorld().playSound(player.getLocation(),Sound.ENTITY_AXOLOTL_SPLASH,SoundCategory.MASTER,0.5f,0);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {
        if(charge > 6) charge = 6;
        charge /= 6;


        player.getWorld().spawnParticle(Particle.SPIT,player.getLocation(),(int)(MathUtils.levelInter(100,1000, powerLevel)*charge), MathUtils.levelInter(5,10,powerLevel)*charge,0,MathUtils.levelInter(5,10,powerLevel)*charge,0,null, true);


        List<Block> blocks = new ArrayList<>();
        int radius = (int)(MathUtils.levelInter(5,10,powerLevel)*charge);
        Location center = player.getLocation(); // Center of the circle
        World world = center.getWorld();
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x*x + z*z <= radius*radius) { // Check if the location is within the circle
                    blocks.add(world.getBlockAt(centerX + x, centerY, centerZ + z));
                }
            }
        }

        for(Block block : blocks)
        {
            if(block.getType() != Material.AIR) continue;
            block.setType(Material.WATER,true);

            BlockData blockData = block.getBlockData();

            // Check if the BlockData is an instance of Levelled (water, lava)
            if (blockData instanceof Levelled) {
                Levelled levelled = (Levelled) blockData;

                // Set the water level to 1
                levelled.setLevel(1);

                // Apply the modified BlockData back to the block
                block.setBlockData(levelled);
            }
        }
    }

    @Override
    public void domainExpand(Player player)
    {
        // Change to your domain
        Domain domain = new Domain(player,20, player.getLocation(),30,1,2,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }

}
