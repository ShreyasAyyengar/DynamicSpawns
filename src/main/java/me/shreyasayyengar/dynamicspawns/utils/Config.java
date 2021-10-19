package me.shreyasayyengar.dynamicspawns.utils;

import me.shreyasayyengar.dynamicspawns.DynamicSpawns;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config {

    private static DynamicSpawns main;

    public Config(DynamicSpawns main) {
        Config.main = main;
        main.getConfig().options().copyDefaults(true);
        main.saveDefaultConfig();
    }

    public static Spawn getSpawn(String spawnName) {
        if (main.getConfig().getConfigurationSection("spawns.").getKeys(false).equals(spawnName)) {
            return new Spawn(
                    main.getConfig().getString("spawns." + spawnName),
                    new Location(
                            Bukkit.getWorld(main.getConfig().getString("spawns." + spawnName + ".world")),
                            main.getConfig().getDouble("spawns." + spawnName + ".x"),
                            main.getConfig().getDouble("spawns." + spawnName + ".y"),
                            main.getConfig().getDouble("spawns." + spawnName + ".z"),
                            (float) main.getConfig().getDouble("spawns." + spawnName + ".pitch"),
                            (float) main.getConfig().getDouble("spawns." + spawnName + ".yaw")
                    ),
                    false);
        } else return null;
    }

    public static void editSpawn(String spawnName, double x, double y, double z, float pitch, float yaw) {
        main.getConfig().set("spawns." + spawnName + ".x", x);
        main.getConfig().set("spawns." + spawnName + ".y", y);
        main.getConfig().set("spawns." + spawnName + ".z", z);
        main.getConfig().set("spawns." + spawnName + ".pitch", pitch);
        main.getConfig().set("spawns." + spawnName + ".yaw", yaw);
        main.saveConfig();
    }

    public static Location getSpawnLocation(String spawnName) {
        Set<String> keys = main.getConfig().getConfigurationSection("spawns.").getKeys(false);

        for (String key : keys) {
            if (key.equals(spawnName)) {
                return new Location(
                        Bukkit.getWorld(main.getConfig().getString("spawns." + spawnName + ".world")),
                        main.getConfig().getDouble("spawns." + spawnName + ".x"),
                        main.getConfig().getDouble("spawns." + spawnName + ".y"),
                        main.getConfig().getDouble("spawns." + spawnName + ".z"),
                        (float) main.getConfig().getDouble("spawns." + spawnName + ".yaw"),
                        (float) main.getConfig().getDouble("spawns." + spawnName + ".pitch")
                );

            }
        }
        return null;
    }

    public static void registerSpawn(Spawn spawn) {
//        main.getConfig().getConfigurationSection("spawns.").getKeys(false).add(spawn.getName());sdfdsf
        main.getConfig().set("spawns." + spawn.getName() + ".world", spawn.getLocation().getWorld().getName());
        main.getConfig().set("spawns." + spawn.getName() + ".x", (int) spawn.getLocation().getX());
        main.getConfig().set("spawns." + spawn.getName() + ".y", (int) spawn.getLocation().getY());
        main.getConfig().set("spawns." + spawn.getName() + ".z", (int) spawn.getLocation().getZ());
        main.getConfig().set("spawns." + spawn.getName() + ".yaw", spawn.getLocation().getYaw());
        main.getConfig().set("spawns." + spawn.getName() + ".pitch", spawn.getLocation().getPitch());
        main.saveConfig();
    }

    public static boolean useTimer() {
        return main.getConfig().getBoolean("timer.enabled");
    }

    public static int getTimer() {
        if (main.getConfig().getBoolean("timer.enabled")) {
            return main.getConfig().getInt("timer.seconds");
        }
        return 0;
    }

    public static String getCountdownMessage() {
        return main.getConfig().getString("timer.message");
    }

    public static boolean useTeleportMessage() {
        return main.getConfig().getBoolean("teleport-messages.enabled");
    }

    public static List<String> getTeleportMessage() {
        return new ArrayList<>(main.getConfig().getStringList("teleport-messages.message"));
    }

    public static List<String> getHelpMessages() {
        return new ArrayList<>(main.getConfig().getStringList("help-messages"));
    }

    public static String getNoPerm() {
        return main.getConfig().getString("messages.no-permission");
    }

    public static String reload() {
        return main.getConfig().getString("messages.reload");
    }

    public static String getCreate() {
        return main.getConfig().getString("messages.spawn-create");
    }

    public static String getDelete() {
        return main.getConfig().getString("messages.spawn-delete");
    }

    public static String noSuchSpawn() {
        return main.getConfig().getString("messages.spawn-not-found");
    }

    public static String getNoSpawns() {
        return main.getConfig().getString("messages.no-spawns");
    }

    public static String getAlreadyExists() {
        return main.getConfig().getString("messages.already-exist");
    }

    public static String getNotNumber() {
        return main.getConfig().getString("messages.not-valid-number");
    }

    public static String getEdited() {
        return main.getConfig().getString("messages.spawn-edit");
    }

    public static boolean teleportJoin() {
        return main.getConfig().getBoolean("teleport-on-join");
    }
}