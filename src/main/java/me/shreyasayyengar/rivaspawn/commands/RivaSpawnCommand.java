package me.shreyasayyengar.rivaspawn.commands;

import me.shreyasayyengar.rivaspawn.RivaSpawn;
import me.shreyasayyengar.rivaspawn.utils.Config;
import me.shreyasayyengar.rivaspawn.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RivaSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            if (Config.useTimer()) {

                final int[] seconds = {Config.getTimer()};

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (seconds[0] > 0) {
                            sender.sendMessage(Utils.colourise(Config.getCountdownMessage().replace("{seconds}", String.valueOf(seconds[0]))));
                            seconds[0]--;
                        } else if (seconds[0] == 0) {
                            Utils.teleportPlayer(((Player) sender));
                            cancel();
                            if (Config.useTeleportMessage()) {
                                for (String message : Config.getTeleportMessage()) {
                                    sender.sendMessage(Utils.colourise(message));
                                }
                            }
                        }
                    }
                }.runTaskTimer(RivaSpawn.getInstance(), 0, 20);
            } else {
                Utils.teleportPlayer(((Player) sender));
                if (Config.useTeleportMessage()) {
                    for (String message : Config.getTeleportMessage()) {
                        sender.sendMessage(Utils.colourise(message));
                    }
                }
            }

        } else sender.sendMessage("You cannot execute this command through the console!");
        return false;
    }
}