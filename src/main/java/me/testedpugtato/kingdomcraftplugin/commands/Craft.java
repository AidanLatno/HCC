package me.testedpugtato.kingdomcraftplugin.commands;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
import me.testedpugtato.kingdomcraftplugin.util.GeneralUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Craft {
    public Craft()
    {
        new CommandBase("craft")
        {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments)
            {
                Player player = (Player) sender;
                CustomItem mainHand = GeneralUtils.GetHeldCustomItem(player);
                CustomItem offHand = GeneralUtils.GetHeldCustomItem(player.getInventory().getItemInOffHand());



                return true;
            }

            @Override
            public String getUsage() { return "/craft";}
        };
    }
}
