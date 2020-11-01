package de.flamefoxes.prebuild.event;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.score.Score;
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
        player.teleport(preBuilding.locations().location("spawn"));
        player.sendMessage(PreBuilding.PREFIX + "§7Wilkommen auf dem Bau-Server!");
        player.sendMessage(PreBuilding.PREFIX + "§7Solltest du noch kein Plot haben gebe §c/start §7ein!");
        player.sendMessage(PreBuilding.PREFIX + "§7Bist du fertig mit deinem Plot? §aDann gebe §c/finish §aein!");
        prePlayer.create(player.getName(), null, null, null, 0, 0);
        Score.setScoreboard(player);
    }
}
