package me.testedpugtato.kingdomcraftplugin.util;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Msg
{
    public static void send(CommandSender sender, String message)
    {
        send(sender,message,"&a");
    }
    public static void send(CommandSender sender,String message, String prefix)
    {
        sender.sendMessage(color(prefix + message));
    }

    public static String color(String message)
    {
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    public static void NoPermission(Player player) {
        Msg.send(player,"You do not have permission to use this command!","&c");
        player.playSound(player, Sound.ENTITY_VILLAGER_NO,100,1);
    }
}
