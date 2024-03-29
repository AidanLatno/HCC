package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class FireSword extends Sword {
    public FireSword()
    {

        name = ChatColor.RED + ChatColor.BOLD.toString() + "Fire Sword";
        lore.add(ChatColor.DARK_RED + "A sword infused with fire blood.");
        CustomModelData = 1;
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

        loc.getWorld().playSound(loc, Sound.ENTITY_BLAZE_SHOOT, SoundCategory.MASTER,2,1);
        loc.getWorld().spawnParticle(Particle.FLAME,loc,100, 2,2,2,0,null,true);

        for(LivingEntity e : entitiesInCone)
        {
            if(e.equals(player)) continue;

            e.setFireTicks(60);
            loc.getWorld().spawnParticle(Particle.FLAME,e.getLocation(),10, 2,2,2,0,null,true);
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
