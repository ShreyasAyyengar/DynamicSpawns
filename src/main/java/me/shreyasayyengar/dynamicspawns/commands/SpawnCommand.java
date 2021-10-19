package me.shreyasayyengar.dynamicspawns.commands;

import me.shreyasayyengar.dynamicspawns.DynamicSpawns;
import me.shreyasayyengar.dynamicspawns.utils.Config;
import me.shreyasayyengar.dynamicspawns.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (DynamicSpawns.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false).size() == 0) {
            sender.sendMessage(Utils.colourise(Config.getNoSpawns()));
            return false;
        }

        if (sender instanceof Player) {

            if (sender.hasPermission("dynamicspawns.bypass")) {
                Utils.teleportPlayer(((Player) sender));
                if (Config.useTeleportMessage()) {
                    for (String message : Config.getTeleportMessage()) {
                        sender.sendMessage(Utils.colourise(message));
                    }
                }
                return false;
            }

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
                }.runTaskTimer(DynamicSpawns.getInstance(), 0, 20);
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