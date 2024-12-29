package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import me.testedpugtato.kingdomcraftplugin.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpecialist {
    public SetSpecialist() {
        new CommandBase("setspecialist",1, 2) {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                if(!player.isOp()) {
                    Msg.send(player,"You do not have permission to use this command!","&c");
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO,100,1);
                }
                if(arguments.length == 1) {
                    PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
                    memory.setSpecialist(arguments[0]);
                    PlayerUtility.setPlayerMemory(player, memory);

                    player.sendMessage("Your power has been set to " + arguments[0]);
                } else {
                    Player player2 = Bukkit.getPlayerExact(arguments[1]);
                    if(player2 == null) {
                        Msg.send(player,"Could not find player.","&c");
                        return true;
                    }
                    PlayerMemory memory = PlayerUtility.getPlayerMemory(player2);
                    memory.setSpecialist(arguments[0]);
                    PlayerUtility.setPlayerMemory(player2, memory);
                    player.sendMessage("Set " + player2.getName() + "'s power to " + arguments[0]);
                    player2.sendMessage("Your power has been set to " + arguments[0]);
                }

                return true;
            }

            @Override
            public String getUsage() { return "/setpower <power> <player-name>"; }
        };
    }
}
