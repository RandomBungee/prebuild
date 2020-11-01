package de.flamefoxes.prebuild;

import de.flamefoxes.prebuild.configuration.Themes;
import org.bukkit.plugin.java.JavaPlugin;

public class PreBuilding extends JavaPlugin {
    private PreBuilding() {}

    public static final String PREFIX = "§7[§eVorbau-Server§7] §8| §r";
    public Themes themes;

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
        themes = new Themes();
    }

    public Themes themes() {
        return this.themes;
    }
}
