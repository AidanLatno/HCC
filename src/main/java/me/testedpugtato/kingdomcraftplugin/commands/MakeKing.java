package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import me.testedpugtato.kingdomcraftplugin.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MakeKing
{
    public MakeKing()
    {
        new CommandBase("makeking",0,1)
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
                    player.sendMessage("Set " + Bukkit.getPlayerExact(arguments[0]).getName() + "'s to a king");
                    player = Bukkit.getPlayerExact(arguments[0]);
                }

                PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
                memory.setKing(true);
                PlayerUtility.setPlayerMemory(player,memory);
                player.sendMessage("You are now a king!");
                return true;
            }

            @Override
            public String getUsage() { return "/makeking <name>"; }
        };
    }
}
