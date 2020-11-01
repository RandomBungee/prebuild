package de.flamefoxes.prebuild.event;

import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class CancelledBlockListener implements Listener {
    private final PrePlayer prePlayer;

    public CancelledBlockListener() {
        prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
    }

    @EventHandler
    public void checkPlayerAllowedToPlace(BlockPlaceEvent blockPlaceEvent) {
        Player player = blockPlaceEvent.getPlayer();
        if(prePlayer.status(player.getName()) == 1) {
            blockPlaceEvent.setCancelled(true);
        }
        blockPlaceEvent.setCancelled(false);
    }

    @EventHandler
    public void checkPlayerAllowedToBreak(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        if(prePlayer.status(player.getName()) == 1) {
            blockBreakEvent.setCancelled(true);
        }
        blockBreakEvent.setCancelled(false);
    }
}
