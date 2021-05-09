package de.marvinleiers.mpluginapi.mpluginapi.commands;

import de.marvinleiers.mpluginapi.mpluginapi.MPlugin;
import de.marvinleiers.mpluginapi.mpluginapi.MPluginMain;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Command implements CommandExecutor, ICommand
{
    private final String name;
    private final String permission;
    private final boolean allowConsole;
    private static final MPluginMain main = MPluginMain.getInstance();

    public Command(JavaPlugin plugin, String name)
    {
        this(plugin, name, null, false);
    }

    public Command(JavaPlugin plugin, String name, String permission)
    {
        this(plugin, name, permission, false);
    }

    public Command(JavaPlugin plugin, String name, boolean allowConsole)
    {
        this(plugin, name, null, allowConsole);
    }

    public Command(JavaPlugin plugin, String name, String permission, boolean allowConsole)
    {
        this.name = name;
        this.permission = permission;
        this.allowConsole = allowConsole;

        plugin.getCommand(name).setExecutor(this);
    }

    public Command(MPlugin mPlugin, String name)
    {
        this(mPlugin, name, null, false);
    }

    public Command(MPlugin mPlugin, String name, String permission)
    {
        this(mPlugin, name, permission, false);
    }

    public Command(MPlugin mPlugin, String name, boolean allowConsole)
    {
        this(mPlugin, name, null, allowConsole);
    }

    public Command(MPlugin mPlugin, String name, String permission, boolean allowConsole)
    {
        this.name = name;
        this.permission = permission;
        this.allowConsole = allowConsole;

        main.getPlugin(mPlugin).getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            if (getAllowConsole())
            {
                this.onExecute(sender, args);
                return true;
            }
            else
            {
                sender.sendMessage("§cOnly players can perform this command");
                return true;
            }
        }

        Player player = (Player) sender;

        if (permission == null || permission.isEmpty() || player.hasPermission(getPermission()))
        {
            this.onPlayerExecute(player, args);
        }
        else
        {
            player.sendMessage("§cYou are not permitted to do that!");
        }

        return true;
    }

    public String getName()
    {
        return name;
    }

    public boolean hasPermission()
    {
        return permission != null;
    }

    public String getPermission()
    {
        return permission;
    }

    public boolean getAllowConsole()
    {
        return allowConsole;
    }
}
