package me.testedpugtato.kingdomcraftplugin.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;

public class GeneralUtils
{
    public static void PlaySound(Location location, Sound sound, float volume, float pitch)
    {
        location.getWorld().playSound(location,sound, SoundCategory.MASTER,volume,pitch);
    }
    public static void PlaySound(Location location, Sound sound, float pitch) { PlaySound(location,sound,1,pitch); }
    public static void PlaySound(Location location, Sound sound) { PlaySound(location,sound,1); }


    public static void SpawnParticle(Location location, Particle particle, int count, float offsetX, float offsetY, float offsetZ, float speed)
    {
        location.getWorld().spawnParticle(particle,location,count,offsetX,offsetY,offsetZ,speed,null, true);
    }
    public static void SpawnParticle(Location location, Particle particle, int count, float offsetX, float offsetY, float offsetZ)
    {
        SpawnParticle(location,particle,count,offsetX,offsetY,offsetZ,0);
    }
    public static void SpawnParticle(Location location, Particle particle, int count)
    {
        SpawnParticle(location,particle,count,0,0,0);
    }
}
