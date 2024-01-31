package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.projectiles.LightningProjectiles.LightningBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import me.testedpugtato.kingdomcraftplugin.util.Msg;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;

public class Lightning extends Power
{
    public Lightning()
    {
        setBasicCooldown(3);
        setDashCooldown(1);
        setQuickCooldown(4);
        setArielCooldown(7);
        setChargedCooldown(15);
        setSlamCooldown(15);
        id = "lightning";
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel) {
        LightningBasicProj proj = new LightningBasicProj(player,4,7);
        proj.moveSelf(MathUtils.levelInter(0.4,1,powerLevel),true);
    }

    @Override
    public void useAriel(Player player, int playerLevel) {
        super.useAriel(player, playerLevel);
    }

    @Override
    public void useArielDash(Player player, int powerLevel) {
        player.setVelocity(player.getLocation().getDirection().clone().multiply(10));

        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                player.sendMessage("Ticks: " + ticks);
                player.getWorld().spawnParticle(Particle.SCRAPE,player.getEyeLocation(),4,.2,.2,.2,0, null, true);
                if(ticks >= MathUtils.levelInter(0.1,0.5, powerLevel)*20) {
                    player.setVelocity(new Vector(0,0,0));
                    player.sendMessage("DONE");
                }
                else {
                    player.sendMessage("Keep going");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), this, 1);

                }
            }
        },1);
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge) {
        super.chargeChargedAttack(player, powerLevel, charge);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge) {
        super.useChargedAttack(player, powerLevel, charge);
    }

    @Override
    public void useQuickAttack(Player player, int powerLevel) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            @Override
            public void run(){

            }
        },1);
    }

    @Override
    public void useGroundSlam(Player player, int powerLevel) {
        super.useGroundSlam(player, powerLevel);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge) {
        super.groundSlamFalling(player, powerLevel, charge);
    }

    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge) {
        super.useGroundSlamLanding(player, powerLevel, charge);
    }
}
