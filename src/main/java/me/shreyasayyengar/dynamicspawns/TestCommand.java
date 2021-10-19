package me.shreyasayyengar.dynamicspawns;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            if (DynamicSpawns.getInstance().getConfig().getConfigurationSection("spawns").getKeys(false).equals(args[0].toLowerCase())) {
                sender.sendMessage("true");
            }
        }
        return false;
    }
}