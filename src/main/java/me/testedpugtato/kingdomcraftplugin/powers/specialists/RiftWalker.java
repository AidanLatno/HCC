package me.testedpugtato.kingdomcraftplugin.powers.specialists;

import me.testedpugtato.kingdomcraftplugin.barriers.RiftWalkerDomain;
import me.testedpugtato.kingdomcraftplugin.items.swords.FireSword;
import me.testedpugtato.kingdomcraftplugin.powers.Power;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SamuraiChargeProj;
import org.bukkit.entity.Player;

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
        RiftWalkerDomain domain = new RiftWalkerDomain(player,20, player.getLocation(),30,4,1,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }
}
