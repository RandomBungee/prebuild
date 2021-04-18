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

    public void setDefaultFantasyThemes() {
        if(themeFantasyExist()) {
            List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Fantasy");
            themes.add("EXAMPLE_THEME");
            fileConfiguration.set("PreBuilding.Themes.Fantasy", themes);
            saveConfiguration();
        }
    }

    public void setDefaultModernThemes() {
        if(themeModernExist()) {
            List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Modern");
            themes.add("EXAMPLE_THEME");
            fileConfiguration.set("PreBuilding.Themes.Modern", themes);
            saveConfiguration();
        }
    }

    public void setDefaultIdyllicThemes() {
        if(themeIdyllicExist()) {
            List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Idyllic");
            themes.add("EXAMPLE_THEME");
            fileConfiguration.set("PreBuilding.Themes.Idyllic", themes);
            saveConfiguration();
        }
    }

    public void setDefaultPostThemes() {
        if(themePostExist()) {
            List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Post");
            themes.add("EXAMPLE_THEME");
            fileConfiguration.set("PreBuilding.Themes.Post", themes);
            saveConfiguration();
        }
    }

    public void setDefaultOldThemes() {
        if(themeOldExist()) {
            List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Old");
            themes.add("EXAMPLE_THEME");
            fileConfiguration.set("PreBuilding.Themes.Old", themes);
            saveConfiguration();
        }
    }

    public void setDefaultNaturalThemes() {
        if(themeNaturalExist()) {
            List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Natural");
            themes.add("EXAMPLE_THEME");
            fileConfiguration.set("PreBuilding.Themes.Natural", themes);
            saveConfiguration();
        }
    }

    public boolean themeFantasyExist() {
        List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Fantasy");
        return themes.isEmpty();
    }

    public boolean themeModernExist() {
        List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Modern");
        return themes.isEmpty();
    }

    public boolean themeIdyllicExist() {
        List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Idyllic");
        return themes.isEmpty();
    }

    public boolean themePostExist() {
        List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Post");
        return themes.isEmpty();
    }

    public boolean themeOldExist() {
        List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Old");
        return themes.isEmpty();
    }

    public boolean themeNaturalExist() {
        List<String> themes = fileConfiguration.getStringList("PreBuilding.Themes.Natural");
        return themes.isEmpty();
    }

    public List<String> themesFantasyFromFile() {
        return fileConfiguration.getStringList("PreBuilding.Themes.Fantasy");
    }

    public List<String> themesModernFromFile() {
        return fileConfiguration.getStringList("PreBuilding.Themes.Modern");
    }

    public List<String> themesIdyllicFromFile() {
        return fileConfiguration.getStringList("PreBuilding.Themes.Idyllic");
    }

    public List<String> themesPostFromFile() {
        return fileConfiguration.getStringList("PreBuilding.Themes.Post");
    }

    public List<String> themesOldFromFile() {
        return fileConfiguration.getStringList("PreBuilding.Themes.Old");
    }

    public List<String> themesNaturalFromFile() {
        return fileConfiguration.getStringList("PreBuilding.Themes.Natural");
    }

    public void saveConfiguration() {
        try {
            fileConfiguration.save(file);
        } catch (IOException fileSaveFailure) {
            System.err.println("Can´t save themes.yml: " + fileSaveFailure.getMessage());
        }
    }
}
