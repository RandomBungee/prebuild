package de.flamefoxes.prebuild.event;

import de.flamefoxes.prebuild.PreBuilding;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final PreBuilding preBuilding;

    public JoinListener(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
    }

    @EventHandler
    public void playerTeleportToSpawn(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        player.teleport(preBuilding.locations().location("spawn"));
        player.sendMessage(PreBuilding.PREFIX + "§7Wilkommen auf dem Bau-Server!");
        player.sendMessage(PreBuilding.PREFIX + "§7Solltest du noch kein Plot haben gebe §c/start §7ein!");
        player.sendMessage(PreBuilding.PREFIX + "§7Bist du fertig mit deinem Plot? §aDann gebe §c/finish §aein!");
    }
}
