package me.testedpugtato.kingdomcraftplugin.barriers;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class NecromancerDomain extends Domain{
    public NecromancerDomain(Player caster, int Energy, Location center, float radius, int tickRate, int count, float offSetX, float offSetY, float offSetZ, float particleSpeed) {
        super(caster, Energy, center, radius, tickRate, count, offSetX, offSetY, offSetZ, particleSpeed);
    }

    @Override
    public void construct()
    {
        particles.add(Particle.DAMAGE_INDICATOR);
        particles.add(Particle.SQUID_INK);
    }

    @Override
    public boolean Logic()
    {
        return true;
    }

}
