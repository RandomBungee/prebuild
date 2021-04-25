package de.flamefoxes.build.listener;

import de.flamefoxes.build.*;
import de.flamefoxes.build.player.*;
import de.flamefoxes.build.sql.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class PlayerInitialListener implements Listener {
  private final BuildUtil buildUtil;
  private final IBuildPlayer iBuildPlayer = SqlBuildPlayer.create(Mysql.connection);

  public PlayerInitialListener(BuildUtil buildUtil) {
    this.buildUtil = buildUtil;
  }

  @EventHandler
  public void playerInitialOnJoin(PlayerJoinEvent playerJoinEvent) {
    Player player = playerJoinEvent.getPlayer();
    playerJoinEvent.setJoinMessage(null);
    if(!iBuildPlayer.exist(player.getUniqueId())) {
      buildUtil.inventory().createInventory("video", player);
      buildUtil.registerProcess(true);
      return;
    }
    player.sendMessage(BuildUtil.PREFIX + "§7Wilkommen auf dem Bau-Server!");
    player.sendMessage(BuildUtil.PREFIX + "§7Bist du fertig mit deiner Welt? §aDann gebe §c/finish §aein!");
  }
}
