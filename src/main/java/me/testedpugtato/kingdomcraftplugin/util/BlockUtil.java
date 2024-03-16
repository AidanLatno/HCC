package me.testedpugtato.kingdomcraftplugin.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;

public class BlockUtil {
    public static void fill(Location pos1, Location pos2, Material filler, Material ignore)
    {
        int higherX = pos1.getBlockX() > pos2.getBlockX() ? pos1.getBlockX() : pos2.getBlockX();
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
                    if(block.getType() != ignore) continue;
                    block.setType(filler,true);
                }
            }
        }
    }
}
