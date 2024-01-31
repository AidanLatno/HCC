package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.data.PlayerMemory;
import me.testedpugtato.kingdomcraftplugin.data.PlayerUtility;
import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeSlot
{
    public ChangeSlot()
    {
        new CommandBase("changeslot",1)
        {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments)
            {
                Player player = (Player) sender;
                int slot = Integer.parseInt(arguments[0]);

                if(slot < 1 || slot > 9) { return false; }

                slot--;
                PlayerMemory memory = PlayerUtility.getPlayerMemory(player);

                memory.setPowerSlot(slot);
                PlayerUtility.setPlayerMemory(player,memory);
                return true;

            }

            @Override
            public String getUsage() { return "/changeslot <1-9>";}
        };
    }
}
