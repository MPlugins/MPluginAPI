package de.marvinleiers.mpluginapi.mpluginapi.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemUtils
{
    public static boolean hasPersistentDataKey(JavaPlugin plugin, ItemStack item, String key)
    {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();

        return persistentDataContainer.has(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    public static String beautifyName(String str)
    {
        str = str.toLowerCase().replace("_", " ");
        String name = "";

        for (String word : str.split(" "))
        {
            name += word.substring(0, 1).toUpperCase() + word.substring(1) + " ";
        }

        return name.trim();
    }
}
