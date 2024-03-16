package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class YouTubeLink
{
    public YouTubeLink()
    {
        new CommandBase("yt")
        {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                TextComponent message = new TextComponent("Click here to visit our YouTube Channel!");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/channel/UCsialUqqquukbgiSr1oC_XA"));
                message.setBold(true);
                message.setUnderlined(true);
                message.setColor(ChatColor.GOLD.asBungee());
                sender.spigot().sendMessage(message);
                return true;
            }

            @Override
            public String getUsage() { return "/yt"; }
        };
    }
}
