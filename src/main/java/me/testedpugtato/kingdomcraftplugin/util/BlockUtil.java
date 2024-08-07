package me.testedpugtato.kingdomcraftplugin.util;

import me.testedpugtato.kingdomcraftplugin.powers.Power;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {
    public static void fill(Location pos1, Location pos2, Material filler, Material ignore)
    {
        int higherX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int lowerX = pos1.getBlockX() < pos2.getBlockX() ? pos1.getBlockX() : pos2.getBlockX();

        int higherY = pos1.getBlockY() > pos2.getBlockY() ? pos1.getBlockY() : pos2.getBlockY();
        int lowerY = pos1.getBlockY() < pos2.getBlockY() ? pos1.getBlockY() : pos2.getBlockY();

        int higherZ = pos1.getBlockZ() > pos2.getBlockZ() ? pos1.getBlockZ() : pos2.getBlockZ();
        int lowerZ = pos1.getBlockZ() < pos2.getBlockZ() ? pos1.getBlockZ() : pos2.getBlockZ();


        for(int x = lowerX; x <= higherX; x++)
        {
            for(int z = lowerZ; z <= higherZ; z++)
            {
                for(int y = lowerY; y <= higherY; y++)
                {
                    Block block = pos1.getWorld().getBlockAt(x,y,z);
                    if(block.getType() != ignore)
                        block.setType(filler,true);
                }
            }
        }
    }
    public static ArrayList<Block> getBlocksCircular(Location center, int radius){
        ArrayList<Block> blocks = new ArrayList<>();
        World world = center.getWorld();
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++)
            {
                if (x*x + z*z <= radius*radius) { // Check if the location is within the circle
                    blocks.add(world.getBlockAt(centerX + x, centerY, centerZ + z));
                }
            }
        }
        return blocks;
    }
    public static void fillBlocks(ArrayList<Block> blocksToFill,Material filledWith,Material[] replaceList)
    {
        for(Block block : blocksToFill)
        {
            for(Material replaced : replaceList)
            {
                if (block.getType().equals(replaced)) {
                    block.setType(filledWith,true);
                    break;
                }
            }

            BlockUtil.setBlockLevel(block,1);
        }
    }

    public static boolean isColliding(Location loc)
    {

        for(Material m : ignoreList)
        {
            if(!loc.getWorld().getBlockAt(loc).getType().equals(m))
                return true;
        }
        return false;

    }
    public static void setBlockLevel(Block block, int level)
    {
        BlockData blockData = block.getBlockData();

        // Check if the BlockData is an instance of Levelled (water, lava)
        if (blockData instanceof Levelled) {
            Levelled levelled = (Levelled) blockData;

            // Set the water level to 1
            levelled.setLevel(level);

            // Apply the modified BlockData back to the block
            block.setBlockData(levelled);
        }
    }


    public static Material[] ignoreList = {
            Material.LIGHT,
            Material.AIR,
            Material.GRASS,
            Material.TALL_GRASS,
            Material.SEAGRASS,
            Material.TALL_SEAGRASS,
            Material.SEA_PICKLE,
            Material.KELP_PLANT,
            Material.OXEYE_DAISY,
            Material.DANDELION,
            Material.ROSE_BUSH,
            Material.POPPY,
            Material.AZURE_BLUET,
            Material.CORNFLOWER,
            Material.SUNFLOWER,
            Material.BAMBOO,
            Material.SMALL_DRIPLEAF,
            Material.BIG_DRIPLEAF,
            Material.BIG_DRIPLEAF_STEM,
            Material.COBWEB,
            Material.FIRE,
            Material.SOUL_FIRE,
            Material.BLUE_ORCHID,
            Material.ALLIUM,
            Material.PEONY,
            Material.ORANGE_TULIP,
            Material.PINK_TULIP,
            Material.RED_TULIP,
            Material.WHITE_TULIP,
            Material.LILAC,
            Material.LILY_OF_THE_VALLEY,
            Material.LILY_PAD,
            Material.WITHER_ROSE,
            Material.TORCH,
            Material.SOUL_TORCH,
            Material.LARGE_FERN,
            Material.RED_CARPET,
            Material.ORANGE_CARPET,
            Material.YELLOW_CARPET,
            Material.LIME_CARPET,
            Material.GREEN_CARPET,
            Material.BLUE_CARPET,
            Material.CYAN_CARPET,
            Material.LIGHT_BLUE_CARPET,
            Material.PINK_CARPET,
            Material.MAGENTA_CARPET,
            Material.PURPLE_CARPET,
            Material.BLACK_CARPET,
            Material.GRAY_CARPET,
            Material.LIGHT_GRAY_CARPET,
            Material.WHITE_CARPET,
            Material.BROWN_CARPET,
            Material.OAK_SIGN,
            Material.DARK_OAK_SIGN,
            Material.SPRUCE_SIGN,
            Material.BIRCH_SIGN,
            Material.ACACIA_SIGN,
            Material.MANGROVE_SIGN,
            Material.WARPED_SIGN,
            Material.CRIMSON_SIGN,
            Material.BAMBOO_SAPLING,
            Material.STRING,
            Material.SCAFFOLDING,

    };
}
