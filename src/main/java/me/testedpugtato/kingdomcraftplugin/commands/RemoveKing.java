package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import me.testedpugtato.kingdomcraftplugin.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveKing
{
    public RemoveKing()
    {
        new CommandBase("removeking",0,1)
        {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;

                if(!player.isOp())
                {
                    Msg.NoPermission(player);
                    return true;
                }

                if(arguments.length == 1){
                    if(Bukkit.getPlayerExact(arguments[0]) == null){
                        Msg.send(player,"Could not find player.","&c");
                        return true;
                    }
                    player.sendMessage("Removed king status from " + Bukkit.getPlayerExact(arguments[0]).getName());
                    player = Bukkit.getPlayerExact(arguments[0]);
                }

                PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
                memory.setKing(false);
                PlayerUtility.setPlayerMemory(player,memory);
                player.sendMessage("You are no longer a king.");
                return true;
            }

            @Override
            public String getUsage() { return "/removeking <name>"; }
        };
    }
}
