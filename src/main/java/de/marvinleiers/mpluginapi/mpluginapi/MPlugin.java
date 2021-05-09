package de.marvinleiers.mpluginapi.mpluginapi;

import de.marvinleiers.mpluginapi.mpluginapi.utils.Messages;
import de.marvinleiers.mpluginapi.mpluginapi.utils.Updater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public abstract class MPlugin extends JavaPlugin
{
    private ConsoleCommandSender console;
    private Updater updater;
    private Messages messages;

    @Override
    public void onEnable()
    {
        console = Bukkit.getConsoleSender();
        MPluginMain.getInstance().registerMPlugin(this);

        this.messages = Messages.setUp(this);

        onStart();

        if (this.isEnabled())
            updater = new Updater(this);
    }

    @Override
    public void onDisable()
    {
        onStop();
    }

    public void log(String msg)
    {
        if (getConfig().getBoolean("enable-logging"))
            console.sendMessage("[" + getDescription().getName() + "] " + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public Updater getUpdater()
    {
        return updater;
    }

    public Messages getMessages()
    {
        return messages;
    }

    public static Collection<MPlugin> getMPlugins()
    {
        return MPluginMain.getInstance().getMPlugins();
    }

    protected abstract void onStart();

    protected abstract void onStop();
}
