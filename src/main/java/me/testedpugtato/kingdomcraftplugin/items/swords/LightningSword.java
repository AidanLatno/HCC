package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.util.GeneralUtils;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class LightningSword extends Sword{
    public LightningSword()
    {
        name = ChatColor.YELLOW + ChatColor.BOLD.toString() + "Lightning Sword";
        lore.add(ChatColor.DARK_RED + "A sword infused with lightning blood.");
        CustomModelData = 3;
        baseItem = Material.NETHERITE_SWORD;
    }

    @Override
    public void useBasicAttack(Player player, int powerLevel, float swordDamage)
    {

    }

    @Override
    public void useAriel(Player player, int powerLevel, float swordDamage)
    {

    }
    @Override
    public void useArielDash(Player player, int powerLevel, float swordDamage)
    {

    }
    @Override
    public void useQuickAttack(Player player, int powerLevel, float swordDamage)
    {
        super.useQuickAttack(player,powerLevel,swordDamage);
        List<LivingEntity> entitiesInCone = MathUtils.getEntitiesInCone(player.getLocation());
        Location loc = player.getEyeLocation().clone();

        loc.add(loc.getDirection().multiply(2));

        GeneralUtils.SpawnParticle(loc, Particle.SCRAPE,100,2,2,2);

        for(LivingEntity e : entitiesInCone)
        {
            if(e.equals(player)) continue;

            e.getWorld().strikeLightningEffect(e.getLocation());
            GeneralUtils.SpawnParticle(e.getLocation(),Particle.SCRAPE,10,2,2,2);
        }
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
