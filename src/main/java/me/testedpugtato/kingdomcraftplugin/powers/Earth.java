package me.testedpugtato.kingdomcraftplugin.powers;


import me.testedpugtato.kingdomcraftplugin.barriers.EarthDomain;
import me.testedpugtato.kingdomcraftplugin.util.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Earth extends Power
{
    public static Set<Material> blockList = new HashSet<>();
    public static Set<Material> ignoreListSet = new HashSet<>();

    public Earth()
    {
        setBasicCooldown(3);
        setDashCooldown(5);
        setQuickCooldown(1);
        setArielCooldown(7);
        setChargedCooldown(15);
        setSlamCooldown(15);
        id = "earth";

        Material[] set = {
                Material.GRASS_BLOCK,
                Material.DIRT,
                Material.SAND,
                Material.STONE,
                Material.COBBLESTONE,
                Material.DEEPSLATE,
                Material.COBBLED_DEEPSLATE,
                Material.GRAVEL,
                Material.ANDESITE,
                Material.GRANITE,
                Material.DIORITE,
                Material.COARSE_DIRT,
                Material.FARMLAND,
                Material.DIRT_PATH
        };

        blockList.addAll(Arrays.asList(set));
        ignoreListSet.addAll(Arrays.asList(BlockUtil.ignoreList));
    }
    @Override
    public void useBasicAttack(Player player, int powerLevel)
    {

    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        Block block = player.getTargetBlock(ignoreListSet,3);
        BlockData data = block.getBlockData();

        player.getWorld().spawnFallingBlock(block.getLocation().add(0,3,0),data);
    }
    @Override
    public void useArielDash(Player player, int powerLevel)
    {

    }
    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {

    }
    @Override
    public void useGroundSlam(Player player, int powerLevel)
    {

    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {

    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {

    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge)
    {

    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {

    }

    @Override
    public void domainExpand(Player player)
    {
        EarthDomain domain = new EarthDomain(player,20, player.getLocation(),30,1,2,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }
}
