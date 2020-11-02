package de.flamefoxes.prebuild.event;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.command.AdminCommand;
import org.bukkit.entity.HumanEntity;
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
             if(inventoryClickEvent.getInventory().getName().equalsIgnoreCase("§7Spieler-Liste")) {
                String playerName
                        = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§8» §7", "");
                preBuilding.getServer().dispatchCommand(player, "p teleport " + playerName);
             }
         } catch (Exception ignore) {}
    }
}
