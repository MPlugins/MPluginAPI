package de.marvinleiers.mpluginapi.mpluginapi.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Messages
{
    private CustomConfig customConfig;

    private Messages() {}

    public static Messages setUp(Plugin plugin)
    {
        Messages messages = new Messages();
        messages.setPlugin(plugin);

        return messages;
    }

    private void setPlugin(Plugin plugin)
    {
        this.customConfig = new CustomConfig(plugin.getDataFolder().getPath() + "/config.yml");
    }

    public List<String> getList(String path, OfflinePlayer player)
    {
        if (!customConfig.isSet(path))
            return null;

        List<String> list = new ArrayList<>();

        for (String str : customConfig.getConfig().getStringList(path))
        {
            ChatColor.translateAlternateColorCodes('&', str);

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI"))
                str = PlaceholderAPI.setPlaceholders(player, str);

            list.add(str);
        }

        return list;
    }

    public String get(String path, OfflinePlayer player)
    {
        if (!customConfig.isSet(path))
            return null;

        String msg = ChatColor.translateAlternateColorCodes('&', customConfig.getString(path));

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI"))
        {
            msg = PlaceholderAPI.setPlaceholders(player, msg);
        }

        return msg;
    }

    public String get(String path, String... parameters)
    {
        if (!customConfig.isSet(path))
            return null;

        String msg = ChatColor.translateAlternateColorCodes('&', customConfig.getString(path));

        if (parameters == null || parameters.length == 0)
        {
            return msg;
        }

        for (int i = 0; i < parameters.length; i++)
        {
            if (msg.contains("<v>"))
            {
                msg = msg.replaceFirst("<v>", parameters[i]);
            }
            else
            {
                break;
            }
        }

        return msg;
    }
}
