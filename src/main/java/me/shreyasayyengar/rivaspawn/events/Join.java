package me.shreyasayyengar.rivaspawn.events;

import me.shreyasayyengar.rivaspawn.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Utils.teleportPlayer(player);
    }
}