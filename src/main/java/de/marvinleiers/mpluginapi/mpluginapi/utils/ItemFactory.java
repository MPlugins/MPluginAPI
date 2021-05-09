package de.marvinleiers.mpluginapi.mpluginapi.utils;

import de.marvinleiers.mpluginapi.mpluginapi.MPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemFactory
{
    private final MPlugin plugin;
    private final ItemStack item;
    private ItemMeta meta;
    private final List<String> lore;

    public ItemFactory(MPlugin plugin)
    {
        this(plugin, Material.BARRIER);
    }

    public ItemFactory(MPlugin plugin, Material material)
    {
        this(plugin, new ItemStack(material, 1));
    }

    public ItemFactory(MPlugin plugin, Material material, short data)
    {
        this(plugin, new ItemStack(material, 1, data));
    }

    public ItemFactory(MPlugin plugin, ItemStack item)
    {
        this.plugin = plugin;
        this.item = item;
        this.meta = item.getItemMeta();
        this.lore = new ArrayList<>();
    }

    public ItemFactory setMaterial(Material material)
    {
        item.setType(material);

        return this;
    }

    public ItemFactory setMaterial(Material material, short data)
    {
        item.setType(material);
        item.setDurability(data);

        return this;
    }

    public ItemFactory setDisplayName(String name)
    {
        if (meta == null)
            meta = item.getItemMeta();

        assert this.meta != null;
        this.meta.setDisplayName(name.replace("&", "§"));

        return this;
    }

    public ItemFactory addLore(String... lore)
    {
        List<String> list = new ArrayList<>();

        for (String str : Arrays.asList(lore))
            list.add(str.replace("&", "§"));

        this.lore.addAll(list);

        return this;
    }

    public ItemFactory addItemFlag(ItemFlag... flags)
    {
        this.meta.addItemFlags(flags);

        return this;
    }

    public ItemFactory addPersistentDataEntry(String key, String value)
    {
        return addPersistentDataEntry(key, value, false);
    }

    public ItemFactory addPersistentDataEntry(String key, String value, boolean force)
    {
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();

        if (persistentDataContainer.has(new NamespacedKey(plugin, key), PersistentDataType.STRING))
        {
            if (force)
                persistentDataContainer.set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        }
        else
        {
            persistentDataContainer.set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        }

        return this;
    }

    public ItemStack getItem()
    {
        this.meta.setLore(lore);
        this.item.setItemMeta(this.meta);

        return item;
    }
}
