package me.testedpugtato.kingdomcraftplugin.barriers;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class EarthDomain extends Domain{
    public EarthDomain(Player caster, float upTime, Location center, float radius, int tickRate, int count, float offSetX, float offSetY, float offSetZ, float particleSpeed) {
        super(caster, upTime, center, radius, tickRate, count, offSetX, offSetY, offSetZ, particleSpeed);
    }

    @Override
    public void construct()
    {
        particles.add(Particle.HEART);
    }
}
