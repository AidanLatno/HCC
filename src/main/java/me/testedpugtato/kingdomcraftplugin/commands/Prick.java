package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import org.bukkit.command.CommandSender;

public class Prick
{
    public Prick() {


        new CommandBase("prick") {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                return false;
            }

            @Override
            public String getUsage() {
                return null;
            }
        };
    }
}
