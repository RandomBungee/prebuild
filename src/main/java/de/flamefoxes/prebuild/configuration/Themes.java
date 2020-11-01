package de.flamefoxes.prebuild.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Themes {
    public Themes() {}

    private File file = new File("plugins/PreBuilding", "themes.yml");
    private FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

    public void setDefaultThemes() {
        if(themeExist()) {
            List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes");
            themes.add("EXAMPLE_THEME");
            fileConfiguration.set("PreBuilding.Themes", themes);
            saveConfiguration();
        }
    }

    public boolean themeExist() {
        List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes");
        return themes.isEmpty();
    }

    public List<String> themesFromFile() {
        return fileConfiguration.getStringList("PreBuilding.Themes");
    }

    public void saveConfiguration() {
        try {
            fileConfiguration.save(file);
        } catch (IOException fileSaveFailure) {
            System.err.println("Can´t save themes.yml: " + fileSaveFailure.getMessage());
        }
    }
}
