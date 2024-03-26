package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.barriers.Domain;
import me.testedpugtato.kingdomcraftplugin.items.swords.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;


public class Samurai extends Power
{
    final public static Material[] swordList = {Material.WOODEN_SWORD,Material.STONE_SWORD,Material.IRON_SWORD,Material.GOLDEN_SWORD,Material.DIAMOND_SWORD,Material.NETHERITE_SWORD};

    public Samurai()
    {
        setBasicCooldown(3);
        setDashCooldown(5);
        setQuickCooldown(1);
        setArielCooldown(7);
        setChargedCooldown(15);
        setSlamCooldown(15);
        id = "samurai";
    }
    @Override
    public void useQuickAttack(Player player, int powerLevel)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);


        Sword sword = getHeldSword(player);
        sword.useQuickAttack(player,powerLevel,swordDamage);
    }

    @Override
    public void useAriel(Player player, int powerLevel)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);
    }
    @Override
    public void useArielDash(Player player, int powerLevel)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);

        Sword sword = new Sword();
        sword.useArielDash(player,powerLevel,swordDamage);
    }
    @Override
    public void useBasicAttack(Player player, int powerLevel)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);

        getHeldSword(player);
        Sword sword = new Sword();
        sword.useBasicAttack(player,powerLevel,swordDamage);
    }
    @Override
    public void useGroundSlam(Player player, int powerLevel)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);
    }

    @Override
    public void groundSlamFalling(Player player, int powerLevel, double charge)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);
    }
    @Override
    public void useGroundSlamLanding(Player player, int powerLevel, double charge)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);
    }

    @Override
    public void chargeChargedAttack(Player player, int powerLevel, double charge)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);
    }

    @Override
    public void useChargedAttack(Player player, int powerLevel, double charge)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);
    }

    @Override
    public void domainExpand(Player player)
    {
        if(!isHoldingSword(player)) return;
        float swordDamage = getSwordDamage(player);

        // Change to your domain
        Domain domain = new Domain(player,20, player.getLocation(),30,1,2,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }

    public float getSwordDamage(Player player)
    {
        ItemStack item = player.getInventory().getItemInMainHand();
        float baseDamage;
        switch (item.getType()) {
            case WOODEN_SWORD:
            case GOLDEN_SWORD:
                baseDamage = 4.0f;
                break;
            case STONE_SWORD:
                baseDamage = 5.0f;
                break;
            case IRON_SWORD:
                baseDamage = 6.0f;
                break;
            case DIAMOND_SWORD:
                baseDamage = 7.0f;
                break;
            case NETHERITE_SWORD:
                baseDamage = 8.0f;
                break;
            // Add more cases for other items as needed
            default:
                return -1.0f;
        }

        if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
            int sharpnessLevel = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            // In Minecraft 1.9+, the formula for extra damage is 1 + 0.5 * (level - 1) for Sharpness
            float sharpnessExtraDamage = 1.0f + 0.5f * (sharpnessLevel - 1);
            baseDamage += sharpnessExtraDamage;
        }
        return baseDamage;
    }

    public boolean isHoldingSword(Player player)
    {
        Material HeldItemMat = player.getInventory().getItemInMainHand().getType();
        for (Material e : swordList) {
            if (e.equals(HeldItemMat)) {
                return true;
            }
        }
        return false;
    }

    public Sword getHeldSword(Player player)
    {
        ItemStack HeldItem = player.getInventory().getItemInMainHand();

        if(HeldItem.getItemMeta().hasCustomModelData()) {
            switch (HeldItem.getItemMeta().getCustomModelData()) {
                case 1:
                    return new FireSword();
                case 2:
                    return new WaterSword();
                case 3:
                    return new LightningSword();
                case 4:
                    return new EarthSword();
                case 5:
                    return new AirSword();
                case 12:
                    return new BloodStoneSword();
                default:
                    return new Sword();
            }
        }
        return new Sword();
    }
}
