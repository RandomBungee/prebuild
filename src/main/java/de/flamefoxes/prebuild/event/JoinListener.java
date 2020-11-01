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
    }
}
