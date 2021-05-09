package de.marvinleiers.mpluginapi.mpluginapi;

import de.marvinleiers.menuapi.MenuAPI;
import de.marvinleiers.mpluginapi.mpluginapi.commands.InfoCommand;
import de.marvinleiers.mpluginapi.mpluginapi.listeners.PlayerJoinListener;
import de.marvinleiers.mpluginapi.mpluginapi.utils.Updater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;

public class MPluginMain extends JavaPlugin
{
    private static final HashMap<String, MPlugin> plugins = new HashMap<>();
    private static MPluginMain instance;
    private static Updater updater;
    private ConsoleCommandSender console;
    private boolean printLogo;

    @Override
    public void onEnable()
    {
        instance = this;
        updater = new Updater(this);
        this.printLogo = true;
        this.console = Bukkit.getConsoleSender();

        MenuAPI.setUp(this);

        new InfoCommand(this, "info");

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        Bukkit.getScheduler().runTaskAsynchronously(this, this::printLogo);
    }

    public void setPrintLogo(boolean printLogo)
    {
        this.printLogo = printLogo;
    }

    public MPlugin getPlugin(MPlugin mPlugin)
    {
        return plugins.get(mPlugin.getDescription().getName());
    }

    public Collection<MPlugin> getMPlugins()
    {
        return plugins.values();
    }

    public void registerMPlugin(MPlugin mPlugin)
    {
        String pluginName = mPlugin.getDescription().getName();

        plugins.put(pluginName, mPlugin);

        log("&9Registered " + pluginName);
    }

    private void printLogo()
    {
        if (!printLogo)
        {
            getLogger().info("\tPlugin by Marvin Leiers");
            getLogger().info("\tDownload free + paid plugins here:");
            getLogger().info("\thttps://www.marvinleiers.de");
            return;
        }

        console.sendMessage(" ");
        console.sendMessage(" ");
        console.sendMessage("\t\t\t§9Plugin by: §r");
        console.sendMessage(" ");
        console.sendMessage(" §9__  __                  _       _         _");
        console.sendMessage("§9|  \\/  | __ _ _ ____   _(_)_ __ | |    ___(_) ___ _ __ ___");
        console.sendMessage("§9| |\\/| |/ _` | '__\\ \\ / | | '_ \\| |   / _ | |/ _ | '__/ __|");
        console.sendMessage("§9| |  | | (_| | |   \\ V /| | | | | |__|  __| |  __| |  \\__ \\");
        console.sendMessage("§9|_|  |_|\\__,_|_|    \\_/ |_|_| |_|_____\\___|_|\\___|_|  |___/");
        console.sendMessage(" ");
        console.sendMessage("\t\t§bhttps://www.marvinleiers.de");
        console.sendMessage(" ");
        console.sendMessage(" ");
    }

    public void log(String msg)
    {
        console.sendMessage("[" + getDescription().getName() + "] " + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static MPluginMain getInstance()
    {
        return instance;
    }

    public static Updater getUpdater()
    {
        return updater;
    }
}
