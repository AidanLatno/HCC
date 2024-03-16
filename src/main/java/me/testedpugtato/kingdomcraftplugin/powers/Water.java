package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.barriers.Domain;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
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
        if(isInNether(player)) return;

        WaterBasicProj proj = new WaterBasicProj(player,MathUtils.levelInter(1,2,powerLevel),1);
        proj.moveSelf(2,false);
    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        if(isInNether(player)) return;

        List<LivingEntity> entitiesInCone = new ArrayList<>();
        Vector playerDirection = player.getLocation().getDirection();
        Vector playerLocation = player.getLocation().toVector();

        player.getWorld().spawnParticle(Particle.WATER_SPLASH,player.getLocation().clone().add(playerDirection),(int)MathUtils.levelInter(500,1500,powerLevel),1,1,1,0,null,true);

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

            p.getLocation().getWorld().spawnParticle(Particle.WATER_SPLASH,p.getLocation(),(int)MathUtils.levelInter(500,1500,powerLevel),1,1,1,0,null,true);

            Location playerCenterLocation = player.getEyeLocation();
            Location playerToThrowLocation = p.getEyeLocation();

            double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
            double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
            double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

            Vector throwVector = new Vector(x, y, z);

            throwVector.normalize();
            throwVector.multiply(MathUtils.levelInter(1,2,powerLevel));
            throwVector.setY(MathUtils.levelInter(0.4,1.3,powerLevel));

            p.setVelocity(throwVector);
        }
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

                if(ticks >= MathUtils.levelInter(8,30,powerLevel)) return;


                Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
            }
        },1);
    }
    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        if(isInNether(player)) return;

        WaterQuickProj proj = new WaterQuickProj(player,MathUtils.levelInter(2,3,powerLevel),1);
        proj.moveSelf(MathUtils.levelInter(0.3,1,powerLevel),true);
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
        if(isInNether(player)) return;

        player.getWorld().spawnParticle(Particle.WATER_SPLASH,player.getLocation(),100,1,1,1,1,null,true);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_AXOLOTL_SPLASH,10,0);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(isInNether(player)) return;

        if(charge > 1) charge = 1;


       player.getWorld().spawnParticle(Particle.SPIT,player.getLocation(),(int)(MathUtils.levelInter(500,4000,powerLevel)*charge), MathUtils.levelInter(5,10,powerLevel)*charge,1,MathUtils.levelInter(5,10,powerLevel)*charge,0,null,true);
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
            throwVector.multiply(MathUtils.levelInter(1,2,powerLevel)*charge);
            throwVector.setY(MathUtils.levelInter(0.4,1.3,powerLevel)*charge);

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
        if(isInNether(player)) return;

        if(charge > 6) charge = 6;
        charge /= 6;

        player.getWorld().spawnParticle(Particle.BUBBLE_POP,player.getLocation(),(int)(MathUtils.levelInter(100,1000, powerLevel)*charge), MathUtils.levelInter(5,10,powerLevel)*charge,0,MathUtils.levelInter(5,10,powerLevel)*charge,0,null, true);
        if(charge == 1) player.getWorld().playSound(player.getLocation(),Sound.ENTITY_AXOLOTL_SPLASH,SoundCategory.MASTER,0.5f,0);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {
        if(isInNether(player)) return;

        if(charge > 6) charge = 6;
        charge /= 6;

        int radius = (int)(MathUtils.levelInter(5,10,powerLevel)*charge);
        int trapTicks = (int)(MathUtils.levelInter(20,100,powerLevel)*charge);

        player.getWorld().spawnParticle(Particle.SPIT,player.getLocation(),(int)(MathUtils.levelInter(100,1000, powerLevel)*charge), radius,0,radius,0,null, true);

        Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(radius, 2, radius);

        for(LivingEntity entity : entities)
        {
            if(entity.equals(player)) continue;

            if(entity.getLocation().distance(player.getLocation()) <= radius)
            {
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
                        }

                        if(ticks < trapTicks)  Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
                    }
                },1);
            }
        }

        List<Block> blocks = new ArrayList<>();
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
        if(isInNether(player)) return;

        // Change to your domain
        Domain domain = new Domain(player,20, player.getLocation(),30,1,2,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }

    private boolean isInNether(Player player)
    {
        if(player.getWorld().getEnvironment().equals(World.Environment.NETHER))
        {
            player.getWorld().playSound(player.getLocation(),Sound.ENTITY_GENERIC_EXTINGUISH_FIRE,1,1);
            player.getWorld().spawnParticle(Particle.SMOKE_LARGE,player.getLocation().clone().add(player.getLocation().getDirection().clone().multiply(3)),4,.1,.1,.1,1,null,true);
            return true;
        }
        return false;
    }

}
