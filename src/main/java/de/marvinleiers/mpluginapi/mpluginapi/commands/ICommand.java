package de.marvinleiers.mpluginapi.mpluginapi.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ICommand
{
    void onPlayerExecute(Player player, String[] args);

    void onExecute(CommandSender sender, String[] args);
}
