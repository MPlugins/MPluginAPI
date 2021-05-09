package de.marvinleiers.mpluginapi.mpluginapi.commands;

import de.marvinleiers.mpluginapi.mpluginapi.MPlugin;
import de.marvinleiers.mpluginapi.mpluginapi.MPluginMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public abstract class RootCommand implements CommandExecutor
{
    private static final MPluginMain main = MPluginMain.getInstance();
    private ArrayList<Subcommand> subcommands = new ArrayList<>();
    private String name;

    public RootCommand(MPlugin mPlugin, String name)
    {
        this.name = name;

        try
        {
            main.getPlugin(mPlugin).getCommand(this.name).setExecutor(this);
        }
        catch (NullPointerException e)
        {
            Bukkit.getConsoleSender().sendMessage("§4Error: §cCommand " + name + " is missing in plugin.yml!");
            main.getPluginLoader().disablePlugin(mPlugin);
        }
    }

    public RootCommand(JavaPlugin plugin, String name)
    {
        this.name = name;

        try
        {
            plugin.getCommand(this.name).setExecutor(this);
        }
        catch (NullPointerException e)
        {
            Bukkit.getConsoleSender().sendMessage("§4Error: §cCommand " + name + " is missing in plugin.yml!");
            main.getPluginLoader().disablePlugin(plugin);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            this.onConsoleExecuted((ConsoleCommandSender) sender, args);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0)
        {
            this.onNoArguments(player, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("help"))
        {
            sendHelp(player);
            return true;
        }

        for (Subcommand subcommand : subcommands)
        {
            if (subcommand.getName().equalsIgnoreCase(args[0]))
            {
                subcommand.execute(player, args);
                return true;
            }
        }

        sendHelp(sender);
        return true;
    }

    protected void onNoArguments(Player player, String[] args)
    {
        sendHelp(player);
    }

    protected void onConsoleExecuted(ConsoleCommandSender sender, String[] args)
    {
        sender.sendMessage("§cOnly players can execute this command!");
    }

    public String getName()
    {
        return name;
    }

    public void addSubcommand(Subcommand subcommand)
    {
        for (Subcommand command : subcommands)
        {
            if (command.getName().equalsIgnoreCase(subcommand.getName()))
                return;
        }

        subcommand.setRootCommand(this);
        subcommands.add(subcommand);
    }

    public ArrayList<Subcommand> getSubcommands()
    {
        return subcommands;
    }

    private void sendHelp(CommandSender sender)
    {
        sender.sendMessage(" ");
        sender.sendMessage(" ");

        for (Subcommand subcommand : subcommands)
        {
            sender.sendMessage(subcommand.getUsage());
            sender.sendMessage(" ");
        }

        sender.sendMessage(" ");
        sender.sendMessage(" ");
    }
}
