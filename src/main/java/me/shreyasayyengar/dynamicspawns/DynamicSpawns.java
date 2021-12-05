package me.shreyasayyengar.dynamicspawns;

import me.shreyasayyengar.dynamicspawns.commands.AdminSpawnCommand;
import me.shreyasayyengar.dynamicspawns.commands.SpawnCommand;
import me.shreyasayyengar.dynamicspawns.events.Join;
import me.shreyasayyengar.dynamicspawns.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicSpawns extends JavaPlugin {

    private static DynamicSpawns INSTANCE;

    public static DynamicSpawns getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        new Config(this);
        Bukkit.getLogger().info("Plugin started with no errors");
        getServer().getPluginManager().registerEvents(new Join(), this);
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("dynamicspawns").setExecutor(new AdminSpawnCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.AQUA + "Shutting down... goodbye!");
    }
}