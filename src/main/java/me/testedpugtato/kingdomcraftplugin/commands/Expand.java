package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Expand {
    public Expand()
    {
        new CommandBase("expand")
        {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments)
            {
                Player player = (Player) sender;
                PlayerUtility.getPlayerMemory(player).getPower().domainExpand();

                return true;
            }

            @Override
            public String getUsage() { return "/expand";}
        };
    }
}
