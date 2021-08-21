package me.shreyasayyengar.rivaspawn.commands;

import me.shreyasayyengar.rivaspawn.RivaSpawn;
import me.shreyasayyengar.rivaspawn.utils.Config;
import me.shreyasayyengar.rivaspawn.utils.Spawn;
import me.shreyasayyengar.rivaspawn.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminSpawnCommand implements CommandExecutor {

    private static final String noPerm = Config.getNoPerm();
    private static final String reload = Config.reload();
    private static final String create = Config.getCreate();
    private static final String delete = Config.getDelete();
    private static final String notFound = Config.noSuchSpawn();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (sender.hasPermission("rivaspawn.list")) {
                    sender.sendMessage(Utils.colourise("&3Active Spawns"));
                    for (String key : RivaSpawn.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false)) {
                        sender.sendMessage(Utils.colourise("&3- " + key));
                    }

                } else sender.sendMessage(Utils.colourise(noPerm));
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("rivaspawn.reload")) {
                    RivaSpawn.getInstance().reloadConfig();
                    sender.sendMessage(Utils.colourise(reload));
                } else sender.sendMessage(noPerm);
            }
        }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("rivacoins.setspawn")) {

                            String spawnName = args[1].toLowerCase();
                            new Spawn(spawnName, ((Player) sender).getLocation(), true);
                            sender.sendMessage(Utils.colourise(create.replace("{spawn}", spawnName)));


                        } else sender.sendMessage(Utils.colourise(noPerm));
                    } else sender.sendMessage(Utils.colourise("You cannot execute this command from the console!"));
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    if (sender.hasPermission("rivaspawn.delete")) {
                        String spawnName = args[1].toLowerCase();
                        for (String key : RivaSpawn.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false)) {
                            if (key.toLowerCase().equals(spawnName)) {
                                RivaSpawn.getInstance().getConfig().set("spawns." + key, null);
                            } else sender.sendMessage(Utils.colourise(notFound));
                        }
                        RivaSpawn.getInstance().saveConfig();
                        sender.sendMessage(Utils.colourise(delete.replace("{spawn}", spawnName)));
                    }
                }
            }


        return false;
    }
}
