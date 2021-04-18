package de.flamefoxes.prebuild.event;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinTeleportListener implements Listener {
    private final PreBuilding preBuilding;
    private final PrePlayer prePlayer;

    public JoinTeleportListener(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
        this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
    }

    @EventHandler
    public void playerTeleportToSpawn(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        if(!prePlayer.exist(player.getName())) {
            player.teleport(preBuilding.locations().location("spawn"));
            preBuilding.playerInventory().createIntroductionInventory(player);
            preBuilding.registerProcess(true);
        }
        joinEvent.setJoinMessage(null);
        if(prePlayer.status(player.getName()) == 1) {
            player.sendMessage(PreBuilding.PREFIX + "§7Wilkommen auf dem Bau-Server!");
            player.sendMessage(PreBuilding.PREFIX + "§7Bist du fertig mit deiner Welt? §aDann gebe §c/finish §aein!");
            return;
        }
        if(prePlayer.status(player.getName()) == 2) {
            player.sendMessage(PreBuilding.PREFIX + "§7Deine vorgebaute Welt wurde §aangenommen");
            return;
        }
        if(prePlayer.status(player.getName()) == 3) {
            player.sendMessage(PreBuilding.PREFIX + "§7Deine vorgebaute Welt wurde §cabgelehnt");
        }
    }
}
