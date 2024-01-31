package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import me.testedpugtato.kingdomcraftplugin.util.Msg;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;


public class YouTubeLink
{
    public YouTubeLink()
    {
        new CommandBase("yt")
        {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                TextComponent component = new TextComponent("https://www.youtube.com/channel/UCsialUqqquukbgiSr1oC_XA");
                sender.spigot().sendMessage(component);
                return true;
            }

            @Override
            public String getUsage() { return "/yt"; }
        };
    }
}
