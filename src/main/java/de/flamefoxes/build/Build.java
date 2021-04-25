package de.flamefoxes.build;

import org.bukkit.plugin.java.JavaPlugin;

public class Build extends JavaPlugin {
  private final BuildUtil buildUtil = BuildUtil.create(this);

  public Build() {}

  @Override
  public void onEnable() {
    buildUtil.initial();
    buildUtil.registerCommands();
    buildUtil.registerListeners();
  }

  @Override
  public void onDisable() {
    buildUtil.deinitialize();
  }
}
