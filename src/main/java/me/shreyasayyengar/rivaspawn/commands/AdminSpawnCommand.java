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
    private static final String noSpawn = Config.getNoSpawns();
    private static final String alreadyExists = Config.getAlreadyExists();
    private static final String noNumber = Config.getNotNumber();
    private static final String edited = Config.getEdited();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            if (sender.hasPermission("rivaspawn.help")) {
                for (String helpmessage : Config.getHelpMessages()) {
                    sender.sendMessage(Utils.colourise(helpmessage));
                }
            }
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("liste")) {
                if (sender.hasPermission("rivaspawn.list")) {
                    sender.sendMessage(Utils.colourise("&3Active Spawns"));
                    if (RivaSpawn.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false).size() == 0) {
                        sender.sendMessage(Utils.colourise(noSpawn));
                        return false;
                    }

                    for (String key : RivaSpawn.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false)) {
                        sender.sendMessage(Utils.colourise("&3- " + key));
                    }

                } else sender.sendMessage(Utils.colourise(noPerm));
            }

            if (args[0].equalsIgnoreCase("yenile") || args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("rivaspawn.reload")) {
                    RivaSpawn.getInstance().reloadConfig();
                    sender.sendMessage(Utils.colourise(reload));
                } else sender.sendMessage(noPerm);
            }

            if (args[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("rivaspawn.help")) {
                    for (String helpmessage : Config.getHelpMessages()) {
                        sender.sendMessage(Utils.colourise(helpmessage));
                    }
                }
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("belirle") || args[0].equalsIgnoreCase("create")) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("rivacoins.setspawn")) {

                        String spawnName = args[1].toLowerCase();
                        for (String key : (RivaSpawn.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false))) {
                            if (key.equalsIgnoreCase(spawnName)) {
                                sender.sendMessage(Utils.colourise(alreadyExists));
                                return false;
                            }
                        }
                        new Spawn(spawnName, ((Player) sender).getLocation(), true);
                        sender.sendMessage(Utils.colourise(create.replace("{spawn}", spawnName)));

                    } else sender.sendMessage(Utils.colourise(noPerm));
                } else sender.sendMessage(Utils.colourise("You cannot execute this command from the console!"));
            }

            if (args[0].equalsIgnoreCase("sil") || args[0].equalsIgnoreCase("delete")) {
                if (sender.hasPermission("rivaspawn.delete")) {
                    String spawnName = args[1].toLowerCase();
                    for (String key : RivaSpawn.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false)) {
                        if (key.toLowerCase().equals(spawnName)) {
                            RivaSpawn.getInstance().getConfig().set("spawns." + key, null);
                            break;
                        } else sender.sendMessage(Utils.colourise(notFound));
                        return false;
                    }

                    RivaSpawn.getInstance().saveConfig();
                    sender.sendMessage(Utils.colourise(delete.replace("{spawn}", spawnName)));
                }
            }
        }

        if (args.length == 7) {
            if (args[0].equalsIgnoreCase("edit")) {

                if (sender.hasPermission("rivaspawn.edit")) {

                    for (String key : RivaSpawn.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false)) {
                        if (key.equalsIgnoreCase(args[1])) {
                            double x;
                            double y;
                            double z;
                            float pitch;
                            float yaw;
                            try {
                                x = Double.parseDouble(args[2]);
                                y = Double.parseDouble(args[3]);
                                z = Double.parseDouble(args[4]);
                                pitch = Float.parseFloat(args[5]);
                                yaw = Float.parseFloat(args[6]);

                            } catch (NumberFormatException ignore) {
                                sender.sendMessage(Utils.colourise(noNumber));
                                return false;
                            }

                            Config.editSpawn(args[1].toLowerCase(), x, y, z, pitch, yaw);
                            sender.sendMessage(Utils.colourise(edited));
                            break;
                        } else {
                            sender.sendMessage(Utils.colourise(notFound));
                            return false;
                        }
                    }
                } else sender.sendMessage(Utils.colourise(noPerm));
            }
            return false;
        }
        return false;
    }
}