package me.testedpugtato.kingdomcraftplugin.powers;

import me.testedpugtato.kingdomcraftplugin.barriers.Domain;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Samurai extends Power
{

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
    public void useBasicAttack(Player player, int powerLevel)
    {
        player.sendMessage("Damage: " + Float.toString(getSwordDamage(player)));
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
        // Change to your domain
        Domain domain = new Domain(player,20, player.getLocation(),30,1,2,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
    }

    public float getSwordDamage(Player player)
    {
        ItemStack item = player.getInventory().getItemInMainHand();
        float baseDamage = 0;
        switch (item.getType()) {
            case WOODEN_SWORD:
                baseDamage = 4.0f;
            case STONE_SWORD:
                baseDamage = 5.0f;
            case IRON_SWORD:
                baseDamage = 6.0f;
            case DIAMOND_SWORD:
                baseDamage = 7.0f;
            case NETHERITE_SWORD:
                baseDamage = 8.0f;
            // Add more cases for other items as needed
            default:
                baseDamage = 0.0f; // Items not listed do not deal extra damage
        }

        if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
            int sharpnessLevel = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            // In Minecraft 1.9+, the formula for extra damage is 1 + 0.5 * (level - 1) for Sharpness
            float sharpnessExtraDamage = 1.0f + 0.5f * (sharpnessLevel - 1);
            return baseDamage + sharpnessExtraDamage;
        }
        return baseDamage;
    }
}
