package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.*;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class AirSword extends Sword{
    public AirSword()
    {
        name = ChatColor.RED + ChatColor.BOLD.toString() + "Air Sword";
        lore.add(ChatColor.DARK_RED + "A sword infused with air blood.");
        CustomModelData = 5;
        baseItem = Material.NETHERITE_SWORD;
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel, float swordDamage)
    {

    }

    @Override
    public void useAriel(Player player, int powerLevel, float swordDamage)
    {
        super.useAriel(player, powerLevel, swordDamage);
        Location loc = player.getEyeLocation();

        loc.setPitch(90);
        loc.setYaw(0);


        ParticleMaker.createCircle(
                Particle.CLOUD,
                loc,
                lvl.i(1, 4, powerLevel),
                (int)lvl.i(1,6,powerLevel),
                lvl.i(2,16,powerLevel),
                1, 0.1,1);
        CombatManager.DamageNearby(player.getLocation(),lvl.i(1,4,powerLevel),3,lvl.i(1,4,powerLevel),swordDamage+4,player);
        for(LivingEntity e : MathUtils.getEntitiesInSphere(player.getLocation(),(int)(lvl.i(1, 4, powerLevel))))
        {
            if(e instanceof Player)
                PlayerUtility.getPlayerMemory((Player)e).stun(30);
        }

    }
    @Override
    public void useArielDash(Player player, int powerLevel, float swordDamage)
    {
super.useArielDash(player, powerLevel, swordDamage);
        GeneralUtils.PlaySound(player.getLocation(), Sound.ITEM_ELYTRA_FLYING,100,2);
        GeneralUtils.PlaySound(player.getLocation(),Sound.ENTITY_WITHER_HURT,40,-4);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            ArrayList<LivingEntity> hitEnemies = new ArrayList<>();
            @Override
            public void run() {
                ticks++;
                Vector dir = player.getLocation().getDirection().clone().multiply(10);
                dir.setY(0.2f);
                player.setVelocity(dir);
                ParticleMaker.SpawnParticle(player.getEyeLocation(), Particle.SCRAPE,4,.2f,.2f,.2f);
                CombatManager.ApplyPulse(player.getLocation(),3,1.5f,new Vector(3,3,3),player);
                for(LivingEntity e : player.getLocation().getNearbyLivingEntities(3,3,3))
                {
                    if(e.equals(player)) continue;
                    CombatManager.DamageEntity(swordDamage*lvl.i(0.9,1.4,powerLevel),e,player);
                    hitEnemies.add(e);
                }
                if(ticks >= lvl.i(0.1,0.35, powerLevel)*20) {
                    player.setVelocity(new Vector(0,0,0));
                    for(LivingEntity entity : hitEnemies)
                    {
                        player.getWorld().strikeLightningEffect(entity.getLocation());
                    }
                }
                else {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), this, 1);
                }
            }
        },1);

    }
    @Override
    public void useQuickAttack(Player player, int powerLevel, float swordDamage)
    {

    }
    @Override
    public void useGroundSlam(Player player, int powerLevel, float swordDamage)
    {

    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge, float swordDamage)
    {

    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge, float swordDamage)
    {

    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {

    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge, float swordDamage)
    {

    }
}
