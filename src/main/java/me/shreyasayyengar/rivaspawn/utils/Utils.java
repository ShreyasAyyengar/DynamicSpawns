package me.shreyasayyengar.rivaspawn.utils;

import me.shreyasayyengar.rivaspawn.RivaSpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Utils {

    public static String getRandomElement(List<String> list) {
        try {
            Random rand = new Random();
            return list.get(rand.nextInt(list.size()));
        } catch (IllegalArgumentException x) {
            Bukkit.getLogger().warning("There are no spawns set!");
            Bukkit.getScheduler().cancelTasks(RivaSpawn.getInstance());
        }
        return null;
    }

    public static void teleportPlayer(Player player) {
        Set<String> keys = RivaSpawn.getInstance().getConfig().getConfigurationSection("spawns.").getKeys(false);

        if (keys.size() > 0) {
            List<String> keyList = new ArrayList<>(keys);

            Location spawnLocation = Config.getSpawnLocation(Utils.getRandomElement(keyList));
            assert spawnLocation != null;

            double x = spawnLocation.getX();
            double z = spawnLocation.getZ();

            if (x > 0 && z > 0) {
                player.teleport(spawnLocation.add(0.5, 0, 0.5));
            } else if (x < 0 && z > 0) {
                player.teleport(spawnLocation.add(-0.5, 0, 0.5));
            } else if (x > 0 && z < 0) {
                player.teleport(spawnLocation.add(0.5, 0, -0.5));
            } else if (x < 0 && z <  0) {
                player.teleport(spawnLocation.add(-0.5, 0, -0.5));
            }

        }
    }

    public static String colourise(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
