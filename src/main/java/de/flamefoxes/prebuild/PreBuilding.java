package de.flamefoxes.prebuild;

import de.flamefoxes.prebuild.configuration.Locations;
import de.flamefoxes.prebuild.configuration.Themes;
import de.flamefoxes.prebuild.sql.Mysql;
import org.bukkit.plugin.java.JavaPlugin;

public class PreBuilding extends JavaPlugin {
    private PreBuilding() {}

    public static final String PREFIX = "§7[§eVorbau-Server§7] §8| §r";
    public Themes themes;
    public Locations locations;

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
        themes = new Themes();
        locations = new Locations();
        Mysql mysql = new Mysql("", "", "", "", 3306);
        mysql.createTable();
    }

    public Themes themes() {
        return this.themes;
    }
}
