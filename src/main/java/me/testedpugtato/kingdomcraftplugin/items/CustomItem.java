package me.testedpugtato.kingdomcraftplugin.items;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem
{
    public String name;
    public Material baseItem;
    public int CustomModelData;
    public ItemMeta itemMeta;
    public List<String> lore = new ArrayList<>();

    public void give(Player player, int quantity)
    {
        ItemStack item = new ItemStack(baseItem);
        itemMeta = item.getItemMeta();

        itemMeta.setCustomModelData(CustomModelData);

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }
    public void setItemMeta(ItemMeta meta)
    {
        itemMeta = meta;
        itemMeta.setLore(lore);

    }
    public ItemStack getItemStack()
    {
        ItemStack item = new ItemStack(baseItem);
        ItemMeta meta = item.getItemMeta();

        meta.setCustomModelData(CustomModelData);

        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
