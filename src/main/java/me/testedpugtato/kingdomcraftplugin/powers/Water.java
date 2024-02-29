package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.barriers.Domain;
import me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles.WaterBasicProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.WaterProjectiles.WaterQuickProj;
import org.bukkit.entity.Player;

public class Water extends Power
{
    public Water()
    {
        setBasicCooldown(3);
        setDashCooldown(5);
        setQuickCooldown(0.1f);
        setArielCooldown(7);
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

    }
    @Override
    public void useArielDash(Player player, int powerLevel)
    {

    }
    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        WaterQuickProj proj = new WaterQuickProj(player,3,1);
        proj.moveSelf(1,false);
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

    public void domainExpand(Player player)
    {
        // Change to your domain
        Domain domain = new Domain(player,20, player.getLocation(),30,1,2,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }

}
