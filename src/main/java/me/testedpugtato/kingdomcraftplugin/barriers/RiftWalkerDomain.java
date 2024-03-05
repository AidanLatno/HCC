package me.testedpugtato.kingdomcraftplugin.barriers;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class RiftWalkerDomain extends Domain{
    public RiftWalkerDomain(Player caster, int Energy , Location center, float radius, int tickRate, int count, float offSetX, float offSetY, float offSetZ, float particleSpeed) {
        super(caster, Energy, center, radius, tickRate, count, offSetX, offSetY, offSetZ, particleSpeed);
    }

    @Override
    public void construct()
    {
        particles.add(Particle.SONIC_BOOM);
    }

    @Override
    public boolean Logic()
    {
        Location loc = center.clone();

        for(int i = 0; i < 20; i++) {
            loc.add((Math.random() * radius * 2) - radius, (Math.random() * radius * 2) - radius, (Math.random() * radius * 2) - radius);
            loc.createExplosion(caster, 3, true, true);
            loc = center.clone();
        }
        return true;
    }

}
