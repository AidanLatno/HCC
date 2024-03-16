package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stats
{
    public Stats()
    {
        new CommandBase("stats")
        {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments)
            {
                Player player = (Player) sender;
                PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
                if(memory.getPower() == null) {
                    player.sendMessage("You do not have a power!");
                    return true;
                }
                player.sendMessage(memory.toString());

                return true;
            }

            @Override
            public String getUsage()
            {
                return "/stats";
            }
        };
    }

}
