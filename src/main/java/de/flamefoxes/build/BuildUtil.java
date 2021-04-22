package de.flamefoxes.build;

import org.bukkit.plugin.PluginManager;

public class BuildUtil {
  private final Build build;
  private PluginManager pluginManager;
  
  private BuildUtil(Build build) {
    this.build = build;
  }

  public void initial() {
    pluginManager = build.getServer().getPluginManager();
  }

  public void deinitialize() {
    
  }
  
  public void registerCommands() {
    
  }

  public void registerListeners() {

  }

  public static BuildUtil create(Build build) {
    return new BuildUtil(build);
  }
}
