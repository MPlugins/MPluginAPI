package de.marvinleiers.mpluginapi.mpluginapi.commands;

import org.bukkit.entity.Player;

public abstract class Subcommand
{
    private RootCommand rootCommand;

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getPermission();

    public boolean hasPermission()
    {
        return getPermission() != null;
    }

    public void setRootCommand(RootCommand rootCommand)
    {
        this.rootCommand = rootCommand;
    }

    public RootCommand getRootCommand()
    {
        return rootCommand;
    }

    public String getUsage()
    {
        return "§a§l‣ §7" + getSyntax() + " §6• §7" + getDescription();
    }

    public abstract void execute(Player player, String[] args);
}