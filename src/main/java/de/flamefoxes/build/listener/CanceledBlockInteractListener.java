package de.flamefoxes.build.listener;

import de.flamefoxes.build.Build;
import de.flamefoxes.build.BuildUtil;
import de.flamefoxes.build.player.*;
import de.flamefoxes.build.sql.*;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;

public class CanceledBlockInteractListener implements Listener {

  private final IBuildPlayer iBuildPlayer;
  private final BuildUtil buildUtil;

  public CanceledBlockInteractListener(BuildUtil buildUtil) {
    this.buildUtil = buildUtil;
    iBuildPlayer = SqlBuildPlayer.create(Mysql.connection, buildUtil);
  }

  @EventHandler
  public void blockDamageByEntity(EntityDamageByEntityEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void blockDamageByBlock(EntityDamageByBlockEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void blockDamage(EntityDamageEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void checkPlayerAllowedToPlace(BlockPlaceEvent blockPlaceEvent) {
    Player player = blockPlaceEvent.getPlayer();
    if (buildPlayer(player).getSubmitted() == 1) {
      blockPlaceEvent.setCancelled(true);
      return;
    }
    blockPlaceEvent.setCancelled(false);
  }

  @EventHandler
  public void checkPlayerAllowedToBreak(BlockBreakEvent blockBreakEvent) {
    Player player = blockBreakEvent.getPlayer();
    if (buildPlayer(player).getSubmitted() == 1) {
      blockBreakEvent.setCancelled(true);
      return;
    }
    blockBreakEvent.setCancelled(false);
  }

  private BuildPlayer buildPlayer(Player player) {
    Optional<BuildPlayer> optionalBuildPlayer = iBuildPlayer.find(player.getUniqueId());
    return optionalBuildPlayer.orElse(noSuchPlayer());
  }

  public BuildPlayer noSuchPlayer() {
    return BuildPlayer.newBuilder()
      .setName("")
      .setUniqueId(UUID.randomUUID())
      .setApplyKey("0")
      .setSubmitted(0)
      .setPluginKind("")
      .setBuildStyle("")
      .setStructureKind("")
      .setTheme("")
      .build();
  }
}
