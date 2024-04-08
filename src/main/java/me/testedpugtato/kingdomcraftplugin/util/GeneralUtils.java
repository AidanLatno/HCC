package me.testedpugtato.kingdomcraftplugin.util;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import me.testedpugtato.kingdomcraftplugin.items.blood.*;
import me.testedpugtato.kingdomcraftplugin.items.misc.BloodStone;
import me.testedpugtato.kingdomcraftplugin.items.swords.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GeneralUtils
{
    public static void PlaySound(Location location, Sound sound, float volume, float pitch)
    {
        location.getWorld().playSound(location,sound, SoundCategory.MASTER,volume,pitch);
    }
    public static void PlaySound(Location location, Sound sound, float pitch) { PlaySound(location,sound,1,pitch); }
    public static void PlaySound(Location location, Sound sound) { PlaySound(location,sound,1); }

    public static CustomItem GetHeldCustomItem(ItemStack item)
    {
        if(item.getItemMeta().hasCustomModelData()) {
            switch (item.getItemMeta().getCustomModelData()) {
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
                case 6:
                    return new AirBlood();
                case 7:
                    return new LightningBlood();
                case 8:
                    return new EarthBlood();
                case 9:
                    return new WaterBlood();
                case 10:
                    return new FireBlood();
                case 11:
                    return new BloodStone();
                case 12:
                    return new BloodStoneSword();
            }
        }
        return null;
    }
    public static CustomItem GetHeldCustomItem(Player player)
    {
        return GetHeldCustomItem(player.getInventory().getItemInMainHand());
    }
}
