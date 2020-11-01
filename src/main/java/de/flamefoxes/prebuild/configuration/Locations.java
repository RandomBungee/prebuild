package de.flamefoxes.prebuild.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Locations {
    public Locations() {

    }

    private File file = new File("plugins/PreBuilding", "locations.yml");
    private FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

    public void setLocation(String name, Location location) {
        fileConfiguration.set(name + ".world", location.getWorld().getName());
        fileConfiguration.set(name + ".x", location.getX());
        fileConfiguration.set(name + ".Y", location.getY());
        fileConfiguration.set(name + ".Z", location.getZ());
        fileConfiguration.set(name + ".yaw", location.getYaw());
        fileConfiguration.set(name + ".pitch", location.getPitch());
        saveConfiguration();
    }

    public Location location(String name) {
        Location location;
        World world = Bukkit.getWorld(fileConfiguration.getString(name + ".world"));
        double x = fileConfiguration.getDouble(name + ".x");
        double y = fileConfiguration.getDouble(name + ".y");
        double z = fileConfiguration.getDouble(name + ".z");
        location = new Location(world, x, y, z);
        location.setYaw((float) fileConfiguration.getInt(name + ".yaw"));
        location.setPitch((float) fileConfiguration.getInt(name + ".pitch"));
        return location;
    }

    public void saveConfiguration() {
        try {
            fileConfiguration.save(file);
        } catch (IOException fileSaveFailure) {
            System.err.println("Can´t save themes.yml: " + fileSaveFailure.getMessage());
        }
    }
}
