package de.flamefoxes.build.listener;

import de.flamefoxes.build.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;

public class AdminInventoryInteractListener implements Listener {
  private final BuildUtil buildUtil;

  public AdminInventoryInteractListener(BuildUtil buildUtil) {
    this.buildUtil = buildUtil;
  }

  @EventHandler
  public void teleportToWorld(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player)inventoryClickEvent.getWhoClicked();
    try {
      if(inventoryClickEvent.getView().getTitle().equalsIgnoreCase("§7Spieler-List")) {
        String playerName
          = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§8» ", "");
        player.closeInventory();
        buildUtil.build().getServer().dispatchCommand(player, "mv tp " + playerName);
        buildUtil.build().getServer().dispatchCommand(player, "mv confirm");
      }
    } catch (Exception ignore) {}
  }
}
