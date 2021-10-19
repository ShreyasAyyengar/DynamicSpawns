package me.shreyasayyengar.dynamicspawns.events;

import me.shreyasayyengar.dynamicspawns.utils.Config;
import me.shreyasayyengar.dynamicspawns.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        if (Config.teleportJoin()) {
            Player player = e.getPlayer();
            Utils.teleportPlayer(player);
        }
    }
}