package de.flamefoxes.prebuild.event;

import de.flamefoxes.prebuild.PreBuilding;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminInventoryInteractListener implements Listener {
    private final PreBuilding preBuilding;

    public AdminInventoryInteractListener(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
    }

    @EventHandler
    public void teleportToPlot(InventoryClickEvent inventoryClickEvent) {
         Player player = (Player)inventoryClickEvent.getWhoClicked();
         try {
             if(inventoryClickEvent.getView().getTitle().equalsIgnoreCase("§7Spieler-Liste")) {
                String playerName
                        = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§8» ", "");
                preBuilding.getServer().dispatchCommand(player, "mv tp " + playerName);
                player.closeInventory();
             }
         } catch (Exception ignore) {}
    }
}
