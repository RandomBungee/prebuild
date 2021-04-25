package de.flamefoxes.build.listener;

import de.flamefoxes.build.player.BuildPlayer;
import de.flamefoxes.build.player.IBuildPlayer;
import de.flamefoxes.build.player.SqlBuildPlayer;
import de.flamefoxes.build.sql.Mysql;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CanceledBlockInteractListener implements Listener {
  private final IBuildPlayer iBuildPlayer = SqlBuildPlayer.create(Mysql.connection);
  private BuildPlayer buildPlayer;

  public CanceledBlockInteractListener() {}

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
    if(buildPlayer(player).getSubmitted() == 0) {
      blockPlaceEvent.setCancelled(true);
      return;
    }
    blockPlaceEvent.setCancelled(false);
  }

  @EventHandler
  public void checkPlayerAllowedToBreak(BlockBreakEvent blockBreakEvent) {
    Player player = blockBreakEvent.getPlayer();
    if(buildPlayer(player).getSubmitted() == 0) {
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
      .setApplyKey(0)
      .setSubmitted(0)
      .setPluginKind("")
      .setBuildStyle("")
      .setStructureKind("")
      .setTheme("")
      .build();
  }
}
