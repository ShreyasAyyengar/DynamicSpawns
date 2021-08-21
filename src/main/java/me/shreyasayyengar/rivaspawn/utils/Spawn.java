package me.shreyasayyengar.rivaspawn.utils;

import me.shreyasayyengar.rivaspawn.RivaSpawn;
import org.bukkit.Location;

public class Spawn {

    private String name;
    private Location location;

    public Spawn(String name, Location location, boolean register) {
        this.name = name;
        this.location = location;
        if (register) {
            Config.registerSpawn(this);
            RivaSpawn.getInstance().saveConfig();
        }
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
