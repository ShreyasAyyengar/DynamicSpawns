package me.shreyasayyengar.dynamicspawns.commands;

import com.google.common.collect.Lists;
import me.shreyasayyengar.dynamicspawns.DynamicSpawns;
import me.shreyasayyengar.dynamicspawns.utils.Config;
import me.shreyasayyengar.dynamicspawns.utils.Spawn;
import me.shreyasayyengar.dynamicspawns.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminSpawnCommand implements CommandExecutor, TabCompleter {

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
            if (sender.hasPermission("dynamicspawns.help")) {
                for (String helpmessage : Config.getHelpMessages()) {
                    sender.sendMessage(Utils.colourise(helpmessage));
                }
            }
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (sender.hasPermission("dynamicspawns.list")) {
                    sender.sendMessage(Utils.colourise("&3Active Spawns:"));
                    if (Utils.getSpawns().size() == 0) {
                        sender.sendMessage(Utils.colourise(noSpawn));
                        return false;
                    }

                    for (String key : Utils.getSpawns()) {
                        sender.sendMessage(Utils.colourise("&3- " + key));
                    }

                } else sender.sendMessage(Utils.colourise(noPerm));
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("dynamicspawn.reload")) {
                    DynamicSpawns.getINSTANCE().reloadConfig();
                    sender.sendMessage(Utils.colourise(reload));
                } else sender.sendMessage(noPerm);
            }

            if (args[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("dynamicspawn.help")) {
                    for (String helpmessage : Config.getHelpMessages()) {
                        sender.sendMessage(Utils.colourise(helpmessage));
                    }
                }
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("dynamicspawns.setspawn")) {

                        String spawnName = args[1].toLowerCase();
                        for (String key : Utils.getSpawns()) {
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

            if (args[0].equalsIgnoreCase("delete")) {
                if (sender.hasPermission("dynamicspawns.delete")) {
                    String spawnName = args[1].toLowerCase();

                    if (Utils.getSpawns().toString().contains(spawnName)) {
                        DynamicSpawns.getINSTANCE().getConfig().set("spawns." + spawnName.toLowerCase(), null);
                        DynamicSpawns.getINSTANCE().saveConfig();
                        sender.sendMessage(Utils.colourise(delete.replace("{spawn}", spawnName)));
                        return false;
                    } else sender.sendMessage(Utils.colourise(notFound));

                }
            }
        }

        if (args.length == 7) {
            if (args[0].equalsIgnoreCase("edit")) {

                if (sender.hasPermission("dynamicspawns.edit")) {

                    for (String key : Utils.getSpawns()) {
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
                            sender.sendMessage(Utils.colourise(edited.replace("{spawn}", args[1].toLowerCase())));
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> options;
        List<String> complete = new ArrayList<>();

        if (args.length == 1) {

            options = Arrays.asList("create", "delete", "edit", "list", "reload");
            complete = Lists.newArrayList();

            for (String option : options) {
                if (option.toLowerCase().startsWith(args[0].toLowerCase()))
                    complete.add(option);
            }

            return complete;
        }

        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("create")) {
                complete.add("<name>");
                return complete;
            }

            if (args[0].equalsIgnoreCase("delete")) {

                options = new ArrayList<>(Utils.getSpawns());

                for (String option : options) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase()))
                        complete.add(option);
                }

                return options;
            }

            if (args[0].equalsIgnoreCase("edit")) {
                return new ArrayList<>(Utils.getSpawns());
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("edit")) {

                ArrayList<String> objects = new ArrayList<>();
                objects.add((sender instanceof Player ? String.valueOf(Math.round(((Player) sender).getLocation().getX())) : "<x>"));

                return objects;
            }
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("edit")) {

                ArrayList<String> objects = new ArrayList<>();
                objects.add((sender instanceof Player ? String.valueOf(Math.round(((Player) sender).getLocation().getY())) : "<y>"));
                return objects;
            }
        }

        if (args.length == 5) {
            if (args[0].equalsIgnoreCase("edit")) {

                ArrayList<String> objects = new ArrayList<>();
                objects.add((sender instanceof Player ? String.valueOf(Math.round(((Player) sender).getLocation().getZ())) : "<z>"));

                return objects;
            }
        }

        if (args.length == 6) {
            if (args[0].equalsIgnoreCase("edit")) {

                ArrayList<String> objects = new ArrayList<>();
                objects.add((sender instanceof Player ? String.valueOf(Math.round(((Player) sender).getLocation().getPitch())) : "<pitch>"));

                return objects;
            }
        }

        if (args.length == 7) {
            if (args[0].equalsIgnoreCase("edit")) {

                ArrayList<String> objects = new ArrayList<>();
                objects.add((sender instanceof Player ? String.valueOf(Math.round(((Player) sender).getLocation().getYaw())) : "<yaw>"));

                return objects;
            }
        }

        return null;
    }
}