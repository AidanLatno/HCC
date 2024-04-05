package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import org.bukkit.command.CommandSender;

public class Craft {
    public Craft()
    {
        new CommandBase("craft")
        {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments)
            {

                return true;
            }

            @Override
            public String getUsage() { return "/craft";}
        };
    }
}
