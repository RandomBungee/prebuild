package de.flamefoxes.prebuild;

import com.avaje.ebeaninternal.server.type.ScalarDataReader;
import de.flamefoxes.prebuild.command.ContactCommand;
import de.flamefoxes.prebuild.command.FinishCommand;
import de.flamefoxes.prebuild.command.SetupCommand;
import de.flamefoxes.prebuild.command.StartCommand;
import de.flamefoxes.prebuild.configuration.Locations;
import de.flamefoxes.prebuild.configuration.Themes;
import de.flamefoxes.prebuild.event.CancelledBlockListener;
import de.flamefoxes.prebuild.event.JoinTeleportListener;
import de.flamefoxes.prebuild.score.Score;
import de.flamefoxes.prebuild.sql.Mysql;
import org.bukkit.plugin.java.JavaPlugin;

public class PreBuilding extends JavaPlugin {
    public PreBuilding() {}

    public static final String PREFIX = "§7[§eVorbau-Server§7] §8| §r";
    private Themes themes;
    private Locations locations;

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
        themes = new Themes();
        locations = new Locations();
        Mysql mysql = new Mysql(
                "localhost",
                "test123",
                "123456789",
                "test123",
                3306
        );
        mysql.createTable();
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("contact").setExecutor(new ContactCommand());
        getCommand("finish").setExecutor(new FinishCommand(this));
        getCommand("setspawn").setExecutor(new SetupCommand(this));
        getServer().getPluginManager().registerEvents(new JoinTeleportListener(this), this);
        getServer().getPluginManager().registerEvents(new CancelledBlockListener(), this);
        themes.setDefaultThemes();
    }

    public Themes themes() {
        return this.themes;
    }

    public Locations locations() { return this.locations; }
}
