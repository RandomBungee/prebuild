package de.flamefoxes.build.config;

import org.bukkit.*;
import org.bukkit.configuration.file.*;

import java.io.*;

public class Locations {

  public Locations() {
  }

  private final File file = new File("plugins/PreBuilding", "locations.yml");
  private final FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

  public void setLocation(String name, Location location) {
    fileConfiguration.set(name + ".world", location.getWorld().getName());
    fileConfiguration.set(name + ".X", location.getX());
    fileConfiguration.set(name + ".Y", location.getY());
    fileConfiguration.set(name + ".Z", location.getZ());
    fileConfiguration.set(name + ".Yaw", location.getYaw());
    fileConfiguration.set(name + ".Pitch", location.getPitch());
    saveConfiguration();
  }

  public Location location(String name) {
    Location location;
    World world = Bukkit.getWorld(fileConfiguration.getString(name + ".world"));
    double x = fileConfiguration.getDouble(name + ".X");
    double y = fileConfiguration.getDouble(name + ".Y");
    double z = fileConfiguration.getDouble(name + ".Z");
    location = new Location(world, x, y, z);
    location.setYaw(fileConfiguration.getInt(name + ".Yaw"));
    location.setPitch(fileConfiguration.getInt(name + ".Pitch"));
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
