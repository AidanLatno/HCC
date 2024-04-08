package me.testedpugtato.kingdomcraftplugin.util;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import org.bukkit.inventory.ItemStack;

public class Recipe
{
    ItemStack item1;
    ItemStack item2;
    CustomItem custom1;
    CustomItem custom2;
    ItemStack returnItem;
    public Recipe(ItemStack item1, ItemStack item2)
    {
        this.item1 = item1;
        this.item2 = item2;
    }
    public Recipe(ItemStack item1, CustomItem custom2)
    {
        this.item1 = item1;
        this.custom2 = custom2;
    }
    public Recipe(CustomItem custom1, ItemStack item2)
    {
        this.custom1 = custom1;
        this.item2 = item2;
    }
    public Recipe(CustomItem custom1, CustomItem custom2)
    {
        this.custom1 = custom1;
        this.custom2 = custom2;
    }
}
