package me.shreyasayyengar.dynamicspawns;

import me.shreyasayyengar.dynamicspawns.commands.AdminSpawnCommand;
import me.shreyasayyengar.dynamicspawns.commands.SpawnCommand;
import me.shreyasayyengar.dynamicspawns.events.Join;
import me.shreyasayyengar.dynamicspawns.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicSpawns extends JavaPlugin {

    private static DynamicSpawns instance;

    public static DynamicSpawns getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        new Config(this);
        Bukkit.getLogger().info("Plugin started with no errors");
        getServer().getPluginManager().registerEvents(new Join(), this);
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("dynamicspawns").setExecutor(new AdminSpawnCommand());
        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}