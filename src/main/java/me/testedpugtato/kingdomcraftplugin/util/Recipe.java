package me.testedpugtato.kingdomcraftplugin.util;

import me.testedpugtato.kingdomcraftplugin.items.CustomItem;
import org.bukkit.inventory.ItemStack;

public class Recipe
{
    ItemStack item1;
    ItemStack item2;
    CustomItem custom1;
    CustomItem custom2;
    CustomItem returnItem;
    public Recipe(ItemStack item1, ItemStack item2, CustomItem returnItem)
    {
        this.item1 = item1;
        this.item2 = item2;
        this.returnItem = returnItem;
    }
    public Recipe(ItemStack item1, CustomItem custom2, CustomItem returnItem)
    {
        this.item1 = item1;
        this.custom2 = custom2;
        this.returnItem = returnItem;
    }
    public Recipe(CustomItem custom1, ItemStack item2, CustomItem returnItem)
    {
        this.custom1 = custom1;
        this.item2 = item2;
        this.returnItem = returnItem;
    }
    public Recipe(CustomItem custom1, CustomItem custom2, CustomItem returnItem)
    {
        this.custom1 = custom1;
        this.custom2 = custom2;
        this.returnItem = returnItem;
    }


    public CustomItem Check(ItemStack item1, ItemStack item2)
    {
        if(this.item1 == null || this.item2 == null) return null;

        if(this.item1.getType().equals(item1.getType()) && this.item2.getType().equals(item2.getType())) return returnItem;
        return null;
    }
    public CustomItem Check(ItemStack item1, CustomItem custom2)
    {
        this.item1 = item1;
        this.custom2 = custom2;

    }
    public CustomItem Check(CustomItem custom1, ItemStack item2)
    {
        this.custom1 = custom1;
        this.item2 = item2;

    }
    public CustomItem Check(CustomItem custom1, CustomItem custom2)
    {
        this.custom1 = custom1;
        this.custom2 = custom2;

    }
}
