package me.testedpugtato.kingdomcraftplugin.powers.specialists;

import me.testedpugtato.kingdomcraftplugin.barriers.NecromancerDomain;
import me.testedpugtato.kingdomcraftplugin.barriers.RiftWalkerDomain;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.powers.Power;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Necromancer extends Power {
    @Override
    public boolean domainExpand()
    {
        UUID uuid = UUID.fromString(PlayerUtility.findKeyByValue(memory)); // Convert the string to a UUID
        Player player = Bukkit.getPlayer(uuid);

        NecromancerDomain domain = new NecromancerDomain(player,10, player.getLocation(),30,3,1,0.1f,0.1f,0.1f,0.05f);
        domain.ExpandDomain();
        return true;
    }
}
