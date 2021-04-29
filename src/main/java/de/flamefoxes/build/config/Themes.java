package de.flamefoxes.build.config;

import java.io.*;
import java.util.*;
import org.bukkit.configuration.file.*;

public class Themes {

  public Themes() {
  }

  private final File file = new File("plugins/PreBuilding", "themes.yml");
  private final FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

  public void createFileIsNotExist(String topic) {
    if (themeExist(topic)) {
      List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes." + topic);
      themes.add("EXAMPLE_THEME");
      fileConfiguration.set("PreBuilding.Themes." + topic, themes);
      saveConfiguration();
    }
  }

  public boolean themeExist(String topic) {
    List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes." + topic);
    return themes.isEmpty();
  }

  public void saveConfiguration() {
    try {
      fileConfiguration.save(file);
    } catch (IOException fileSaveFailure) {
      System.err.println("Can´s save File: " + fileSaveFailure.getMessage());
    }
  }

  public List<String> selectAllThemes(String topic) {
    return fileConfiguration.getStringList("PreBuilding.Themes." + topic);
  }
}