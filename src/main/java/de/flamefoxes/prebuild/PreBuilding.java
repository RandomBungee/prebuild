package de.flamefoxes.prebuild;

import de.flamefoxes.prebuild.command.*;
import de.flamefoxes.prebuild.configuration.Locations;
import de.flamefoxes.prebuild.configuration.Themes;
import de.flamefoxes.prebuild.event.AdminInventoryInteractListener;
import de.flamefoxes.prebuild.event.CancelledBlockListener;
import de.flamefoxes.prebuild.event.JoinTeleportListener;
import de.flamefoxes.prebuild.ineventory.AdminInventory;
import de.flamefoxes.prebuild.sql.Mysql;
import org.bukkit.plugin.java.JavaPlugin;

public class PreBuilding extends JavaPlugin {
    public PreBuilding() {}

    public static final String PREFIX = "§7[§eVorbau-Server§7] §8| §r";
    private Themes themes;
    private Locations locations;
    private AdminInventory adminInventory;

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
        Mysql mysql = new Mysql(
                "localhost",
                "test123",
                "123456789",
                "test123",
                3306
        );
        themes = new Themes();
        locations = new Locations();
        adminInventory = new AdminInventory();
        mysql.createTable();
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("contact").setExecutor(new ContactCommand());
        getCommand("finish").setExecutor(new FinishCommand(this));
        getCommand("setspawn").setExecutor(new SetupCommand(this));
        getCommand("admin").setExecutor(new AdminCommand(this));
        getCommand("status").setExecutor(new StatusCommand());
        getServer().getPluginManager().registerEvents(new JoinTeleportListener(this), this);
        getServer().getPluginManager().registerEvents(new CancelledBlockListener(), this);
        getServer().getPluginManager().registerEvents(new AdminInventoryInteractListener(this), this);
        themes.setDefaultThemes();
    }

    public Themes themes() {
        return this.themes;
    }

    public Locations locations() { return this.locations; }

    public AdminInventory adminInventory() { return this.adminInventory; }
}
