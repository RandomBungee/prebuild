package de.flamefoxes.prebuild.ineventory;

import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class AdminInventory {
    private final PrePlayer prePlayer;
    private final Inventory adminInventory;

    public AdminInventory() {
        this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
        adminInventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Spieler-Liste");
    }

    public void createInventory(Player player) {
        adminInventory.clear();
        for(String players : prePlayer.players()) {
            adminInventory.addItem(createPlayerHead(players));
        }
        player.openInventory(adminInventory);
    }

    private ItemStack createPlayerHead(String name) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
        skullMeta.setOwner(name);
        skullMeta.setDisplayName("§8» §7" + name);
        skullMeta.setLore(lore(name));
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }


    private List<String> lore(String name) {
        List<String> lores = new ArrayList<String>();
        lores.add("§7Thema §8» §a" + prePlayer.theme(name));
        lores.add("§7E-Mail §8» §a" + prePlayer.email(name));
        lores.add("§7Discord §8» §a" + prePlayer.discord(name));
        lores.add("§7Plotstatus §8» §a" + submitted(name));
        lores.add("§7Bewerbungsstatus §8» §a" + status(name));
        return lores;
    }

    private String status(String name) {
        if(prePlayer.status(name) == 0) {
            return "§cNicht Registriert";
        } else if(prePlayer.status(name) == 1) {
            return "§bIn Bearbeitung";
        } else if(prePlayer.status(name) == 2) {
            return "§aAngenommen";
        } else if(prePlayer.status(name) == 3) {
            return "§cAbgelehnt";
        }
        return "ERROR";
    }

    private String submitted(String name) {
        if(prePlayer.submitted(name) == 1) {
            return "§aAbgegeben";
        } else {
            return "§cNicht abgegeben";
        }
    }
}
