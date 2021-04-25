package de.flamefoxes.build;

import de.flamefoxes.build.command.*;
import de.flamefoxes.build.config.*;
import de.flamefoxes.build.listener.*;
import de.flamefoxes.build.sql.Mysql;
import de.flamefoxes.build.util.PlayerInventory;
import org.bukkit.plugin.PluginManager;

public class BuildUtil {
  public static final String PREFIX = "";
  private final Build build;
  private PluginManager pluginManager;
  private Locations locations;
  private Mysql mysql;
  private PlayerInventory inventory;
  private Themes themes;
  private boolean registerProcess = false;

  private BuildUtil(Build build) {
    this.build = build;
  }

  public void initial() {
    mysql = new Mysql(
      "localhost",
      "test123",
      "123456789",
      "test123",
      3306
    );
    mysql.connect();
    mysql.createTable();
    pluginManager = build.getServer().getPluginManager();
    locations = new Locations();
    inventory = new PlayerInventory();
    themes = new Themes();
  }

  public void deinitialize() {
    mysql.closeConnection();
    System.out.println("Plugin was disabled");
  }
  
  public void registerCommands() {
    build.getCommand("admin").setExecutor(new AdminCommand(this));
    build.getCommand("finish").setExecutor(new FinishCommand(this));
    build.getCommand("setspawn").setExecutor(new SetupCommand(this));
    build.getCommand("info").setExecutor(new InformationCommand(this));
  }

  public void registerListeners() {
    pluginManager.registerEvents(new AdminInventoryInteractListener(this), build);
    pluginManager.registerEvents(new CanceledBlockInteractListener(), build);
    pluginManager.registerEvents(new PlayerInitialListener(this), build);
    pluginManager.registerEvents(new RegisterInventoryClickListener(this), build);
  }

  public Build build() {
    return build;
  }

  public Locations locations() {
    return locations;
  }

  public static BuildUtil create(Build build) {
    return new BuildUtil(build);
  }

  public PlayerInventory inventory() {
    return inventory;
  }

  public Themes themes() {
    return themes;
  }

  public boolean registerProcess() {
    return registerProcess;
  }

  public void registerProcess(boolean registerProcess) {
    this.registerProcess = registerProcess;
  }
}
