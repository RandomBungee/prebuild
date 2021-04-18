package de.flamefoxes.prebuild;

import de.flamefoxes.prebuild.command.*;
import de.flamefoxes.prebuild.configuration.Locations;
import de.flamefoxes.prebuild.configuration.Themes;
import de.flamefoxes.prebuild.event.AdminInventoryInteractListener;
import de.flamefoxes.prebuild.event.CancelledBlockListener;
import de.flamefoxes.prebuild.event.JoinTeleportListener;
import de.flamefoxes.prebuild.event.RegisterInventoryInteractListener;
import de.flamefoxes.prebuild.ineventory.PlayerInventory;
import de.flamefoxes.prebuild.sql.Mysql;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class PreBuilding extends JavaPlugin {
    public PreBuilding() {}

    public static final String PREFIX = "§7[§eVorbau-Server§7] §8| §r";
    private Themes themes;
    private Locations locations;
    private PlayerInventory playerInventory;
    private boolean registerProcess = false;

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
       /* Mysql mysql = new Mysql(
                "192.168.1.102",
                "pre_build",
                "j6QkFrVNm1nezoU8",
                "pre_build",
                3306
        );*/
        Mysql mysql = new Mysql(
          "127.0.0.1",
          "test123",
          "123456789",
          "test123",
          3306
        );
        themes = new Themes();
        locations = new Locations();
        playerInventory = new PlayerInventory();
        mysql.createTable();
        getCommand("finish").setExecutor(new FinishCommand(this));
        getCommand("setspawn").setExecutor(new SetupCommand(this));
        getCommand("admin").setExecutor(new AdminCommand(this));
        getCommand("status").setExecutor(new StatusCommand());
        getServer().getPluginManager().registerEvents(new JoinTeleportListener(this), this);
        getServer().getPluginManager().registerEvents(new CancelledBlockListener(), this);
        getServer().getPluginManager().registerEvents(new AdminInventoryInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new RegisterInventoryInteractListener(this), this);
        themes.setDefaultFantasyThemes();
        themes.setDefaultIdyllicThemes();
        themes.setDefaultOldThemes();
        themes.setDefaultNaturalThemes();
        themes.setDefaultModernThemes();
        themes.setDefaultPostThemes();
        for(World worlds : Bukkit.getWorlds()) {
            getServer().getWorld(worlds.getName()).setStorm(false);
            getServer().getWorld(worlds.getName()).setGameRuleValue("randomTickSpeed", "0");
            getServer().getWorld(worlds.getName()).setGameRuleValue("doDaylightCycle", "false");
            getServer().getWorld(worlds.getName()).setTime(1000);
        }
    }

    public Themes themes() {
        return this.themes;
    }

    public Locations locations() { return this.locations; }

    public PlayerInventory playerInventory() { return this.playerInventory; }

    public boolean registerProcess() {
        return registerProcess;
    }

    public void registerProcess(boolean registerProcess) {
        this.registerProcess = registerProcess;
    }
}
