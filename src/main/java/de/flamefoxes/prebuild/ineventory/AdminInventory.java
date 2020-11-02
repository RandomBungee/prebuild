package de.flamefoxes.prebuild.ineventory;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class AdminInventory {
    private final PreBuilding preBuilding;
    private final PrePlayer prePlayer;
    private final Inventory adminInventory;

    public AdminInventory(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
        this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
        adminInventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Spieler-Liste");
    }

    public void createInventory(Player player) {

    }
}
