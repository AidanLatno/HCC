//package me.testedpugtato.kingdomcraftplugin.commands;
//
//import me.testedpugtato.kingdomcraftplugin.util.CommandBase;
//import org.bukkit.Material;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Sword
//{
//    public Sword()
//    {
//        new CommandBase("sword",true)
//        {
//            @Override
//            public boolean onCommand(CommandSender sender, String [] arguments)
//            {
//                Player player = (Player) sender;
//
//                ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
//                ItemMeta meta = item.getItemMeta();
//                if(meta != null)
//                {
//                    meta.setDisplayName("Hello World");
//                    meta.setUnbreakable(true);
//
//                    List<String> lore = new ArrayList<>();
//                    lore.add("Test1");
//                    lore.add("Test2");
//                    lore.add("Test3");
//                    meta.setLore(lore);
//                }
//                item.setItemMeta(meta);
//                player.getInventory().addItem(item);
//                return true;
//            }
//
//            @Override
//            public String getUsage()
//            {
//                return "/sword";
//            }
//        };
//    }
//}
