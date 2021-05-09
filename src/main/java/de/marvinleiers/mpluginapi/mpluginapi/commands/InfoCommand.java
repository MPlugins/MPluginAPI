package de.marvinleiers.mpluginapi.mpluginapi.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InfoCommand extends Command
{
    public InfoCommand(JavaPlugin plugin, String name)
    {
        super(plugin, name, true);
    }

    @Override
    public void onPlayerExecute(Player player, String[] args)
    {
        onExecute(player, args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args)
    {
        sender.sendMessage("§7Plugins powered by. §9Marvin Leiers. §7Visit §bhttps://marvinleiers.de §7for free + paid plugins");
    }
}
