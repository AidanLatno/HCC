package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import me.testedpugtato.kingdomcraftplugin.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerLevel
{
    public PlayerLevel()
    {
        new CommandBase("addplayerxp",2,3)
        {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments)
            {
                Player player = (Player) sender;
                if(!player.isOp()){
                    Msg.send(player,"You do not have permission to use this command!","&c");
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO,100,1);
                    return true;
                }

                if(!arguments[0].equals("xp") && !arguments[0].equals("level")){
                    return false;
                }


                if(arguments.length == 3){
                    if(Bukkit.getPlayerExact(arguments[2]) == null){
                        Msg.send(player,"Could not find player.","&c");
                        return true;
                    }
                    player.sendMessage("Added " + arguments[1] + " player " + arguments[0] + " to " + Bukkit.getPlayerExact(arguments[2]).getName());
                    player = Bukkit.getPlayerExact(arguments[2]);
                }
                PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
                if(arguments[0].equals("xp"))
                    memory.addPlayerEXP(Integer.parseInt(arguments[1]));
                else
                    memory.addPlayerLevel(Integer.parseInt(arguments[1]));
                PlayerUtility.setPlayerMemory(player, memory);
                player.sendMessage("Your player level is now " + memory.getPowerLevel());
                player.sendMessage("Your player xp is now " + memory.getPowerEXP());


                return true;
            }

            @Override
            public String getUsage()
            {
                return "/addplayerxp <xp|level> <amount> <name>";
            }
        };

        new CommandBase("setplayerlevel",1,2)
        {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments)
            {
                Player player = (Player) sender;
                if(!player.isOp()){
                    Msg.send(player,"You do not have permission to use this command!","&c");
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO,100,1);
                    return true;
                }

                if(arguments.length == 2){
                    if(Bukkit.getPlayerExact(arguments[1]) == null){
                        Msg.send(player,"Could not find player.","&c");
                        return true;
                    }
                    player.sendMessage("Set " + Bukkit.getPlayerExact(arguments[1]).getName() + "'s player level to " + arguments[0]);
                    player = Bukkit.getPlayerExact(arguments[1]);
                }
                PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
                memory.setPlayerLevel(Integer.parseInt(arguments[0]));
                PlayerUtility.setPlayerMemory(player, memory);
                player.sendMessage("Your player level is now " + memory.getPlayerLevel());
                return true;
            }

            @Override
            public String getUsage()
            {
                return "/setplayerlevel <amount> <name>";
            }
        };
    }

}
