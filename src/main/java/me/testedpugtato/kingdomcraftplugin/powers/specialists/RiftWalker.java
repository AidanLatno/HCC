package me.testedpugtato.kingdomcraftplugin.powers.specialists;

import me.testedpugtato.kingdomcraftplugin.barriers.Domain;
import me.testedpugtato.kingdomcraftplugin.barriers.RiftWalkerDomain;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.powers.Power;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class RiftWalker extends Power {
    public RiftWalker()
    {
        setBasicCooldown(3);
        setDashCooldown(5);
        setQuickCooldown(1);
        setArielCooldown(7);
        setChargedCooldown(15);
        setSlamCooldown(15);
        id = "riftwalker";
    }
    @Override
    public void useBasicAttack(Player player, int powerLevel)
    {

    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {

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
        RiftWalkerDomain domain = new RiftWalkerDomain(player,20, player.getLocation(),30,1,1,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }
}
