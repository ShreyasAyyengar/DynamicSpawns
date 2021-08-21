package me.shreyasayyengar.rivaspawn;

import me.shreyasayyengar.rivaspawn.commands.AdminSpawnCommand;
import me.shreyasayyengar.rivaspawn.commands.RivaSpawnCommand;
import me.shreyasayyengar.rivaspawn.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RivaSpawn extends JavaPlugin {

    private static RivaSpawn instance;

    public static RivaSpawn getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        new Config(this);
        Bukkit.getLogger().info("Plugin started with no errors");
//        getServer().getPluginManager().registerEvents(new Join(), this);
        getCommand("spawn").setExecutor(new RivaSpawnCommand());
        getCommand("rivaspawn").setExecutor(new AdminSpawnCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
