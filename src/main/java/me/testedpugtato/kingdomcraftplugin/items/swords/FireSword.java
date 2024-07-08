package me.testedpugtato.kingdomcraftplugin.items.swords;

import me.testedpugtato.kingdomcraftplugin.projectiles.FireProjectiles.FireBasicAttackProj;
import me.testedpugtato.kingdomcraftplugin.projectiles.SamuraiProjectiles.SamuraiBasicProj;
import me.testedpugtato.kingdomcraftplugin.util.*;
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
        super.useBasicAttack(player,powerLevel,swordDamage);
        FireBasicAttackProj proj = new FireBasicAttackProj(player,2,Particle.FLAME,2);
        proj.moveSelf(2,false);
    }

    @Override
    public void useAriel(Player player, int powerLevel, float swordDamage)
    {
        Location loc = player.getEyeLocation();

        loc.setPitch(90);
        loc.setYaw(0);

        ParticleMaker.createCircle(
                Particle.SWEEP_ATTACK,
                loc,
                lvl.i(1, 4, powerLevel),
                (int)lvl.i(1,5,powerLevel),
                lvl.i(2,32,powerLevel));
        ParticleMaker.createCircle(
                Particle.FLAME,
                loc,
                lvl.i(1, 4, powerLevel),
                (int)lvl.i(1,3,powerLevel),
                lvl.i(2,16,powerLevel));
        CombatManager.DamageNearby(player.getLocation(),lvl.i(1,4,powerLevel),3,lvl.i(1,4,powerLevel),swordDamage+3,player);

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

        GeneralUtils.PlaySound(loc,Sound.ENTITY_BLAZE_SHOOT,2,1);
        ParticleMaker.SpawnParticle(loc,Particle.FLAME,100,2,2,2);

        for(LivingEntity e : entitiesInCone)
        {
            if(e.equals(player)) continue;

            e.setFireTicks(60);
            ParticleMaker.SpawnParticle(e.getLocation(),Particle.FLAME,10,2,2,2);
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
