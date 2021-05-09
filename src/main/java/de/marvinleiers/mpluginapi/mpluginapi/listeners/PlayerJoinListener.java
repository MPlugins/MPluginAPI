package de.marvinleiers.mpluginapi.mpluginapi.listeners;

import de.marvinleiers.mpluginapi.mpluginapi.MPlugin;
import de.marvinleiers.mpluginapi.mpluginapi.MPluginMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(MPluginMain.getInstance(), () -> {
            if (player.isOp())
            {
                MPluginMain.getUpdater().sendUpdateNotification(player);

                for (MPlugin mPlugin : MPluginMain.getInstance().getMPlugins())
                {
                    if (player.isOp())
                        mPlugin.getUpdater().sendUpdateNotification(player);
                }
            }
        }, 5);
    }
}
