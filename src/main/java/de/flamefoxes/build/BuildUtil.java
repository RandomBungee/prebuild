package de.flamefoxes.build;

import de.flamefoxes.build.command.*;
import de.flamefoxes.build.config.*;
import de.flamefoxes.build.listener.*;
import de.flamefoxes.build.sql.Mysql;
import de.flamefoxes.build.util.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;

public class BuildUtil {

  public static final String PREFIX = "§7§l︳ §eVorbau§7 ● ";
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

  public void loadPlugin() {
    mysql = new Mysql(
      "192.168.1.102",
      "pre_build",
      "j6QkFrVNm1nezoU8",
      "pre_build",
      3306
    );
    mysql.connect();
    mysql.createTable();
    pluginManager = build.getServer().getPluginManager();
    locations = new Locations();
    inventory = new PlayerInventory(this);
    themes = new Themes();
    for(World worlds : Bukkit.getWorlds()) {
      build.getServer().getWorld(worlds.getName()).setStorm(false);
      build.getServer().getWorld(worlds.getName()).setGameRuleValue("randomTickSpeed", "0");
      build.getServer().getWorld(worlds.getName()).setGameRuleValue("doDaylightCycle", "false");
      build.getServer().getWorld(worlds.getName()).setTime(1000);
    }
  }

  public void unloadPlugin() {
    mysql.closeConnection();
    System.out.println("Plugin was disabled");
  }

  public void registerCommands() {
    build.getCommand("admin").setExecutor(new AdminCommand(this));
    build.getCommand("finish").setExecutor(new FinishCommand(this));
    build.getCommand("setspawn").setExecutor(new SetupCommand(this));
    build.getCommand("info").setExecutor(new InformationCommand(this));
    build.getCommand("delete").setExecutor(new DeleteCommand(this));
  }

  public void registerListeners() {
    pluginManager.registerEvents(new AdminInventoryInteractListener(this), build);
    pluginManager.registerEvents(new CanceledBlockInteractListener(this), build);
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

  public Mysql mysql() {
    return mysql;
  }
}
