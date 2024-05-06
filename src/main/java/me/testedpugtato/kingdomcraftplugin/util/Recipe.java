package me.testedpugtato.kingdomcraftplugin.util;

import jdk.internal.net.http.common.Pair;
import me.testedpugtato.kingdomcraftplugin.Database;
import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Recipe
{

    static CustomItem craft(ItemStack item1, ItemStack item2)
    {
        CustomItem returnItem = null;
        if(item1.getItemMeta().hasCustomModelData() && item2.getItemMeta().hasCustomModelData())
        {
            returnItem = Database.RecipeMap.get(Pair.pair(item1.getItemMeta().getCustomModelData(),item2.getItemMeta().getCustomModelData()));
            ItemMeta data = item1.getItemMeta();
            data.setCustomModelData(returnItem.CustomModelData);
            returnItem.setItemMeta(data);
        }
        return returnItem;
    }

}
