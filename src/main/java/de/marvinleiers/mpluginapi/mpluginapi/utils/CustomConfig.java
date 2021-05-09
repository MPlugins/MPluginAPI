package de.marvinleiers.mpluginapi.mpluginapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CustomConfig
{
    private File file;
    private FileConfiguration config;

    public CustomConfig(String path)
    {
        file = new File(path);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reload()
    {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void addToStringList(String path, String... elements)
    {
        List<String> list = config.getStringList(path);
        list.addAll(Arrays.asList(elements));
        config.set(path, list);
        saveConfig();
    }

    public void addDefault(String path, Object obj)
    {
        if (!config.isSet(path))
            config.set(path, obj);

        saveConfig();
    }

    public void set(String path, Location obj)
    {
        setLocation(path, obj);
    }

    public void set(String path, Object obj)
    {
        if (obj instanceof Location)
            setLocation(path, (Location) obj);
        else
            config.set(path, obj);

        saveConfig();
    }

    public boolean isSet(String path)
    {
        return config.isSet(path);
    }

    public void setLocation(String path, Location loc)
    {
        config.set(path + ".world", loc.getWorld().getName());
        config.set(path + ".x", loc.getX());
        config.set(path + ".y", loc.getY());
        config.set(path + ".z", loc.getZ());
        config.set(path + ".yaw", loc.getYaw());
        config.set(path + ".pitch", loc.getPitch());

        saveConfig();
    }

    public Location getLocation(String path)
    {
        try
        {
            return new Location(Bukkit.getWorld(config.getString(path + ".world")), config.getDouble(path + ".x"),
                    config.getDouble(path + ".y"), config.getDouble(path + ".z"),
                    (float) config.getDouble(path + ".yaw"), (float) config.getDouble(path + ".pitch"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public long getLong(String path)
    {
        return config.getLong(path);
    }

    public int getInt(String path)
    {
        return config.getInt(path);
    }

    public String getString(String path)
    {
        return config.getString(path);
    }

    public double getDouble(String path)
    {
        return config.getDouble(path);
    }

    public ConfigurationSection getSection(String path)
    {
        return config.getConfigurationSection(path);
    }

    public FileConfiguration getConfig()
    {
        return config;
    }

    public void saveConfig()
    {
        try
        {
            config.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
